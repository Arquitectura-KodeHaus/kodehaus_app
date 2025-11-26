export interface Gerente {
  id?: number;
  nombre: string;
  apellido: string;
  email: string;
  telefono: string;
  identificacion: string;
  estado: 'ACTIVO' | 'INACTIVO' | 'SUSPENDIDO';
  rol: 'admin' | 'gerente' | 'supervisor';
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
  rol: 'admin' | 'gerente' | 'supervisor';
  idPlaza?: number;
}

export interface UpdateGerenteRequest {
  nombre?: string;
  apellido?: string;
  telefono?: string;
  estado?: string;
  rol?: 'admin' | 'gerente' | 'supervisor';
  idPlaza?: number;
}
