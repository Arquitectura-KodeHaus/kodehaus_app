package com.kodehaus.stocksbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuscripcionDTO {
    private Long id;
    private String periodicidad;
    private LocalDate fechaUltimoPago;
    private LocalDate fechaRenovacion;
    private String estado;
    private PlazaInfoDTO plaza;
    private PlanInfoDTO plan;
    private List<ModuloInfoDTO> modulos;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlazaInfoDTO {
        private Long id;
        private String nombre;
        private String contacto;
        private String dominio;
        private LocalDate fechaCreacion;
        private UbicacionInfoDTO ubicacion;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UbicacionInfoDTO {
        private Long id;
        private String nombre;
        private String direccion;
        private String ciudad;
        private String pais;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlanInfoDTO {
        private Long id;
        private String tipo;
        private int numModulos;
        private int numUsuarios;
        private double precio;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModuloInfoDTO {
        private Long id;
        private String nombre;
        private String estado;
    }
}
