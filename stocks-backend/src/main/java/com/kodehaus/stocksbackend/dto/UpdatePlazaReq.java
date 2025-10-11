package com.kodehaus.stocksbackend.dto;

public record UpdatePlazaReq(
    String nombre,
    String contacto,
    String dominio,
    String departamento,
    String ciudad,
    String direccion
    ) {

}
