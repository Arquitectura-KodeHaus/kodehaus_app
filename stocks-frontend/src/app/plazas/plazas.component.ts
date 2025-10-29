import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlazasService } from '../services/plazas.service';
import { PlanService } from '../services/plan.service';
import { Plaza } from '../entity/Plaza';
import { CreatePlaza } from '../entity/CreatePlaza';
import { Plan } from '../models/plan';
import { FormsModule } from '@angular/forms';
import { Modulo } from '../models/modulo';


@Component({
  selector: 'app-plazas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './plazas.component.html',
  styleUrl: './plazas.component.css'
})
export class PlazasComponent implements OnInit {
  nuevaPlaza: CreatePlaza = {
    id: 0,
    nombre: '',
    contacto: '',
    dominio: '',
    departamento: '',
    ciudad: '',
    direccion: '',
    planId: 0,
    fechaCreacion: null
  } 

  plazaInfo: Plaza = {
    id: 0,
    nombre: '',
    contacto: '',
    dominio: '',
    departamento: '',
    ciudad: '',
    direccion: '',
    fechaCreacion: new Date(),
    plan: ''
  }

  constructor(
    private PlazasService: PlazasService,
    private PlanService: PlanService
  ) {}

  planes: Plan[] = [];
  selectedPlanId: number = 0;

  showRegisterForm = false;
  showDeleteConfirm = false;
  showInfoPanel = false;
  showMofidyForm = false;

  DeleteId: bigint;
  DeleteName = '';
  modfiyId: bigint;

  listaPlazas: Plaza[];

  modulosPlaza: Modulo[] = [];

  ngOnInit(){
    // Load plazas
    this.PlazasService.getPlazasActivas().subscribe({
      next: (data) => {
        this.listaPlazas = data;
        console.log("Plazas cargadas", this.listaPlazas)
      },
      error: (err) => {
        console.error('Error al obtener las plazas:', err);
        alert('Error al cargar las plazas: ' + (err.error?.message || err.message));
      }
    });

    // Load plans
    this.PlanService.getPlanes().subscribe({
      next: (data) => {
        this.planes = data;
        console.log("Planes cargados", this.planes);
        console.log("Número de planes:", this.planes.length);
        console.log("Primer plan:", this.planes[0]);
      },
      error: (err) => {
        console.error('Error al obtener los planes:', err);
        alert('Error al cargar los planes: ' + (err.error?.message || err.message));
      }
    });
  }

  toggleRegisterForm(): void{
    this.showRegisterForm = !this.showRegisterForm;
  }

  toggleModifyForm(id): void{
    this.showMofidyForm = !this.showMofidyForm;
    this.modfiyId = id

    if(this.nuevaPlaza.nombre == ''){
      this.PlazasService.findPlaza(id).subscribe({
        next: (data: Plaza) => {
          // Convert Plaza to CreatePlaza for editing
          this.nuevaPlaza = {
            id: data.id,
            nombre: data.nombre,
            contacto: data.contacto,
            dominio: data.dominio,
            departamento: data.departamento,
            ciudad: data.ciudad,
            direccion: data.direccion,
            planId: 0, // We'll need to get this from somewhere or handle it differently
            fechaCreacion: data.fechaCreacion
          }
          console.log("Informacion de plaza: ", this.nuevaPlaza)
        }
      })
    }

    else{
      this.nuevaPlaza = {
        id: 0,
        nombre: '',
        contacto: '',
        dominio: '',
        departamento: '',
        ciudad: '',
        direccion: '',
        planId: 0,
        fechaCreacion: null
      }
    }
  }

  toggleDelete(id, nombre): void{
    this.DeleteId = id;
    this.DeleteName = nombre;
    this.showDeleteConfirm = !this.showDeleteConfirm
  }

  toggleInfoPanel(): void{
    this.showInfoPanel = !this.showInfoPanel;
  }

  showPlazaInfo(id): void{
    this.toggleInfoPanel();

    this.PlazasService.findPlaza(id).subscribe({
      next: (data: Plaza) => {
        this.plazaInfo = data
        console.log("Informacion de plaza: ", this.plazaInfo)
      }
    })
  }

  crearPlaza(): void {
    // Set the plan ID from the selected plan
    this.nuevaPlaza.planId = this.selectedPlanId;
    this.nuevaPlaza.dominio = this.nuevaPlaza.nombre + Math.random();
    this.nuevaPlaza.fechaCreacion = new Date();
    console.log("Nueva plaza:", this.nuevaPlaza);

    this.PlazasService.crearPlaza(this.nuevaPlaza).subscribe({
      next: response => {
        console.log('Plaza creada:', response);
        alert("La plaza " + this.nuevaPlaza.nombre + " ha sido creada");
        window.location.reload();
      },
      error: err => {
        console.error('Error al crear la plaza:', err);
        alert("Ocurrió un error al crear la plaza, por favor intente más tarde");
        window.location.reload();
      }
    });

    this.toggleRegisterForm();
  }

  actualizarPlaza(): void {
    console.log("Nueva información:", this.nuevaPlaza)

    // Create a proper CreatePlaza object for the update
    const updateData: CreatePlaza = {
      id: this.nuevaPlaza.id,
      nombre: this.nuevaPlaza.nombre,
      contacto: this.nuevaPlaza.contacto,
      dominio: this.nuevaPlaza.dominio,
      departamento: this.nuevaPlaza.departamento,
      ciudad: this.nuevaPlaza.ciudad,
      direccion: this.nuevaPlaza.direccion,
      planId: this.nuevaPlaza.planId,
      fechaCreacion: this.nuevaPlaza.fechaCreacion
    };

    this.PlazasService.updatePlaza(this.modfiyId, updateData).subscribe({
      next: response => {
        console.log('Plaza actualizadas', response)
        alert("La información de la plaza ha sido actualizada")
        window.location.reload();},
      
        error: err => {
        console.error('Error al actualizar la plaza:', err)
        alert("Ocurrio un error al actualizar la información de la plaza, por favor intente más tarde")
        window.location.reload();}
    });

    this.toggleModifyForm(null)
  }

  deletePlaza(): void{
    this.toggleDelete(this.DeleteId, this.DeleteName)
    console.log("Eliminar la plaza con Id " + this.DeleteId)

      this.PlazasService.deletePlaza(this.DeleteId).subscribe({
      next: () => {
        alert('La plaza ha sido eliminada con éxito');
        window.location.reload();
      },
      error: (err) => {
        console.error('Error al eliminar la plaza:', err);
        alert('Ocurrió un error al eliminar la plaza: ' + (err.error?.message || err.message));
        window.location.reload();
      }
    });
  }
}
