import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SuscripcionesService } from '../services/suscripciones.service';

@Component({
  selector: 'app-suscripciones',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './suscripciones.component.html',
  styleUrls: ['./suscripciones.component.css']
})
export class SuscripcionesComponent implements OnInit {
  suscripciones: any[] = [];
  filtered: any[] = [];
  estadoPagoFilter = 'Todos';
  estadoSuscripcionFilter = 'Todos';
  isLoading = true;
  error = '';

  constructor(private svc: SuscripcionesService) {}

  ngOnInit(): void {
    // Datos temporales para debug
    this.suscripciones = [
      {
        id: 1,
        plan: 'Premium',
        plaza: 'Plaza Central',
        periodicidad: 'Mensual',
        ultimoPago: '12/09/2025',
        renovacion: '12/10/2025',
        estadoSuscripcion: 'Vigente',
        estadoPago: 'Al día'
      },
      {
        id: 2,
        plan: 'Avanzado',
        plaza: 'Plaza Norte',
        periodicidad: 'Trimestral',
        ultimoPago: '02/07/2025',
        renovacion: '02/10/2025',
        estadoSuscripcion: 'Vigente',
        estadoPago: 'En mora'
      }
    ];
    this.applyFilters();
    this.isLoading = false;
  }

  applyFilters(): void {
    this.filtered = this.suscripciones.filter(s => {
      const pagoOk = this.estadoPagoFilter === 'Todos' || s.estadoPago === this.estadoPagoFilter;
      const susOk = this.estadoSuscripcionFilter === 'Todos' || s.estadoSuscripcion === this.estadoSuscripcionFilter;
      return pagoOk && susOk;
    });
  }

  // Modal y cambio de estado
  modalAbierto = false;
  suscripcionSeleccionada: any = null;

  verSuscripcion(s: any) {
    this.suscripcionSeleccionada = { ...s };
    this.modalAbierto = true;
  }

  cerrarModal() {
    this.modalAbierto = false;
    this.suscripcionSeleccionada = null;
  }

  cambiarEstado(s: any) {
    const req = {
      ...s,
      estado: s.estadoSuscripcion
    };
    this.svc.update(s.id, req).subscribe({
      next: updated => {
        s.estadoSuscripcion = updated.estado;
      },
      error: err => {
        this.error = 'No se pudo actualizar el estado de la suscripción';
      }
    });
  }
}
