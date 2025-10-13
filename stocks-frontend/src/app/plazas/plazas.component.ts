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

  DeleteId: bigint;
  DeleteName = '';

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

  toggleDelete(id, nombre): void{
    this.DeleteId = id;
    this.DeleteName = nombre;
    this.showDeleteConfirm = !this.showDeleteConfirm
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
