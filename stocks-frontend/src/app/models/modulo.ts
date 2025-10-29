export interface Modulo {
    id: number;
    nombre: string;
    plazas: number;
    estado: 'Activo' | 'En desarrollo' | 'Inactivo';
    descripcion: string;
}