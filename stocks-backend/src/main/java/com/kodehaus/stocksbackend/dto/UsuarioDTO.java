package com.kodehaus.stocksbackend.dto;

public record UsuarioDTO (
    Long id,
    String nombre,
    Long cedula,
    String correo,
    String password
){
    
}
