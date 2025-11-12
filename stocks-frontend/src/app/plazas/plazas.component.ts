import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlazasService } from '../services/plazas.service';
import { PlanService } from '../services/plan.service';
import { cuentaGerenteService } from '../services/cuentaGerente.service';
import { Plaza } from '../entity/Plaza';
import { CreatePlaza } from '../entity/CreatePlaza';
import { Plan } from '../models/plan';
import { FormsModule } from '@angular/forms';
import { Modulo } from '../models/modulo';
import { cuentaGerente } from '../models/cuentaGerente';
import { catchError, forkJoin, map, switchMap } from 'rxjs';


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
    fechaCreacion: null,
    gerente_id: 0
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
    plan: '',
    gerente_id: 0
  }

  gerenteInfo: cuentaGerente = {
    id: 0,
    nombre: '',
    cedula: 0,
    correo: '',
    plaza_id: 0,
    password: ''
  }

  gerentePlaza: cuentaGerente = {
    id: 0,
    nombre: '',
    cedula: 0,
    correo: '',
    plaza_id: 0,
    password: ''
  }

  nuevoGerente: cuentaGerente = {
    id: 0,
    nombre: '',
    cedula: 0,
    correo: '',
    plaza_id: 0,
    password: ''
  }

  constructor(
    private PlazasService: PlazasService,
    private PlanService: PlanService,
    private CuentaGerenteService: cuentaGerenteService
  ) {}

  planes: Plan[] = [];
  selectedPlanId: number = 0;

  showRegisterForm = false;
  showDeleteConfirm = false;
  showInfoPanel = false;
  showMofidyForm = false;
  showGerenteForm = false;
  showGerentePanel = false;

  DeleteId: bigint;
  DeleteName = '';
  modfiyId: bigint;
  plazaGerenteId: 0;

  listaPlazas: Plaza[];

  listaGerentes: cuentaGerente[];

  modulosPlaza: Modulo[] = [];

  ngOnInit(){
    // Load plazas and gerentes
    this.PlazasService.getPlazasActivas().pipe(
      switchMap((plazas) => {
        this.listaPlazas = plazas;
        console.log("Plazas cargadas:", plazas);

        const gerenteRequests = plazas.map(plaza =>
          this.CuentaGerenteService.getGerentePlaza(plaza.id).pipe(
            map(gerente => ({
              plazaId: plaza.id,
              gerente
            })),
            // If a plaza has no gerente, handle it gracefully
            catchError(() => [{
              plazaId: plaza.id,
              gerente: null
            }])
          )
        );

        return forkJoin(gerenteRequests);
      })
    ).subscribe({
      next: (results) => {
        // Save all gerentes in a separate list
        this.listaGerentes = results.map(r => r.gerente).filter(g => g != null);

        // Attach gerente_id to each plaza
        this.listaPlazas = this.listaPlazas.map(plaza => {
          const match = results.find(r => r.plazaId === plaza.id);
          return {
            ...plaza,
            gerente_id: match?.gerente?.id ?? null
          };
        });

        console.log("Plazas con gerente_id:", this.listaPlazas);
        console.log("Lista de gerentes:", this.listaGerentes);
      },
      error: (err) => {
        console.error('Error al cargar datos:', err);
        alert('Error al cargar los datos: ' + (err.error?.message || err.message));
      }
    });

    this.PlanService.getPlanes().subscribe({
      next: (data) => {
        this.planes = data;
        console.log("Planes cargados", this.planes);
        console.log("Número de planes:", this.planes.length);
        }
    });
  }

  toggleRegisterForm(): void{
    this.showRegisterForm = !this.showRegisterForm;
  }

  toggleGerenteForm(id): void{
    this.showGerenteForm = !this.showGerenteForm;
    this.plazaGerenteId = id;
  }

  toggleGerenteInfo(id): void{
    this.showGerentePanel = !this.showGerentePanel;
    this.plazaGerenteId = id;

    console.log
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
            fechaCreacion: data.fechaCreacion,
            gerente_id: 0
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
        fechaCreacion: null,
        gerente_id: 0
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

  showGerenteInfo(id): void{
    this.toggleGerenteInfo(id);

    const plaza = this.listaPlazas.find(p => p.id === id);
    if (!plaza?.gerente_id) {
      return;
    }
    
    this.gerenteInfo = this.listaGerentes.find(g => g.id === plaza.gerente_id);
    console.log("Mostrando información del gerente:", this.gerenteInfo);
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
      fechaCreacion: this.nuevaPlaza.fechaCreacion,
      gerente_id: 0
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

  crearGerente(): void{
    this.nuevoGerente.plaza_id = this.plazaGerenteId;
    this.CuentaGerenteService.crearGerente(this.nuevoGerente).subscribe({
      next: response => {
        console.log('Gerente creado:', response);
        alert("El gerente " + this.nuevoGerente.nombre + " ha sido creado para la plaza");
        window.location.reload();
      },
      error: err => {
        console.error('Error al crear el gerente:', err);
        alert("Ocurrió un error al crear el gerente, por favor intente más tarde");
        window.location.reload();
      }
    });

    this.toggleGerenteForm(null);
  }
}
