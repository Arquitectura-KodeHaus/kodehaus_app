package com.kodehaus.stocksbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.kodehaus.stocksbackend.model.Suscripcion;

import java.util.List;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    
    List<Suscripcion> findByEstado(String estado);
    
    long countByEstado(String estado);
    
    @Query("SELECT s FROM Suscripcion s JOIN FETCH s.plaza JOIN FETCH s.plan")
    List<Suscripcion> findAllWithPlazaAndPlan();
    
    @Query("SELECT s FROM Suscripcion s WHERE s.plaza.id = :plazaId")
    List<Suscripcion> findByPlazaId(@Param("plazaId") Long plazaId);
    
    @Query("SELECT s FROM Suscripcion s WHERE s.plan.id = :planId")
    List<Suscripcion> findByPlanId(@Param("planId") Long planId);
}
