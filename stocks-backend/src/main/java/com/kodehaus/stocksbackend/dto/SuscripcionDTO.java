package com.kodehaus.stocksbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuscripcionDTO {
    private Long id;
    private String periodicidad;
    private LocalDate fechaUltimoPago;
    private LocalDate fechaRenovacion;
    private String estado;
    private String estadoPago;
    private String planTipo;
    private String plazaNombre;
}