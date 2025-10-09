package com.kodehaus.stocksbackend.stock;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, UUID> {
    Optional<Stock> findBySymbolIgnoreCase(String symbol);
}
