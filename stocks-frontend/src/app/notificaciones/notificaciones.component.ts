import { Component } from '@angular/core';
import { EventosService } from '../services/eventos.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-notificaciones',
  standalone: true,
  imports: [CommonModule],
  template: `<div class='notificaciones'>
    <div style="display:flex;align-items:center;justify-content:space-between">
      <h2>Notificaciones</h2>
      <div>
        <button (click)="clearAll()" style="background:#e11d48;color:#fff;padding:6px;border-radius:6px">Limpiar todo</button>
      </div>
    </div>

    <div *ngIf="notificaciones.length===0" style="color:#666;margin-top:12px">No hay notificaciones.</div>
    <ul *ngIf="notificaciones.length>0" style="margin-top:12px;list-style:none;padding:0">
      <li *ngFor="let n of notificaciones; let i = index" style="padding:10px;border-bottom:1px solid #eee;display:flex;align-items:center;justify-content:space-between">
        <div>
          <div style="font-weight:600">{{ n.mensaje }}</div>
          <div style="font-size:12px;color:#888">{{ n.fecha }} <span *ngIf="!n.leida" style="color:#d97706;margin-left:8px">(nueva)</span></div>
        </div>
        <div style="display:flex;gap:8px;align-items:center">
          <button *ngIf="!n.leida" (click)="markRead(i)" title="Marcar leída">Marcar leída</button>
          <button (click)="remove(i)" title="Eliminar" style="color:#fff;background:#ef4444;padding:6px;border-radius:6px">Eliminar</button>
        </div>
      </li>
    </ul>
  </div>`,
  styleUrls: ['./notificaciones.component.css']
})
export class NotificacionesComponent {
  constructor(public eventosService: EventosService) {}

  get notificaciones() {
    return this.eventosService.getNotificaciones();
  }

  clearAll() {
    if (!confirm('¿Eliminar todas las notificaciones?')) return;
    this.eventosService.clearNotificaciones();
  }

  remove(i: number) {
    this.eventosService.removeNotificacion(i);
  }

  markRead(i: number) {
    this.eventosService.markAsRead(i);
  }
}
