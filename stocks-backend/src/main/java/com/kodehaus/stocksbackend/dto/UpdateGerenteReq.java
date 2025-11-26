package com.kodehaus.stocksbackend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGerenteReq {
    
    private String nombre;
    private String apellido;
    private String telefono;
    private String estado;
    private Long idPlaza;
    private String rol;
}
