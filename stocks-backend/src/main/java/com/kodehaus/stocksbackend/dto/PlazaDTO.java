package com.kodehaus.stocksbackend.dto;

import java.time.LocalDateTime;

public record PlazaDTO(
    Long id,
    String nombre,
    String contacto,
    String dominio,
    LocalDateTime fechaCreacion,
    String departamento,
    String ciudad,
    String direccion
    ) {

}
