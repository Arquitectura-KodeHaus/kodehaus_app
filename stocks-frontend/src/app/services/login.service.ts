import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { UserData } from '../entity/userData';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
    private apiUrl = `${environment.apiUrl}/api/cuentas`;
    constructor(private http: HttpClient) {}

    login(data: UserData): Observable<any> {
        return this.http.post<UserData>(`${this.apiUrl}/login`, data);
    }
}
