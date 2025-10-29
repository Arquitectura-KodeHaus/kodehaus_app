import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PlanService } from '../services/plan.service';
import { PeticionService } from '../services/peticion.service';
import { Plan } from '../models/plan';
import { peticion } from '../models/peticion';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {

  constructor(private planesService: PlanService, private PeticionService: PeticionService) {}

  showForm = false

  listaPlanes: Plan[] = [];

  peticion: peticion = {
      id: 0,
      correo: '',
      telefono: 0,
      idPlan: 0,
      plaza: ''
    };

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

  toggleForm(id: number): void{
    this.peticion.idPlan = id
    this.showForm = !this.showForm
  }

  sendPetition(): void{
    console.log("Peticion:", this.peticion)
    this.PeticionService.crearPeticion(this.peticion).subscribe({
      next: response => {
        console.log('Peticion creada:', response);
        alert("Todo listo! Pronto un representante se comunicará con usted por medio de correo para guiarlo en el proceso de creacion de cuentas")
        window.location.reload();
      },
      error: err => {
        console.error('Error al crear la peticion:', err);
        alert("Ocurrió un error al enviar la peticion, por favor intente más tarde");
        window.location.reload();
      }
    });
  }

  sendInfoAlert(): void{
    alert("Apreciamos su interes. Pronto un representante se comunicará con usted por medio del correo")
    location.reload()
  }
}
