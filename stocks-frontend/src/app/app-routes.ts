import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ModulosComponent } from './modulos/modulos.component';
import { PlanesComponent } from "./planes/planes.component";
import { PlazasComponent } from "./plazas/plazas.component";
import { SuscripcionesComponent } from "./suscripciones/suscripciones.component";
import { LandingComponent } from './landing/landing.component';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'modulos', component: ModulosComponent },
  { path: 'planes', component: PlanesComponent },
  { path: 'plazas', component: PlazasComponent },
  { path: 'suscripciones', component: SuscripcionesComponent }
];