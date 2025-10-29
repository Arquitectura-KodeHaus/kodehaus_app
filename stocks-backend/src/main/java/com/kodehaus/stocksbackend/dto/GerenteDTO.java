package com.kodehaus.stocksbackend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GerenteDTO {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String identificacion;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimaActualizacion;
    private Long idPlaza;
    private String nombrePlaza;
}
