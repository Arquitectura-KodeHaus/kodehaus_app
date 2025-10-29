import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StockService } from './services/stock.service';
import { Stock } from './models/stock';
import { RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent implements OnInit {
  readonly title = 'Stocks dashboard';
  stocks: Stock[] = [];
  isLoading = true;
  errorMessage = '';

  private readonly stockService = inject(StockService);

  ngOnInit(): void {
    this.stockService.list().subscribe({
      next: stocks => {
        this.stocks = stocks;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'No se pudieron cargar las acciones. Verifica que el backend esté disponible.';
        this.isLoading = false;
      }
    });
  }
}
