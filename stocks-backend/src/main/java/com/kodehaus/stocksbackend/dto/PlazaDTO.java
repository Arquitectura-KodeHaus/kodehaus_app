package com.kodehaus.stocksbackend.dto;

import java.time.LocalDate;

public record PlazaDTO(
    Long id,
    String nombre,
    String contacto,
    String dominio,
    LocalDate fechaCreacion,
    String departamento,
    String ciudad,
    String direccion,
    String plan
    ) {

}
