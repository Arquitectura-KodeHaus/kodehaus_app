import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface Plaza {
  id: number;
  nombre: string;
  contacto: string;
  dominio: string;
  fechaCreacion: string;
  departamento: string;
  ciudad: string;
  direccion: string;
}

@Injectable({ providedIn: 'root' })
export class PlazaService {
  private readonly baseUrl = `${environment.apiUrl}/api/plazas`;

  constructor(private http: HttpClient) {}

  /**
   * Obtener todas las plazas
   */
  list(): Observable<Plaza[]> {
    return this.http.get<Plaza[]>(this.baseUrl);
  }

  /**
   * Obtener plaza por ID
   */
  getById(id: number): Observable<Plaza> {
    return this.http.get<Plaza>(`${this.baseUrl}/${id}`);
  }
}
