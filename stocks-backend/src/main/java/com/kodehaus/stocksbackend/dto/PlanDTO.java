package com.kodehaus.stocksbackend.dto;

public record PlanDTO(
    Long id,
    String tipo,
    int numModulos,
    int numUsuarios,
    double precio
){}
