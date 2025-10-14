package com.kodehaus.stocksbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.kodehaus.stocksbackend.model.Suscripcion;
import java.util.List;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    
    @Query("SELECT s FROM Suscripcion s " +
           "LEFT JOIN FETCH s.plaza p " +
           "LEFT JOIN FETCH s.plan pl " +
           "LEFT JOIN FETCH s.modulos m " +
           "LEFT JOIN FETCH p.ubicacion")
    List<Suscripcion> findAllWithPlazaAndPlan();
    
    @Query("SELECT s FROM Suscripcion s " +
           "LEFT JOIN FETCH s.plaza p " +
           "LEFT JOIN FETCH s.plan pl " +
           "LEFT JOIN FETCH s.modulos m " +
           "LEFT JOIN FETCH p.ubicacion " +
           "WHERE s.id = :id")
    Suscripcion findByIdWithFullInfo(Long id);
}