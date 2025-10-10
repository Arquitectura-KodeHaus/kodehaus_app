package com.kodehaus.stocksbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String periodicidad;
    private LocalDate fechaUltimoPago;
    private LocalDate fechaRenovacion;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_plaza")
    private Plaza plaza;

    @ManyToOne
    @JoinColumn(name = "id_plan")
    private Plan plan;
}

