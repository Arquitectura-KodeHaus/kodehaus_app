package com.kodehaus.stocksbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kodehaus.stocksbackend.model.Ubicacion;

public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
}
