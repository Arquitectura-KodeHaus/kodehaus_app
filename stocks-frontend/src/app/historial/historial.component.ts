import { Component } from '@angular/core';
import { EventosService } from '../services/eventos.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-historial',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `<div class='historial'>
    <div style="display:flex;align-items:center;gap:12px;justify-content:space-between">
      <h2>Historial de Acciones</h2>
      <div>
        <input placeholder="Filtrar..." [(ngModel)]="filter" style="padding:6px;border:1px solid #ccc;border-radius:6px" />
        <button (click)="exportar()" style="margin-left:8px">Exportar</button>
        <button (click)="clear()" style="margin-left:8px;color:#fff;background:#e11d48;padding:6px;border-radius:6px">Limpiar</button>
      </div>
    </div>

    <div *ngIf="historial.length===0" style="color:#666;margin-top:12px">No hay entradas en el historial.</div>
    <ul *ngIf="historial.length>0" style="margin-top:12px">
      <li *ngFor="let accion of filtered" style="padding:8px 0;border-bottom:1px solid #eee">
        <div><b>{{ accion.usuario }}</b> <span style="color:#666">{{ accion.accion }}</span></div>
        <div style="color:#888;font-size:12px">{{ accion.fecha }}</div>
      </li>
    </ul>
  </div>`,
  styleUrls: ['./historial.component.css']
})
export class HistorialComponent {
  filter = '';
  constructor(public eventosService: EventosService) {}

  get historial() {
    return this.eventosService.getHistorial();
  }

  get filtered() {
    const f = this.filter && this.filter.trim().toLowerCase();
    if (!f) return this.historial;
    return this.historial.filter(h => (h.usuario + ' ' + h.accion + ' ' + h.fecha).toLowerCase().includes(f));
  }

  clear() {
    if (!confirm('¿Limpiar todo el historial? Esta acción no se puede deshacer.')) return;
    this.eventosService.clearHistorial();
  }

  exportar() {
    const data = JSON.stringify(this.historial, null, 2);
    const blob = new Blob([data], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `historial_${new Date().toISOString()}.json`;
    a.click();
    URL.revokeObjectURL(url);
  }
}
