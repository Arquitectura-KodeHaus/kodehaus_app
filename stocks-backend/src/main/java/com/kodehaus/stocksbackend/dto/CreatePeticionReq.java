package com.kodehaus.stocksbackend.dto;

public record CreatePeticionReq (
    String correo,
    Long telefono,
    String plaza,
    Long idPlan
){}
