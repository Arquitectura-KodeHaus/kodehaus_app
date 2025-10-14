import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Modulo } from '../models/modulo';
import { ModuloService } from '../services/modulo.service';

@Component({
  selector: 'app-modulos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './modulos.component.html',
  styleUrl: './modulos.component.css'
})
export class ModulosComponent {

  /*/
  modulos: Modulo[] = [
    { id: 1, nombre: 'Inventario', plazas: 15, estado: 'Activo', descripcion: 'Gestión de productos y stock.' },
    { id: 2, nombre: 'Parqueaderos', plazas: 9, estado: 'En desarrollo', descripcion: 'Control y asignación de espacios de parqueo.' },
    { id: 3, nombre: 'Facturación', plazas: 22, estado: 'Activo', descripcion: 'Emisión y gestión de comprobantes de pago.' },
    { id: 4, nombre: 'Contabilidad', plazas: 5, estado: 'Inactivo', descripcion: 'Sistema de registros contables.' }
  ];*/

  modulos: Modulo[] = [];

  modalAbierto: boolean = false;
  moduloAEditar: Modulo | null = null;

  estadosDisponibles: Modulo['estado'][] = ['Activo', 'En desarrollo', 'Inactivo'];

  constructor(private moduloService: ModuloService) {
  }

  ngOnInit(): void {
    this.moduloService.getModulos().subscribe({
      next: (data) => {
        console.log('Módulos cargados:', data);
        this.modulos = data;
      },
      error: (error) => {
        console.error('Error al cargar los módulos:', error);
      }
    });
  }

  abrirModalEdicion(modulo: Modulo): void {
    // Clona el objeto para evitar modificar el original directamente en el formulario
    this.moduloAEditar = { ...modulo }; //la notacion ... se usa para clonar el objeto
    this.modalAbierto = true;
  }



  cerrarModal(): void {
    this.modalAbierto = false;
    this.moduloAEditar = null;
  }

  guardarCambios(): void {
    if (this.moduloAEditar) {
        const isNew = this.moduloAEditar.id === -1;

        if (isNew) {
          // Crear módulo nuevo
          this.moduloService.createModulo(this.moduloAEditar).subscribe({
            next: (nuevo) => {
              this.modulos.push(nuevo);
              console.log(`Módulo "${nuevo.nombre}" creado.`);
              this.cerrarModal();
            },
            error: (err) => console.error('Error al crear el módulo:', err)
          });

        } else {
          // Actualizar módulo existente
          this.moduloService.updateModulo(this.moduloAEditar).subscribe({
            next: (actualizado) => {
              const index = this.modulos.findIndex(m => m.id === actualizado.id);
              if (index !== -1) {
                this.modulos[index] = actualizado;
              }
              console.log(`Módulo "${actualizado.nombre}" actualizado.`);
              this.cerrarModal();
            },
            error: (err) => console.error('Error al actualizar el módulo:', err)
          });
        }
      }
  }

  eliminarModulo(modulo: Modulo): void {
    if (confirm(`¿Estás seguro de que quieres eliminar el módulo "${modulo.nombre}"?`)) {
      this.moduloService.deleteModulo(modulo.id).subscribe({
        next: () => {
          this.modulos = this.modulos.filter(m => m.id !== modulo.id);
          console.log(`Módulo "${modulo.nombre}" eliminado.`);
        },
        error: (err) => console.error('Error al eliminar el módulo:', err)
      });
    }
  }

  obtenerClaseEstado(estado: Modulo['estado']): string {
    switch (estado) {
      case 'Activo':
        return 'active';
      case 'En desarrollo':
        return 'dev';
      case 'Inactivo':
        return 'inactive';
      default:
        return '';
    }
  }
  abrirModalNuevo(): void {
    this.moduloAEditar = {
      // Es crucial que el nuevo módulo tenga un ID temporal nulo o un valor que
      // indique que es nuevo (aquí usamos -1, aunque lo manejaremos con la lógica de Guardar).
      id: -1, 
      nombre: '',
      plazas: 0,
      estado: 'Activo', // Estado predeterminado para nuevos módulos
      descripcion: ''
    };
    this.modalAbierto = true;
  }

  private getNextId(): number {
    const maxId = this.modulos.reduce((max, m) => (m.id > max ? m.id : max), 0);
    return maxId + 1;
  }
}
