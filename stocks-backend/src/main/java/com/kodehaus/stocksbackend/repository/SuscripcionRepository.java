package com.kodehaus.stocksbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kodehaus.stocksbackend.model.Suscripcion;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
}
