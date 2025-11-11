package com.kodehaus.stocksbackend.repository;

import com.kodehaus.stocksbackend.model.CuentaGerente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaGerenteRepository extends JpaRepository<CuentaGerente, Long>{
    
}
