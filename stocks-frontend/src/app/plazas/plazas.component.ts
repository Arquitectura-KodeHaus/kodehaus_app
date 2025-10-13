import { Component } from '@angular/core';
import { PlazasService } from '../services/plazas.service';
import { Plaza } from '../entity/Plaza';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-plazas',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './plazas.component.html',
  styleUrl: './plazas.component.css'
})
export class PlazasComponent {
  nuevaPlaza: Plaza ={
    id: 0,
    nombre: '',
    contacto: '',
    dominio: '',
    departamento: '',
    ciudad: '',
    direccion: ''
  } 

  constructor(private PlazasService: PlazasService) {}

  showRegisterForm = false;
  showDeleteConfirm = false;

  toggleRegisterForm(){
    this.showRegisterForm = !this.showRegisterForm;
  }

  toggleDelete(){
    this.showDeleteConfirm = !this.showDeleteConfirm
  }

  crearPlaza(): void {
    this.nuevaPlaza.dominio = this.nuevaPlaza.nombre + Math.random()
    console.log("Nueva plaza:", this.nuevaPlaza)

    this.PlazasService.crearPlaza(this.nuevaPlaza).subscribe({
      next: response => {
        console.log('Plaza creada:', response)
        alert("La plaza " + this.nuevaPlaza.nombre + " ha sido creada")},
      
        error: err => {
        console.error('Error al crear la plaza:', err)
        alert("Ocurrio un error al crear la plaza, por favor intente más tarde")}
    });

    this.toggleRegisterForm()
  }
}
