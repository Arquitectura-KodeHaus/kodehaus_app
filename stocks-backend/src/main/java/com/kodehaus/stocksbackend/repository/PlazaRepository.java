package com.kodehaus.stocksbackend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.kodehaus.stocksbackend.model.Plaza;

public interface PlazaRepository extends JpaRepository<Plaza, Long> {
    
    @Query("SELECT p FROM Plaza p JOIN FETCH p.ubicacion")
    List<Plaza> findAllWithUbicacion();
}
