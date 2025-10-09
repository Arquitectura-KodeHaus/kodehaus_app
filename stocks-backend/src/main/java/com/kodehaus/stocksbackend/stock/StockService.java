package com.kodehaus.stocksbackend.stock;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StockService {

    private final StockRepository repository;

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    public List<Stock> findAll() {
        return repository.findAll();
    }

    public Stock create(Stock stock) {
        repository.findBySymbolIgnoreCase(stock.getSymbol())
                .ifPresent(existing -> {
                    throw new StockValidationException("A stock with symbol '%s' already exists".formatted(existing.getSymbol()));
                });
        return repository.save(stock);
    }

    public Stock findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new StockValidationException("Stock not found"));
    }
}
