package com.kodehaus.stocksbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String estado;

    @ManyToMany(mappedBy = "modulos")
    private List<Suscripcion> suscripciones;
}

