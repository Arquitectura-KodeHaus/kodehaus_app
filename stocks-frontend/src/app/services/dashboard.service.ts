import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = `${environment.apiUrl}/api/dashboard`;

  constructor(private http: HttpClient) {}

  getModulosActivos(): Observable<any> {
    return this.http.get(`${this.apiUrl}/modulos-activos`);
  }

  getModuloMasUsado(): Observable<any> {
    return this.http.get(`${this.apiUrl}/modulo-mas-usado`);
  }

  getTotalGanancias(): Observable<any> {
    return this.http.get(`${this.apiUrl}/total-ganancias`);
  }

  getPlazasActivas(): Observable<any> {
    return this.http.get(`${this.apiUrl}/plazas-activas`);
  }
}
