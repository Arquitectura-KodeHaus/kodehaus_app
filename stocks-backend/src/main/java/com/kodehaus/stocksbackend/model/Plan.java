package com.kodehaus.stocksbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private int numModulos;
    private int numUsuarios;
    private double precio;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<Suscripcion> suscripciones;
}
