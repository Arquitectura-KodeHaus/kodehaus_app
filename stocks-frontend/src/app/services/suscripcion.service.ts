import { Injectable } from "@angular/core";
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AddModulosRequest } from "../models/AddModulosRequest";
import { Suscripcion } from "../models/Suscripcion";

@Injectable({
    providedIn: 'root'
  })
  export class SuscripcionService {
    private apiUrl = `${environment.apiUrl}/api/suscripciones`;
  
    constructor(private http: HttpClient) {}
  
    getSuscripciones(): Observable<Suscripcion[]> {
      return this.http.get<Suscripcion[]>(this.apiUrl);
    }

    getSuscripcionById(id: number): Observable<Suscripcion> {
        return this.http.get<Suscripcion>(this.apiUrl + '/' + id);
    }
  
  addModulosToSuscripcion(suscripcionId: number, modulosIds:AddModulosRequest): Observable<string> {
    console.log('Service: Adding modulos to suscripcion', suscripcionId, modulosIds);
    return this.http.post(`${this.apiUrl}/${suscripcionId}/modulos`, modulosIds, { responseType: 'text' });
  }

  removeModuloFromSuscripcion(suscripcionId: number, moduloId: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${suscripcionId}/modulos/${moduloId}`, { responseType: 'text' });
  }
  
   
  }
  