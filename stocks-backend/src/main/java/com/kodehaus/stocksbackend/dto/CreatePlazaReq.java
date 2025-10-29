package com.kodehaus.stocksbackend.dto;

public record CreatePlazaReq(
    String nombre,
    String contacto,
    String dominio,
    String departamento,
    String ciudad,
    String direccion,
    long planId
    ) {
}
