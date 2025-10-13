package com.kodehaus.stocksbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "suscripcion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String periodicidad;
    
    @Column(name = "fecha_ultimo_pago")
    private LocalDate fechaUltimoPago;
    
    @Column(name = "fecha_renovacion")
    private LocalDate fechaRenovacion;
    
    @Column(nullable = false)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plaza", nullable = false)
    private Plaza plaza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plan", nullable = false)
    private Plan plan;
    
    // Método helper para determinar estado de pago
    public String getEstadoPago() {
        if (fechaUltimoPago == null) return "Sin pago";
        if (fechaRenovacion != null && fechaRenovacion.isBefore(LocalDate.now())) {
            return "En mora";
        }
        return "Al día";
    }
}

