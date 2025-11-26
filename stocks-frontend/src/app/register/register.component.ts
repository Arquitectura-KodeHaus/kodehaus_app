import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GerentesService } from '../services/gerentes.service';
import { CreateGerenteRequest } from '../models/gerente';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  formulario: CreateGerenteRequest = {
    nombre: '',
    apellido: '',
    email: '',
    password: '',
    telefono: '',
    identificacion: '',
    idPlaza: undefined,
    rol: 'gerente'
  };
  
  isLoading = false;
  errorMessage = '';
  successMessage = '';

  constructor(private gerentesService: GerentesService) {}

  onSubmit(): void {
    // Validaciones básicas
    if (!this.formulario.nombre || !this.formulario.apellido || 
        !this.formulario.email || !this.formulario.password ||
        !this.formulario.telefono || !this.formulario.identificacion) {
      this.errorMessage = 'Por favor complete todos los campos obligatorios';
      return;
    }

    if (this.formulario.password.length < 6) {
      this.errorMessage = 'La contraseña debe tener al menos 6 caracteres';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.gerentesService.create(this.formulario).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.successMessage = '¡Cuenta creada exitosamente! Redirigiendo al login...';
        
        // Limpiar formulario
        setTimeout(() => {
          this.formulario = {
            nombre: '',
            apellido: '',
            email: '',
            password: '',
            telefono: '',
            identificacion: '',
            idPlaza: undefined,
            rol: 'gerente'
          };
          this.successMessage = '';
          // Emitir evento para volver al login
          window.dispatchEvent(new CustomEvent('registroExitoso'));
        }, 2000);
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Error completo:', err);
        
        // Intentar obtener el mensaje de error del servidor
        let errorMsg = 'Error al crear la cuenta. Verifica los datos e intenta nuevamente.';
        
        if (err.error) {
          if (typeof err.error === 'string') {
            errorMsg = err.error;
          } else if (err.error.error) {
            errorMsg = err.error.error;
          } else if (err.error.message) {
            errorMsg = err.error.message;
          }
        } else if (err.message) {
          errorMsg = err.message;
        }
        
        this.errorMessage = errorMsg;
      }
    });
  }

  volverAlLogin(): void {
    window.dispatchEvent(new CustomEvent('volverAlLogin'));
  }
}
