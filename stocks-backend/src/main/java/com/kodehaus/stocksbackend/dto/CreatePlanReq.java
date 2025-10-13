package com.kodehaus.stocksbackend.dto;

public record CreatePlanReq(
    String tipo,
    int numModulos,
    int numUsuarios,
    double precio
){}
