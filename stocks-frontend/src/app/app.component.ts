import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SuscripcionesComponent } from './suscripciones/suscripciones.component';
import { StockService } from './services/stock.service';
import { Stock } from './models/stock';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, DashboardComponent, SuscripcionesComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  readonly title = 'Stocks dashboard';
  stocks: Stock[] = [];
  isLoading = true;
  errorMessage = '';

  private readonly stockService = inject(StockService);

  ngOnInit(): void {
    // Temporalmente deshabilitado para debug
    this.isLoading = false;
    console.log('App component inicializado correctamente');
  }

  // simple view switcher (no router to keep the example small)
  currentView: 'dashboard' | 'suscripciones' = 'dashboard';
  show(view: 'dashboard' | 'suscripciones') { this.currentView = view; }
}
