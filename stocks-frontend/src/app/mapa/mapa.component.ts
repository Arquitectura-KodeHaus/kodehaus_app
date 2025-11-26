import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

interface Plaza {
  name: string;
  query: string; // text to search in Google Maps
}

@Component({
  selector: 'app-mapa',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mapa.component.html',
  styleUrls: ['./mapa.component.css']
})
export class MapaComponent {
  plazas: Plaza[] = [
    { name: 'Paloquemao', query: 'Paloquemao, Bogota, Colombia' },
    { name: 'Usaquén', query: 'Plaza de Mercado Usaquén, Bogota, Colombia' },
    { name: 'Fontibón', query: 'Plaza de Mercado Fontibón, Bogota, Colombia' },
    { name: 'La Perseverancia', query: 'Mercado de la Perseverancia, Bogota, Colombia' },
    { name: 'San Victorino', query: 'Plaza de Mercado San Victorino, Bogota, Colombia' }
  ];

  selected?: Plaza = this.plazas[0];
  mapUrl?: SafeResourceUrl;

  constructor(private sanitizer: DomSanitizer) {
    this.updateMapUrl();
  }

  selectPlaza(p: Plaza) {
    this.selected = p;
    this.updateMapUrl();
  }

  private updateMapUrl() {
    const base = 'https://www.google.com/maps?q=';
    const q = encodeURIComponent(this.selected?.query ?? 'Bogota, Colombia');
    const url = `${base}${q}&output=embed`;
    this.mapUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}
