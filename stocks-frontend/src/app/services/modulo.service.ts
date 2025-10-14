import { Injectable } from "@angular/core";
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Modulo } from "../models/modulo";

@Injectable({
    providedIn: 'root'
  })
  export class ModuloService {
    private apiUrl = `${environment.apiUrl}/api/modulos`;
  
    constructor(private http: HttpClient) {}
  
    getModulos(): Observable<Modulo[]> {
      return this.http.get<Modulo[]>(this.apiUrl);
    }

    createModulo(modulo: Modulo): Observable<Modulo> {
      return this.http.post<Modulo>(this.apiUrl, modulo);
    }

    updateModulo(modulo: Modulo): Observable<Modulo> {
      return this.http.put<Modulo>(`${this.apiUrl}/${modulo.id}`, modulo);
    }

    deleteModulo(id: number): Observable<void> {
      return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
  
}
  