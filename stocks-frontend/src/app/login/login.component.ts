import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { GerentesService } from '../services/gerentes.service';
import { EventosService } from '../services/eventos.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  isLoading = false;
  errorMessage = '';

  

  // file upload state (visible only on login)
  archivoError: string | null = null;
  archivoNombre: string | null = null;
  archivoSeleccionado: File | null = null;
  archivoSubiendo = false;
  archivoExito: string | null = null;

  constructor(private authService: AuthService, private gerentesService: GerentesService, private eventosService: EventosService) {}

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
        this.eventosService.agregarNotificacion('Documento de identidad subido desde login', 'info');
      },
      error: () => {
        this.archivoError = 'Error al subir el archivo. Intenta nuevamente.';
        this.eventosService.agregarNotificacion('Error al subir documento desde login', 'alerta');
      },
      complete: () => {
        this.archivoSubiendo = false;
      }
    });
  }

  onSubmit(): void {
    if (!this.email || !this.password) {
      this.errorMessage = 'Por favor ingrese email y contraseña';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login(this.email, this.password).subscribe({
      next: (response) => {
        this.isLoading = false;
        if (response.success) {
          // El AppComponent detectará el cambio automáticamente
          console.log('Login exitoso:', response.gerente);
        } else {
          this.errorMessage = response.message;
        }
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || 'Credenciales inválidas';
      }
    });
  }

  irARegistro(): void {
    window.dispatchEvent(new CustomEvent('irARegistro'));
  }
    irAlHome(): void {
      window.dispatchEvent(new CustomEvent('irAlHome'));
    }
}
