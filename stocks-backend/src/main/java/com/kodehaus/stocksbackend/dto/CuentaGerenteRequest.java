package com.kodehaus.stocksbackend.dto;

public record CuentaGerenteRequest(
    String correo,
    String password,
    long cedula,
    String nombre,
    Long plaza_id
) {
    
}
