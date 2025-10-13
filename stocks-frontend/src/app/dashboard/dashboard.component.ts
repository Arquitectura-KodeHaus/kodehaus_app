import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../services/dashboard.service';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  plazasActivas = 0;
  modulosActivos = 0;
  moduloMasUsado = '';
  totalGanancias = 0;
  errorMessage = '';
  isLoading = true;

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.getModulosActivos().subscribe({
      next: data => this.modulosActivos = data.cantidad_modulos_activos,
      error: () => this.errorMessage = 'Error cargando módulos activos'
    });

    this.dashboardService.getModuloMasUsado().subscribe({
      next: data => this.moduloMasUsado = data.nombre,
      error: () => this.errorMessage = 'Error cargando módulo más usado'
    });

    this.dashboardService.getTotalGanancias().subscribe({
      next: data => this.totalGanancias = data.total,
      error: () => this.errorMessage = 'Error cargando ganancias'
    });

    this.dashboardService.getPlazasActivas().subscribe({
      next: (data: any) => this.plazasActivas = data.cantidad_plazas_activas,
      error: () => this.errorMessage = 'Error cargando plazas activas'
    });

    this.isLoading = false;
  }
}
