import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule], 
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginData = {
    username: '',
    password: ''
  };

  isLoading: boolean = false;
  errorMessage: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit() {
    if (!this.loginData.username || !this.loginData.password) {
      this.errorMessage = 'Debes completar todos los campos';
      return;
    }

    this.isLoading = true;
    this.errorMessage = null;

    this.authService.login(
      this.loginData.username,
      this.loginData.password
    ).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/dashboard']);  // cambia la ruta si quieres
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error || 'Credenciales incorrectas';
      }
    });
  }
}
