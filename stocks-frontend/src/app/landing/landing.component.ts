import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PlanService } from '../services/plan.service';
import { Plan } from '../models/plan';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {

  constructor(private planesService: PlanService) {}

  showForm = false

  listaPlanes: Plan[] = [];

  contactMail: string;

  ngOnInit(): void {
      this.planesService.getPlanes().subscribe({
        next: (data) =>{
          this.listaPlanes = data
          console.log("Planes: ", this.listaPlanes)
        },
        error: (err) => {
          console.error('Error al obtener las plazas:', err);
          alert('Error al cargar las plazas: ' + (err.error?.message || err.message));
        }
      })
    }

  toggleForm(): void{
    this.showForm = !this.showForm
  }

  sendConfirmAlert(): void{
    alert("Todo listo! Pronto un representante se comunicará con usted por medio de correo para guiarlo en el proceso de creacion de cuentas")
  }

  sendInfoAlert(): void{
    alert("Apreciamos su interes. Pronto un representante se comunicará con usted por medio del correo")
  }
}
