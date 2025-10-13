import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Plaza } from '../entity/Plaza';

@Injectable({
  providedIn: 'root'
})
export class PlazasService {
  private apiUrl = 'http://localhost:8080/api/plazas';

  constructor(private http: HttpClient) {}

  getPlazasActivas(): Observable<any> {
    return this.http.get(`${this.apiUrl}/activas`)
  }

    crearPlaza(plaza: Plaza): Observable<Plaza> {
    return this.http.post<Plaza>(`${this.apiUrl}`, plaza);
  }
}
