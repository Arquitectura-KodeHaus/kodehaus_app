package com.kodehaus.stocksbackend.dto;

public record CreateModuloReq(
        String nombre,
        String estado,
        String descripcion
) {
}

