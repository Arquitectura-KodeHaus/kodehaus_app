import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from "@angular/router";
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, RouterModule],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent {

  constructor() {}

  showLogin = false
  showRegister = false

  toggleLoginForm(): void{
    this.showLogin = !this.showLogin
  }

  toggleRegisterForm(): void{
    this.showRegister = !this.showRegister
  }

  crearCuenta() {
    //Codigo para crear una nueva cuenta
  }

  iniciarSesion() {
    //Codigo para crear una nueva cuenta
  }
}
