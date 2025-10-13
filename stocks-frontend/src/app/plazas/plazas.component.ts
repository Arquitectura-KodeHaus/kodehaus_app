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
    direccion: '',
    fechaCreacion: null
  } 

  plazaInfo: Plaza ={
    id: 0,
    nombre: '',
    contacto: '',
    dominio: '',
    departamento: '',
    ciudad: '',
    direccion: '',
    fechaCreacion: null
  }

  constructor(private PlazasService: PlazasService) {}

  showRegisterForm = false;
  showDeleteConfirm = false;
  showInfoPanel = false;
  showMofidyForm = false;

  DeleteId: bigint;
  DeleteName = '';
  modfiyId: bigint;

  listaPlazas: Plaza[];

  ngOnInit(){
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
  }

  toggleRegisterForm(): void{
    this.showRegisterForm = !this.showRegisterForm;
  }

  toggleModifyForm(id): void{
    this.showMofidyForm = !this.showMofidyForm;
    this.modfiyId = id

    if(this.nuevaPlaza.nombre == ''){
      this.PlazasService.findPlaza(id).subscribe({
        next: (data) => {
          this.nuevaPlaza = data
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
      next: (data) => {
        this.plazaInfo = data
        console.log("Informacion de plaza: ", this.plazaInfo)
      }
    })
  }

  crearPlaza(): void {
    this.nuevaPlaza.dominio = this.nuevaPlaza.nombre + Math.random()
    console.log("Nueva plaza:", this.nuevaPlaza)

    this.PlazasService.crearPlaza(this.nuevaPlaza).subscribe({
      next: response => {
        console.log('Plaza creada:', response)
        alert("La plaza " + this.nuevaPlaza.nombre + " ha sido creada")
        window.location.reload();},
      
        error: err => {
        console.error('Error al crear la plaza:', err)
        alert("Ocurrio un error al crear la plaza, por favor intente más tarde")
        window.location.reload();}
    });

    this.toggleRegisterForm()
  }

  actualizarPlaza(): void {
    console.log("Nueva información:", this.nuevaPlaza)

    this.PlazasService.updatePlaza(this.modfiyId ,this.nuevaPlaza).subscribe({
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
