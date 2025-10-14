import { Injectable } from "@angular/core";
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ModuloInfoDTO } from "../models/Suscripcion";

@Injectable({
    providedIn: 'root'
  })
  export class ModuloService {
    private apiUrl = `${environment.apiUrl}/api/modulos`;
  
    constructor(private http: HttpClient) {}
  
    getModulos(): Observable<ModuloInfoDTO[]> {
      return this.http.get<ModuloInfoDTO[]>(this.apiUrl);
    }
  
}
  