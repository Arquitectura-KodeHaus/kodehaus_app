package com.kodehaus.stocksbackend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGerenteReq {
    
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String identificacion;
    private Long idPlaza; // Opcional: puede asignarse después
}
