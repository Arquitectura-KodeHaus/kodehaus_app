import { Injectable } from "@angular/core";
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Plan } from "../models/plan";

@Injectable({
    providedIn: 'root'
  })
  export class PlanService {
    private apiUrl = `${environment.apiUrl}/api/planes`;
  
    constructor(private http: HttpClient) {}
  
    getPlanes(): Observable<Plan[]> {
      return this.http.get<Plan[]>(this.apiUrl);
    }
  
    createPlan(plan: Plan): Observable<Plan[]> {
      return this.http.post<Plan[]>(`${this.apiUrl}`, plan);
    }
  
    updatePlan(plan: Plan): Observable<Plan> {
      return this.http.put<Plan>(`${this.apiUrl}/${plan.id}`, plan);
    }
  
    deletePlan(id: number): Observable<any> {
      return this.http.delete(`${this.apiUrl}/${id}`);
    }
  }
  