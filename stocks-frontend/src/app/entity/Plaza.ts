export interface Plaza {
    id: number,
    nombre: string,
    contacto: string,
    dominio: string,
    departamento: string,
    ciudad: string,
    direccion: string,
    fechaCreacion: Date,
    plan?: string
    gerente_id: number,
}