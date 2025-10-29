package com.kodehaus.stocksbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
        name = "suscripcion_modulo",
        joinColumns = @JoinColumn(name = "id_suscripcion"),
        inverseJoinColumns = @JoinColumn(name = "id_modulo")
    )
    private List<Modulo> modulos;
}

