import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class SuscripcionesService {
  private readonly baseUrl = `${environment.apiUrl}/api/suscripciones`;

  constructor(private http: HttpClient) {}

  list(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  create(suscripcion: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, suscripcion);
  }

  update(id: number, suscripcion: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, suscripcion);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getStats(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/stats`);
  }
}
