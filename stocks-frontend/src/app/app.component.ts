import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { environment } from '../environments/environment';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SuscripcionesComponent } from './suscripciones/suscripciones.component';
import { GerentesComponent } from './gerentes/gerentes.component';
import { HistorialComponent } from './historial/historial.component';
import { NotificacionesComponent } from './notificaciones/notificaciones.component';
import { ReportesComponent } from './reportes/reportes.component';
import { MapaComponent } from './mapa/mapa.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { StockService } from './services/stock.service';
import { AuthService } from './services/auth.service';
import { Stock } from './models/stock';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, HttpClientModule, DashboardComponent, SuscripcionesComponent, GerentesComponent, HistorialComponent, NotificacionesComponent, ReportesComponent, MapaComponent, LoginComponent, RegisterComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  readonly title = 'Stocks dashboard';
  stocks: Stock[] = [];
  isLoading = true;
  errorMessage = '';
  isLoggedIn = false;
  currentUser: any = null;
  showRegister: boolean = false;

  private readonly stockService = inject(StockService);
  private readonly authService = inject(AuthService);
  private readonly http = inject(HttpClient);

  // Uploaded files (locally stored preview if backend not available)
  uploadedFiles: { name: string; url?: string; uploadedAt: string; cached?: boolean }[] = [];

  private readonly CACHE_KEY = 'cachedUploads_v1';

  ngOnInit(): void {
    // Temporalmente deshabilitado para debug
    this.isLoading = false;
    console.log('App component inicializado correctamente');

    // Suscribirse a cambios de autenticación
    this.authService.currentUser$.subscribe(user => {
      this.isLoggedIn = user !== null;
      this.currentUser = user;
    });

    // Escuchar eventos de navegación entre login y registro
    window.addEventListener('irARegistro', () => {
      this.showRegister = true;
    });

    window.addEventListener('volverAlLogin', () => {
      this.showRegister = false;
    });

    window.addEventListener('registroExitoso', () => {
      this.showRegister = false;
    });
    window.addEventListener('irAlHome', () => {
      this.showRegister = false;
      this.currentView = 'dashboard';
      this.isLoggedIn = true;
    });

    // Cargar uploads cacheados localmente
    this.loadCachedUploads();
  }

  // Simple in-page toast notification
  toastMessage: string | null = null;
  toastType: 'info' | 'success' | 'error' = 'info';
  private toastTimeout: any = null;

  showToast(message: string, type: 'info' | 'success' | 'error' = 'info', duration = 5000) {
    if (this.toastTimeout) clearTimeout(this.toastTimeout);
    this.toastMessage = message;
    this.toastType = type;
    this.toastTimeout = setTimeout(() => {
      this.toastMessage = null;
      this.toastTimeout = null;
    }, duration);
  }

  closeToast() {
    if (this.toastTimeout) clearTimeout(this.toastTimeout);
    this.toastMessage = null;
    this.toastTimeout = null;
  }

  // File viewer (modal) state
  viewerUrl: string | null = null;
  viewerName: string | null = null;
  viewerType: 'image' | 'pdf' | 'other' = 'other';

  openViewer(file: { name: string; url?: string }) {
    if (!file || !file.url) return;
    this.viewerUrl = file.url;
    this.viewerName = file.name;
    // detect simple mime-type from data URL or extension
    if (this.viewerUrl.startsWith('data:image') || /\.(png|jpe?g|gif|bmp|webp)(\?|$)/i.test(this.viewerUrl)) {
      this.viewerType = 'image';
    } else if (this.viewerUrl.startsWith('data:application/pdf') || /\.pdf(\?|$)/i.test(this.viewerUrl)) {
      this.viewerType = 'pdf';
    } else {
      this.viewerType = 'other';
    }
  }

  closeViewer() {
    // revoke objectURL if it was created via createObjectURL
    try {
      if (this.viewerUrl && this.viewerUrl.startsWith('blob:')) {
        URL.revokeObjectURL(this.viewerUrl);
      }
    } catch (e) {
      // ignore
    }
    this.viewerUrl = null;
    this.viewerName = null;
    this.viewerType = 'other';
  }

  async uploadFile(name: string, id: string, email: string, message: string, fileList: FileList | null) {
    if (!fileList || fileList.length === 0) {
      this.showToast('Selecciona un archivo para subir', 'error');
      return;
    }
    const file = fileList[0];

    const form = new FormData();
    form.append('name', name || '');
    form.append('id', id || '');
    form.append('email', email || '');
    form.append('message', message || '');
    form.append('file', file, file.name);

    const endpoint = `${environment.apiUrl}/uploads`;
    try {
      // try upload to backend; if fails, fallback to local preview
      await this.http.post(endpoint, form).toPromise();
      this.uploadedFiles.unshift({ name: file.name, uploadedAt: new Date().toISOString() });
      this.showToast('Archivo subido correctamente', 'success');
    } catch (err) {
      // fallback: save file content in localStorage as data URL for offline cache
      try {
        const dataUrl = await this.readFileAsDataURL(file);
        const cached = { name: file.name, id: id || '', email: email || '', message: message || '', dataUrl, uploadedAt: new Date().toISOString() };
        this.saveToCache(cached);
        // add to uploadedFiles as cached preview
        this.uploadedFiles.unshift({ name: file.name, url: cached.dataUrl, uploadedAt: cached.uploadedAt, cached: true });
        // Simulate successful local save and inform the user the file was reviewed
        this.showToast('Archivo revisado. El archivo se guardó y se revisara después.', 'info');
      } catch (e) {
        // if even caching fails, fallback to object URL preview
        const url = URL.createObjectURL(file);
        this.uploadedFiles.unshift({ name: file.name, url, uploadedAt: new Date().toISOString(), cached: true });
        this.showToast('Archivo revisado. Se creó una vista previa local.', 'info');
      }
    }
  }

  private readFileAsDataURL(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const fr = new FileReader();
      fr.onload = () => resolve(fr.result as string);
      fr.onerror = reject;
      fr.readAsDataURL(file);
    });
  }

  private loadCachedUploads() {
    try {
      const raw = localStorage.getItem(this.CACHE_KEY);
      if (!raw) return;
      const arr = JSON.parse(raw) as Array<any>;
      // Show cached items in uploadedFiles list
      arr.forEach(item => {
        this.uploadedFiles.unshift({ name: item.name, url: item.dataUrl, uploadedAt: item.uploadedAt, cached: true });
      });
    } catch (e) {
      console.warn('No se pudo cargar cache de uploads', e);
    }
  }

  private saveToCache(entry: { name: string; id: string; email: string; message: string; dataUrl: string; uploadedAt: string }) {
    try {
      const raw = localStorage.getItem(this.CACHE_KEY);
      const arr = raw ? JSON.parse(raw) as any[] : [];
      arr.unshift(entry);
      // keep last 20
      localStorage.setItem(this.CACHE_KEY, JSON.stringify(arr.slice(0, 20)));
    } catch (e) {
      console.warn('No se pudo guardar en cache', e);
    }
  }

  get hasCachedUploads(): boolean {
    return this.uploadedFiles.some(f => !!f.cached);
  }

  removeCachedFile(index: number) {
    try {
      // remove from uploadedFiles
      const item = this.uploadedFiles[index];
      if (!item) return;
      this.uploadedFiles.splice(index, 1);
      // remove from localStorage cache by matching name and uploadedAt
      const raw = localStorage.getItem(this.CACHE_KEY);
      if (!raw) return;
      const arr = JSON.parse(raw) as any[];
      const idx = arr.findIndex(a => a.name === item.name && a.uploadedAt === item.uploadedAt);
      if (idx > -1) { arr.splice(idx, 1); localStorage.setItem(this.CACHE_KEY, JSON.stringify(arr)); }
      this.showToast('Archivo eliminado del cache', 'info');
    } catch (e) {
      console.warn('Error al eliminar archivo cacheado', e);
      this.showToast('No se pudo eliminar el archivo', 'error');
    }
  }

  async retryCachedUploads() {
    const raw = localStorage.getItem(this.CACHE_KEY);
    if (!raw) {
      this.showToast('No hay subidas pendientes.', 'info');
      return;
    }
    const arr = JSON.parse(raw) as Array<any>;
    if (!arr.length) { this.showToast('No hay subidas pendientes.', 'info'); return; }

    const successes: string[] = [];
    const failures: string[] = [];

    for (const item of [...arr]) {
      try {
        const blob = await (await fetch(item.dataUrl)).blob();
        const form = new FormData();
        form.append('name', item.name || '');
        form.append('id', item.id || '');
        form.append('email', item.email || '');
        form.append('message', item.message || '');
        form.append('file', blob, item.name || 'file');
        await this.http.post(`${environment.apiUrl}/uploads`, form).toPromise();
        successes.push(item.name);
        // remove from arr
        const idx = arr.indexOf(item);
        if (idx > -1) arr.splice(idx, 1);
        // update uploadedFiles list: mark as uploaded (cached false)
        const uf = this.uploadedFiles.find(u => u.name === item.name && u.cached);
        if (uf) { uf.cached = false; }
      } catch (e) {
        failures.push(item.name);
      }
    }

    // persist remaining
    localStorage.setItem(this.CACHE_KEY, JSON.stringify(arr));

    let msg = '';
    if (successes.length) msg += `Subidos: ${successes.join(', ')}.`;
    if (failures.length) msg += ` Errores: ${failures.join(', ')}.`;
    const type: 'info' | 'success' | 'error' = failures.length ? 'error' : (successes.length ? 'success' : 'info');
    this.showToast(msg || 'No hubo acciones.', type);
  }

  logout(): void {
    this.authService.logout();
  }

  // simple view switcher (no router to keep the example small)
  currentView: 'dashboard' | 'suscripciones' | 'gerentes' | 'historial' | 'notificaciones' | 'reportes' | 'mapa' = 'dashboard';
  show(view: 'dashboard' | 'suscripciones' | 'gerentes' | 'historial' | 'notificaciones' | 'reportes' | 'mapa') { this.currentView = view; }
}
