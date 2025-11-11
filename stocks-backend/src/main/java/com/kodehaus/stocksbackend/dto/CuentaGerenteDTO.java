package com.kodehaus.stocksbackend.dto;

public record CuentaGerenteDTO(
    Long id,
    String correo,
    String password,
    long cedula,
    String nombre,
    Long plaza_id
) {
    
}
