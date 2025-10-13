import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Plaza } from '../entity/Plaza';

@Injectable({
  providedIn: 'root'
})
export class PlazasService {
  private apiUrl = `${environment.apiUrl}/api/plazas`;

  constructor(private http: HttpClient) {}

  getPlazasActivas(): Observable<Plaza[]> {
        return this.http.get<Plaza[]>(`${this.apiUrl}/find/activas`)
    }
    
    crearPlaza(plaza: Plaza): Observable<Plaza> {
        return this.http.post<Plaza>(`${this.apiUrl}`, plaza);
    }

    deletePlaza(id: bigint): Observable<any> {
        return this.http.delete<Plaza>(`${this.apiUrl}/${id}`);
    }
}
