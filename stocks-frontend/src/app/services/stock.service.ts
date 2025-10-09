import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Stock } from '../models/stock';

@Injectable({ providedIn: 'root' })
export class StockService {
  private readonly baseUrl = `${environment.apiUrl}/api/stocks`;

  constructor(private readonly http: HttpClient) {}

  list(): Observable<Stock[]> {
    return this.http.get<Stock[]>(this.baseUrl);
  }
}
