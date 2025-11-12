package com.kodehaus.stocksbackend.repository;

import com.kodehaus.stocksbackend.model.CuentaGerente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CuentaGerenteRepository extends JpaRepository<CuentaGerente, Long>{
    @Query("SELECT g FROM CuentaGerente g WHERE g.plaza_id = :plazaId")
    CuentaGerente findByPlazaId(@Param("plazaId") Long plazaId);
}
