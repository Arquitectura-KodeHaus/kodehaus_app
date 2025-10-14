package com.kodehaus.stocksbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kodehaus.stocksbackend.model.Modulo;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    @Query("SELECT m FROM Modulo m LEFT JOIN FETCH m.suscripciones s LEFT JOIN FETCH s.plaza")
    List<Modulo> findAllWithDetails();

    @Query("SELECT m FROM Modulo m LEFT JOIN FETCH m.suscripciones s LEFT JOIN FETCH s.plaza WHERE m.id = :id")
    Optional<Modulo> findByIdWithDetails(Long id);
}
