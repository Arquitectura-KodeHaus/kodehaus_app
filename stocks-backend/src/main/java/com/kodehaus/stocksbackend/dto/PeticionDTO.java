package com.kodehaus.stocksbackend.dto;

public record PeticionDTO (
    Long id,
    String correo,
    Long telefono,
    String plaza,
    Long idPlan
){
    
}
