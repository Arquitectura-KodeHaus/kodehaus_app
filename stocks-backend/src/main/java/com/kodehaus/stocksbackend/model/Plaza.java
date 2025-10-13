package com.kodehaus.stocksbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "plaza")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plaza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    
    private String contacto;
    private String dominio;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ubicacion", nullable = false)
    private Ubicacion ubicacion;

    @OneToMany(mappedBy = "plaza", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Suscripcion> suscripciones;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "plaza_modulo",
        joinColumns = @JoinColumn(name = "id_plaza"),
        inverseJoinColumns = @JoinColumn(name = "id_modulo")
    )
    private List<Modulo> modulos;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}
