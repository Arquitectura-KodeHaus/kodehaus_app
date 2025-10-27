import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Gerente, CreateGerenteRequest, UpdateGerenteRequest } from '../models/gerente';

@Injectable({ providedIn: 'root' })
export class GerentesService {
  private readonly baseUrl = `${environment.apiUrl}/api/gerentes`;

  constructor(private http: HttpClient) {}

  /**
   * Obtener todos los gerentes
   */
  list(): Observable<Gerente[]> {
    return this.http.get<Gerente[]>(this.baseUrl);
  }

  /**
   * Obtener gerente por ID
   */
  getById(id: number): Observable<Gerente> {
    return this.http.get<Gerente>(`${this.baseUrl}/${id}`);
  }

  /**
   * Obtener gerente por email
   */
  getByEmail(email: string): Observable<Gerente> {
    return this.http.get<Gerente>(`${this.baseUrl}/email/${email}`);
  }

  /**
   * Obtener gerente por plaza
   */
  getByPlaza(idPlaza: number): Observable<Gerente> {
    return this.http.get<Gerente>(`${this.baseUrl}/plaza/${idPlaza}`);
  }

  /**
   * Obtener gerentes por estado
   */
  getByEstado(estado: string): Observable<Gerente[]> {
    return this.http.get<Gerente[]>(`${this.baseUrl}/estado/${estado}`);
  }

  /**
   * Crear nuevo gerente
   */
  create(gerente: CreateGerenteRequest): Observable<Gerente> {
    return this.http.post<Gerente>(this.baseUrl, gerente);
  }

  /**
   * Actualizar gerente
   */
  update(id: number, gerente: UpdateGerenteRequest): Observable<Gerente> {
    return this.http.put<Gerente>(`${this.baseUrl}/${id}`, gerente);
  }

  /**
   * Asignar plaza a gerente
   */
  asignarPlaza(idGerente: number, idPlaza: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${idGerente}/asignar-plaza/${idPlaza}`, {});
  }

  /**
   * Cambiar estado de gerente
   */
  cambiarEstado(id: number, estado: string): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}/estado`, { estado });
  }

  /**
   * Eliminar gerente
   */
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
