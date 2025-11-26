import { Injectable } from '@angular/core';

export interface Evento {
  usuario: string;
  accion: string;
  fecha: string;
}

export interface Notificacion {
  mensaje: string;
  tipo: 'alerta' | 'info';
  fecha?: string;
  leida?: boolean;
}

@Injectable({ providedIn: 'root' })
export class EventosService {
  historial: Evento[] = [];
  notificaciones: Notificacion[] = [];

  private readonly HIST_KEY = 'app_historial_v1';
  private readonly NOT_KEY = 'app_notificaciones_v1';

  constructor() {
    this.loadFromStorage();
  }

  registrarEvento(usuario: string, accion: string) {
    const fecha = new Date().toLocaleString();
    this.historial.unshift({ usuario, accion, fecha });
    this.saveHistorial();
  }

  agregarNotificacion(mensaje: string, tipo: 'alerta' | 'info' = 'info') {
    const fecha = new Date().toLocaleString();
    this.notificaciones.unshift({ mensaje, tipo, fecha, leida: false });
    this.saveNotificaciones();
  }

  getHistorial() {
    return this.historial;
  }

  getNotificaciones() {
    return this.notificaciones;
  }

  clearHistorial() {
    this.historial = [];
    this.saveHistorial();
  }

  clearNotificaciones() {
    this.notificaciones = [];
    this.saveNotificaciones();
  }

  removeNotificacion(index: number) {
    if (index >= 0 && index < this.notificaciones.length) {
      this.notificaciones.splice(index, 1);
      this.saveNotificaciones();
    }
  }

  markAsRead(index: number) {
    const n = this.notificaciones[index];
    if (n) { n.leida = true; this.saveNotificaciones(); }
  }

  private saveHistorial() {
    try { localStorage.setItem(this.HIST_KEY, JSON.stringify(this.historial.slice(0, 200))); } catch (e) { console.warn('No se pudo guardar historial', e); }
  }

  private saveNotificaciones() {
    try { localStorage.setItem(this.NOT_KEY, JSON.stringify(this.notificaciones.slice(0, 200))); } catch (e) { console.warn('No se pudo guardar notificaciones', e); }
  }

  private loadFromStorage() {
    try {
      const h = localStorage.getItem(this.HIST_KEY);
      if (h) this.historial = JSON.parse(h) as Evento[];
      const n = localStorage.getItem(this.NOT_KEY);
      if (n) this.notificaciones = JSON.parse(n) as Notificacion[];
    } catch (e) {
      console.warn('No se pudo cargar eventos desde storage', e);
    }
  }
}
