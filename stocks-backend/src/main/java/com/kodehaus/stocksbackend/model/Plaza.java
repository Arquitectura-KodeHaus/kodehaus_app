package com.kodehaus.stocksbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plaza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String contacto;
    private String dominio;
    private LocalDate fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;

    @OneToMany(mappedBy = "plaza", cascade = CascadeType.ALL)
    private List<Suscripcion> suscripciones;

    @ManyToMany
    @JoinTable(
        name = "plaza_modulo",
        joinColumns = @JoinColumn(name = "id_plaza"),
        inverseJoinColumns = @JoinColumn(name = "id_modulo")
    )
    private List<Modulo> modulos;
}
