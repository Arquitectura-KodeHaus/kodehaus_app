import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SuscripcionService } from '../services/suscripcion.service';
import { ModuloService } from '../services/modulo.service';
import { Suscripcion, ModuloInfoDTO } from '../models/Suscripcion';
import { AddModulosRequest } from '../models/AddModulosRequest';

@Component({
  selector: 'app-suscripciones',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './suscripciones.component.html',
  styleUrl: './suscripciones.component.css'
})
export class SuscripcionesComponent implements OnInit {
  suscripciones: Suscripcion[] = [];
  selectedSuscripcion: Suscripcion | null = null;
  availableModulos: ModuloInfoDTO[] = [];
  selectedModulos: number[] = [];
  loading = false;
  showAddModulosModal = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private suscripcionService: SuscripcionService,
    private moduloService: ModuloService
  ) {}

  ngOnInit() {
    this.loadSuscripciones();
  }

  loadSuscripciones() {
    this.loading = true;
    this.suscripcionService.getSuscripciones().subscribe({
      next: (data) => {
        console.log('Suscripciones loaded:', data);
        this.suscripciones = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading suscripciones:', error);
        this.errorMessage = 'Error al cargar las suscripciones';
        this.loading = false;
      }
    });
  }

  loadSuscripcionesAndUpdateSelection() {
    this.loading = true;
    const currentSelectedId = this.selectedSuscripcion?.id;
    
    this.suscripcionService.getSuscripciones().subscribe({
      next: (data) => {
        console.log('Suscripciones reloaded:', data);
        this.suscripciones = data;
        this.loading = false;
        
        // Update the selected suscripcion with fresh data
        if (currentSelectedId) {
          const updatedSuscripcion = this.suscripciones.find(s => s.id === currentSelectedId);
          if (updatedSuscripcion) {
            this.selectedSuscripcion = updatedSuscripcion;
            console.log('Updated selected suscripcion:', this.selectedSuscripcion);
          }
        }
      },
      error: (error) => {
        console.error('Error loading suscripciones:', error);
        this.errorMessage = 'Error al cargar las suscripciones';
        this.loading = false;
      }
    });
  }

  selectSuscripcion(suscripcion: Suscripcion) {
    this.selectedSuscripcion = suscripcion;
    this.selectedModulos = [];
  }

  openAddModulosModal() {
    this.showAddModulosModal = true;
    this.loadAvailableModulos();
  }

  loadAvailableModulos() {
    this.moduloService.getModulos().subscribe({
      next: (modulos) => {
        // Filter out modulos that are already in the selected suscripcion
        const currentModuloIds = this.selectedSuscripcion?.modulos?.map(m => m.id) || [];
        this.availableModulos = modulos.filter(m => !currentModuloIds.includes(m.id));
      },
      error: (error) => {
        console.error('Error loading modulos:', error);
        this.errorMessage = 'Error al cargar los módulos disponibles';
      }
    });
  }

  toggleModuloSelection(moduloId: number) {
    const index = this.selectedModulos.indexOf(moduloId);
    if (index > -1) {
      this.selectedModulos.splice(index, 1);
    } else {
      this.selectedModulos.push(moduloId);
    }
  }

  addModulosToSuscripcion() {
    if (!this.selectedSuscripcion || this.selectedModulos.length === 0) {
      return;
    }

    const request: AddModulosRequest = {
      moduloIds: this.selectedModulos
    };

    console.log('Sending request:', request);
    console.log('Selected modulos:', this.selectedModulos);

    this.suscripcionService.addModulosToSuscripcion(this.selectedSuscripcion.id, request).subscribe({
      next: (response) => {
        console.log('Response from server:', response);
        
        // Check for success message
        if (response === 'Módulos agregados exitosamente a la suscripción') {
          console.log('✅ Módulos agregados exitosamente');
          this.successMessage = 'Módulos agregados exitosamente';
          this.errorMessage = ''; // Clear any previous errors
          this.showAddModulosModal = false;
          this.selectedModulos = [];
          
          // Reload data and then update selection
          this.loadSuscripcionesAndUpdateSelection();
          
          // Clear success message after 3 seconds
          setTimeout(() => this.successMessage = '', 3000);
        } else {
          console.warn('Unexpected response:', response);
          this.errorMessage = 'Respuesta inesperada del servidor: ' + response;
        }
      },
      error: (error) => {
        console.error('Error adding modulos:', error);
        
        // Check if it's actually a successful response that failed to parse as JSON
        if (error.status === 200 && error.error && typeof error.error === 'string') {
          console.log('Response received as error but status is 200:', error.error);
          // Handle the text response that was treated as an error
          if (error.error === 'Módulos agregados exitosamente a la suscripción') {
            console.log('✅ Módulos agregados exitosamente (from error handler)');
            this.successMessage = 'Módulos agregados exitosamente';
            this.errorMessage = '';
            this.showAddModulosModal = false;
            this.selectedModulos = [];
            
            // Reload data and then update selection
            this.loadSuscripcionesAndUpdateSelection();
            
            setTimeout(() => this.successMessage = '', 3000);
            return;
          }
        }
        
        this.errorMessage = 'Error al agregar los módulos';
      }
    });
  }

  removeModuloFromSuscripcion(moduloId: number) {
    if (!this.selectedSuscripcion) {
      return;
    }

    if (confirm('¿Estás seguro de que quieres eliminar este módulo de la suscripción?')) {
      this.suscripcionService.removeModuloFromSuscripcion(this.selectedSuscripcion.id, moduloId).subscribe({
        next: (response) => {
          console.log('Response from server (remove):', response);
          
          // Check for success (assuming similar success message pattern)
          if (response && (response.includes('eliminado') || response === 'Módulo eliminado exitosamente')) {
            console.log('✅ Módulo eliminado exitosamente');
            this.successMessage = 'Módulo eliminado exitosamente';
            this.errorMessage = ''; // Clear any previous errors
            
            // Reload data and then update selection
            this.loadSuscripcionesAndUpdateSelection();
            
            // Clear success message after 3 seconds
            setTimeout(() => this.successMessage = '', 3000);
          } else {
            console.warn('Unexpected response (remove):', response);
            this.errorMessage = 'Respuesta inesperada del servidor: ' + response;
          }
        },
        error: (error) => {
          console.error('Error removing modulo:', error);
          
          // Check if it's actually a successful response that failed to parse as JSON
          if (error.status === 200 && error.error && typeof error.error === 'string') {
            console.log('Response received as error but status is 200:', error.error);
            // Handle the text response that was treated as an error
            if (error.error.includes('eliminado') || error.error === 'Módulo eliminado exitosamente') {
              console.log('✅ Módulo eliminado exitosamente (from error handler)');
              this.successMessage = 'Módulo eliminado exitosamente';
              this.errorMessage = '';
              
              // Reload data and then update selection
              this.loadSuscripcionesAndUpdateSelection();
              
              setTimeout(() => this.successMessage = '', 3000);
              return;
            }
          }
          
          this.errorMessage = 'Error al eliminar el módulo';
        }
      });
    }
  }

  closeAddModulosModal() {
    this.showAddModulosModal = false;
    this.selectedModulos = [];
  }

  getEstadoClass(estado: string): string {
    switch (estado.toLowerCase()) {
      case 'vigente':
      case 'activa':
      case 'activo':
        return 'status-active';
      case 'cancelada':
        return 'status-cancelled';
      case 'en mora':
        return 'status-overdue';
      default:
        return 'status-default';
    }
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString('es-ES');
  }
}
