-- Script de creación de tabla para Gerentes
-- PlazApp - Servicio del Sistema (Dueño del software)

CREATE TABLE IF NOT EXISTS gerente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    identificacion VARCHAR(50) NOT NULL UNIQUE,
    estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_ultima_actualizacion TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    id_plaza BIGINT UNIQUE,
    
    CONSTRAINT fk_gerente_plaza FOREIGN KEY (id_plaza) REFERENCES plaza(id) ON DELETE SET NULL,
    CONSTRAINT chk_estado CHECK (estado IN ('ACTIVO', 'INACTIVO', 'SUSPENDIDO'))
);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_gerente_email ON gerente(email);
CREATE INDEX idx_gerente_identificacion ON gerente(identificacion);
CREATE INDEX idx_gerente_estado ON gerente(estado);
CREATE INDEX idx_gerente_plaza ON gerente(id_plaza);
