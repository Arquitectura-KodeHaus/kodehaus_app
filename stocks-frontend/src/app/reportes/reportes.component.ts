import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, registerables } from 'chart.js';
import { graficaUsuarios } from './chart.config';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.css']
})
export class ReportesComponent implements AfterViewInit {
  chartConfig = graficaUsuarios;

  @ViewChild('chartCanvas', { static: true }) chartCanvas!: ElementRef<HTMLCanvasElement>;

  ngAfterViewInit(): void {
    Chart.register(...registerables);
    // Create the chart using the config object
    // graficaUsuarios is already a ChartConfiguration
    // @ts-ignore
    new Chart(this.chartCanvas.nativeElement, this.chartConfig as any);
  }
}
