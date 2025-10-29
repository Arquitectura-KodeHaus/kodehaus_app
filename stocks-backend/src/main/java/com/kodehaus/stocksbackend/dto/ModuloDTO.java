package com.kodehaus.stocksbackend.dto;

public record ModuloDTO(
        Long id,
        String nombre,
        String estado,
        String descripcion,
        Long numeroPlazas
) {
}

