import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GerentesService } from '../services/gerentes.service';
import { EventosService } from '../services/eventos.service';
import { PlazaService, Plaza } from '../services/plaza.service';
import { Gerente, CreateGerenteRequest, UpdateGerenteRequest } from '../models/gerente';

@Component({
  selector: 'app-gerentes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './gerentes.component.html',
  styleUrls: ['./gerentes.component.css']
})
export class GerentesComponent implements OnInit {
  gerentes: Gerente[] = [];
  filtered: Gerente[] = [];
  plazas: Plaza[] = [];
  estadoFilter = 'Todos';
  searchTerm = '';
  isLoading = false;
  error = '';

  // Modal de creación/edición
  modalAbierto = false;
  modoEdicion = false;
  gerenteSeleccionado: Gerente | null = null;
  
  // Formulario
  formulario: CreateGerenteRequest = {
    nombre: '',
    apellido: '',
    email: '',
    password: '',
    telefono: '',
    identificacion: '',
    rol: 'gerente',
    idPlaza: undefined
  };

  // Modal de detalle
  modalDetalleAbierto = false;
  gerenteDetalle: Gerente | null = null;

  constructor(
    private gerentesService: GerentesService,
    private plazaService: PlazaService,
    private eventosService: EventosService
  ) {}

  ngOnInit(): void {
    this.cargarGerentes();
    this.cargarPlazas();
  }

  cargarPlazas(): void {
    this.plazaService.list().subscribe({
      next: (data) => {
        this.plazas = data;
      },
      error: (err) => {
        console.error('Error al cargar plazas:', err);
      }
    });
  }

  cargarGerentes(): void {
    this.isLoading = true;
    this.error = '';
    
    this.gerentesService.list().subscribe({
      next: (data) => {
        this.gerentes = data;
        this.applyFilters();
        this.isLoading = false;
      },
      error: (err) => {
        // Mostrar un mensaje amigable sin revelar la URL del backend
        this.error = 'No se pudo cargar la lista de gerentes. Verifica la conexión con el servidor e inténtalo más tarde.';
        this.isLoading = false;
        console.error('Error al cargar gerentes:', err);
        try {
          this.eventosService.agregarNotificacion('Error al cargar gerentes: servidor inaccesible', 'alerta');
        } catch (e) { /* ignore */ }
      }
    });
  }

  /**
   * Permite cerrar manualmente el mensaje de error para limpiar la UI
   */
  cerrarError(): void {
    this.error = '';
  }

  applyFilters(): void {
    let result = [...this.gerentes];

    // Filtro por estado
    if (this.estadoFilter !== 'Todos') {
      result = result.filter(g => g.estado === this.estadoFilter);
    }

    // Búsqueda por texto
    if (this.searchTerm.trim()) {
      const term = this.searchTerm.toLowerCase();
      result = result.filter(g => 
        g.nombre.toLowerCase().includes(term) ||
        g.apellido.toLowerCase().includes(term) ||
        g.email.toLowerCase().includes(term) ||
        g.identificacion.includes(term) ||
        (g.nombrePlaza && g.nombrePlaza.toLowerCase().includes(term))
      );
    }

    this.filtered = result;
  }

  abrirModalCrear(): void {
    this.modoEdicion = false;
    this.gerenteSeleccionado = null;
    this.formulario = {
      nombre: '',
      apellido: '',
      email: '',
      password: '',
      telefono: '',
      identificacion: '',
      rol: 'gerente',
      idPlaza: undefined
    };
    this.modalAbierto = true;
  }

  abrirModalEditar(gerente: Gerente): void {
    this.modoEdicion = true;
    this.gerenteSeleccionado = gerente;
    this.formulario = {
      nombre: gerente.nombre,
      apellido: gerente.apellido,
      email: gerente.email,
      password: '', // No se muestra ni edita
      telefono: gerente.telefono,
      identificacion: gerente.identificacion,
      rol: gerente.rol || 'gerente',
      idPlaza: gerente.idPlaza
    };
    this.modalAbierto = true;
  }

