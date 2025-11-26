import { Component, OnInit } from '@angular/core';
import { GerentesService } from '../services/gerentes.service';
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
  archivoError: string | null = null;
  archivoNombre: string | null = null;
  archivoSeleccionado: File | null = null;
  archivoSubiendo: boolean = false;
  archivoExito: string | null = null;

  onArchivoSeleccionado(event: Event): void {
    this.archivoError = null;
    this.archivoNombre = null;
    this.archivoExito = null;
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;
    const archivo = input.files[0];
    const tiposPermitidos = [
      'application/pdf',
      'image/jpeg',
      'image/png',
      'application/msword',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
    ];
    if (!tiposPermitidos.includes(archivo.type)) {
      this.archivoError = 'Tipo de archivo no permitido. Solo PDF, imágenes (.jpg, .jpeg, .png) y Word (.doc, .docx)';
      this.archivoSeleccionado = null;
      return;
    }
    this.archivoNombre = archivo.name;
    this.archivoSeleccionado = archivo;
  }

  subirArchivo(): void {
    if (!this.archivoSeleccionado) return;
    this.archivoSubiendo = true;
    this.archivoError = null;
    this.archivoExito = null;
    this.gerentesService.uploadDocumento(this.archivoSeleccionado).subscribe({
      next: () => {
        this.archivoExito = 'Archivo subido correctamente.';
        this.archivoSeleccionado = null;
        this.archivoNombre = null;
      },
      error: () => {
        this.archivoError = 'Error al subir el archivo. Intenta nuevamente.';
      },
      complete: () => {
        this.archivoSubiendo = false;
      }
    });
  }
  plazasActivas = 0;
  modulosActivos = 0;
  moduloMasUsado = '';
  totalGanancias = 0;
  errorMessage = '';
  isLoading = true;

  constructor(private dashboardService: DashboardService, private gerentesService: GerentesService) {}

  ngOnInit(): void {
    // Temporalmente usar datos estáticos para debug
    this.modulosActivos = 5;
    this.plazasActivas = 3;
    this.moduloMasUsado = 'Inventario';
    this.totalGanancias = 15000;
    this.isLoading = false;
  }
}
