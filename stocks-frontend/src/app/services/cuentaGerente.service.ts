import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { cuentaGerente } from '../models/cuentaGerente';

@Injectable({
  providedIn: 'root'
})
export class cuentaGerenteService {
  private apiUrl = `${environment.apiUrl}/api/cuentas/gerente`;

  constructor(private http: HttpClient) {}
    
    getGerentePlaza(id: number): Observable<cuentaGerente> {
        return this.http.get<cuentaGerente>(`${this.apiUrl}/plaza/${id}`);
    }

    getGerente(id: number): Observable<cuentaGerente> {
        return this.http.get<cuentaGerente>(`${this.apiUrl}/find/${id}`);
    }

    crearGerente(gerente: cuentaGerente): Observable<cuentaGerente> {
        return this.http.post<cuentaGerente>(`${this.apiUrl}/create`, gerente);
    }
}
