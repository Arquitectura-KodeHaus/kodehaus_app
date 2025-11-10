import { Component } from '@angular/core';
import { PeticionService } from '../services/peticion.service';
import { PlanService } from '../services/plan.service';
import { peticion } from '../entity/Peticion';
import { Plan } from '../models/plan';
import { forkJoin } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-peticiones',
  standalone: true,
  imports: [],
  templateUrl: './peticiones.component.html',
  styleUrl: './peticiones.component.css'
})
export class PeticionesComponent {

  constructor(private peticionService: PeticionService, private planService: PlanService, private router: Router){}

  listaPeticiones: (peticion & { planTipo?: string })[] = [];

   ngOnInit() {
    this.peticionService.getPeticiones().subscribe({
      next: (data) => {
        this.listaPeticiones = data;
        console.log('Peticiones cargadas:', this.listaPeticiones);

        // Create observables for each plan
        const planRequests = this.listaPeticiones.map(p =>
          this.planService.findPlan(p.idPlan)
        );

        // Fetch all plans in parallel
        forkJoin(planRequests).subscribe({
          next: (planes: Plan[]) => {
            // Attach the plan type to each peticion
            this.listaPeticiones = this.listaPeticiones.map((p, i) => ({
              ...p,
              planTipo: planes[i]?.tipo || 'Sin plan'
            }));
            console.log('Peticiones con plan tipo:', this.listaPeticiones);
          },
          error: (err) => {
            console.error('Error al obtener los planes:', err);
          }
        });
      },
      error: (err) => {
        console.error('Error al obtener las peticiones:', err);
        alert('Error al cargar las peticiones: ' + (err.error?.message || err.message));
      }
    });
  }

  deleteRequest(id: number): void{
    this.peticionService.deletePeticion(id).subscribe({
      next: () => {
        window.location.reload();
      },
      error: (err) => {
        console.error('Error al eliminar la peticion:', err);
        alert('Ocurrió un error: ' + (err.error?.message || err.message));
        window.location.reload();
      }
    });
  }

  logout(): void{
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }
}
