package com.kodehaus.stocksbackend.repository;

import com.kodehaus.stocksbackend.model.Gerente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GerenteRepository extends JpaRepository<Gerente, Long> {
    
    Optional<Gerente> findByEmail(String email);
    
    Optional<Gerente> findByIdentificacion(String identificacion);
    
    List<Gerente> findByEstado(String estado);
    
    Optional<Gerente> findByPlazaId(Long plazaId);
    
    boolean existsByEmail(String email);
    
    boolean existsByIdentificacion(String identificacion);
}