  cerrarModal(): void {
    this.modalAbierto = false;
    this.gerenteSeleccionado = null;
  }

  guardarGerente(): void {
    if (this.modoEdicion && this.gerenteSeleccionado) {
      // Actualizar
      const updateData: UpdateGerenteRequest = {
        nombre: this.formulario.nombre,
        apellido: this.formulario.apellido,
        telefono: this.formulario.telefono,
        idPlaza: this.formulario.idPlaza,
        rol: this.formulario.rol // Agregar rol al updateData
      };

      this.gerentesService.update(this.gerenteSeleccionado.id!, updateData).subscribe({
        next: () => {
          this.eventosService.registrarEvento('admin', `Actualizó gerente: ${this.formulario.nombre}`);
          this.eventosService.agregarNotificacion(`Gerente ${this.formulario.nombre} actualizado`, 'info');
          this.cerrarModal();
          this.cargarGerentes();
        },
        error: (err) => {
          this.eventosService.agregarNotificacion('Error al actualizar gerente', 'alerta');
          console.error('Error al actualizar gerente:', err);
          console.error('Error:', err);
        }
      });
    } else {
      // Crear
      this.gerentesService.create(this.formulario).subscribe({
        next: () => {
          this.eventosService.registrarEvento('admin', `Creó gerente: ${this.formulario.nombre}`);
          this.eventosService.agregarNotificacion(`Gerente ${this.formulario.nombre} creado`, 'info');
          this.cerrarModal();
          this.cargarGerentes();
        },
        error: (err) => {
          this.eventosService.agregarNotificacion('Error al crear gerente', 'alerta');
          console.error('Error al crear gerente:', err);
          console.error('Error:', err);
        }
      });
    }
  }

  cambiarRol(gerente: Gerente, nuevoRol: 'admin' | 'gerente' | 'supervisor') {
    // Aquí iría la llamada al backend para actualizar el rol
    gerente.rol = nuevoRol;
    // TODO: Llamar a gerentesService.update(gerente.id, { rol: nuevoRol })
  }
  verDetalle(gerente: Gerente): void {
    this.gerenteDetalle = gerente;
    this.modalDetalleAbierto = true;
  }

  cerrarModalDetalle(): void {
    this.modalDetalleAbierto = false;
    this.gerenteDetalle = null;
  }

  cambiarEstado(gerente: Gerente, nuevoEstado: string): void {
    if (confirm(`¿Cambiar estado del gerente a ${nuevoEstado}?`)) {
      this.gerentesService.cambiarEstado(gerente.id!, nuevoEstado).subscribe({
        next: () => {
          this.cargarGerentes();
          this.eventosService.agregarNotificacion('Estado actualizado exitosamente', 'info');
        },
        error: (err) => {
          this.eventosService.agregarNotificacion('Error al cambiar estado del gerente', 'alerta');
          console.error('Error al cambiar estado:', err);
          console.error('Error:', err);
        }
      });
    }
  }

  eliminarGerente(gerente: Gerente): void {
    if (confirm(`¿Está seguro de eliminar al gerente ${gerente.nombre} ${gerente.apellido}?`)) {
      this.gerentesService.delete(gerente.id!).subscribe({
        next: () => {
          this.eventosService.registrarEvento('admin', `Eliminó gerente: ${gerente.nombre}`);
          this.eventosService.agregarNotificacion(`Gerente ${gerente.nombre} eliminado`, 'alerta');
          this.cargarGerentes();
        },
        error: (err) => {
          this.eventosService.agregarNotificacion('Error al eliminar gerente', 'alerta');
          console.error('Error al eliminar gerente:', err);
          console.error('Error:', err);
        }
      });
    }
  }

  getNombreCompleto(gerente: Gerente): string {
    return `${gerente.nombre} ${gerente.apellido}`;
  }

  getEstadoClass(estado: string): string {
    switch(estado) {
      case 'ACTIVO': return 'badge-activo';
      case 'INACTIVO': return 'badge-inactivo';
      case 'SUSPENDIDO': return 'badge-suspendido';
      default: return '';
    }
  }
}
