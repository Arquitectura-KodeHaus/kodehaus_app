package com.kodehaus.stocksbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kodehaus.stocksbackend.model.Plaza;

public interface PlazaRepository extends JpaRepository<Plaza, Long> {
}
