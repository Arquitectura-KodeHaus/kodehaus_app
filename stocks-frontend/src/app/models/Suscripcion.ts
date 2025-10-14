export interface Suscripcion {
  id: number;
  periodicidad: string;
  fechaUltimoPago: string; // LocalDate maps to string in TypeScript
  fechaRenovacion: string; // LocalDate maps to string in TypeScript
  estado: string;
  plaza: PlazaInfoDTO;
  plan: PlanInfoDTO;
  modulos: ModuloInfoDTO[];
}

export interface PlazaInfoDTO {
  id: number;
  nombre: string;
  contacto: string;
  dominio: string;
  fechaCreacion: string; // LocalDate maps to string in TypeScript
  ubicacion: UbicacionInfoDTO;
}

export interface UbicacionInfoDTO {
  id: number;
  nombre: string;
  direccion: string;
  ciudad: string;
  pais: string;
}

export interface PlanInfoDTO {
  id: number;
  tipo: string;
  numModulos: number;
  numUsuarios: number;
  precio: number;
}

export interface ModuloInfoDTO {
  id: number;
  nombre: string;
  estado: string;
}
  