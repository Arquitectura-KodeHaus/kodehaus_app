import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = 'http://localhost:8081/api/dashboard';

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
    // Usa el endpoint general de plazas (ya existe en tu backend)
    return this.http.get(`http://localhost:8081/api/plazas`);
  }
}
