package com.kodehaus.stocksbackend.stock;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
@Validated
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping
    public List<Stock> list() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Stock create(@RequestBody @Valid CreateStockRequest request) {
        Stock stock = new Stock();
        stock.setSymbol(request.symbol());
        stock.setName(request.name());
        stock.setPrice(request.price());
        return service.create(stock);
    }

    public record CreateStockRequest(
            @NotBlank @Size(max = 10) String symbol,
            @NotBlank @Size(max = 150) String name,
            @NotNull BigDecimal price
    ) {
    }
}
