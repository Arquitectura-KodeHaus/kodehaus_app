package com.kodehaus.stocksbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kodehaus.stocksbackend.model.Modulo;
import com.kodehaus.stocksbackend.model.Plaza;
import java.util.List;

public interface PlazaRepository extends JpaRepository<Plaza, Long> {
    
    @Query("SELECT p FROM Plaza p " +
           "LEFT JOIN FETCH p.suscripciones s " +
           "LEFT JOIN FETCH s.plan " +
           "LEFT JOIN FETCH p.ubicacion")
    List<Plaza> findAllWithRelations();

    @Query("""
        SELECT DISTINCT m
        FROM Suscripcion s
        JOIN s.modulos m
        JOIN FETCH m.suscripciones ms
        WHERE s.plaza.id = :plazaId""")
    List<Modulo> getModulos(@Param("plazaId") Long plazaId);
}
