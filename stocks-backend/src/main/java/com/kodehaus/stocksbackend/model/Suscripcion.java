package com.kodehaus.stocksbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
        name = "suscripcion_modulo",
        joinColumns = @JoinColumn(name = "id_suscripcion"),
        inverseJoinColumns = @JoinColumn(name = "id_modulo")
    )
    private List<Modulo> modulos;
}

