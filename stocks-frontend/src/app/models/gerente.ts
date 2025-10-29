export interface Gerente {
  id?: number;
  nombre: string;
  apellido: string;
  email: string;
  telefono: string;
  identificacion: string;
  estado: 'ACTIVO' | 'INACTIVO' | 'SUSPENDIDO';
  fechaCreacion?: string;
  fechaUltimaActualizacion?: string;
  idPlaza?: number;
  nombrePlaza?: string;
}

export interface CreateGerenteRequest {
  nombre: string;
  apellido: string;
  email: string;
  password: string;
  telefono: string;
  identificacion: string;
  idPlaza?: number;
}

export interface UpdateGerenteRequest {
  nombre?: string;
  apellido?: string;
  telefono?: string;
  estado?: string;
  idPlaza?: number;
}
