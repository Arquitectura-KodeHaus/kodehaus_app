import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { peticion } from '../entity/Peticion';

@Injectable({
  providedIn: 'root'
})
export class PeticionService {
  private apiUrl = `${environment.apiUrl}/api/peticiones`;

  constructor(private http: HttpClient) {}

    crearPeticion(peticion: peticion): Observable<peticion> {
        return this.http.post<peticion>(`${this.apiUrl}`, peticion);
    }

    getPeticiones(): Observable<peticion[]> {
      return this.http.get<peticion[]>(`${this.apiUrl}`)
    }
    
    deletePeticion(id: number): Observable<any> {
      return this.http.delete<peticion>(`${this.apiUrl}/${id}`);
    }
}
