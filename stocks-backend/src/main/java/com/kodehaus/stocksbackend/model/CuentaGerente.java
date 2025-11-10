package com.kodehaus.stocksbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaGerente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String correo;
    private String password;
    private long cedula;
    private String nombre;
    private long plaza_id;
}
