import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { DashboardService } from '../services/dashboard.service';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
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
    // Temporalmente usar datos estáticos para debug
    this.modulosActivos = 5;
    this.plazasActivas = 3;
    this.moduloMasUsado = 'Inventario';
    this.totalGanancias = 15000;
    this.isLoading = false;
  }
}
