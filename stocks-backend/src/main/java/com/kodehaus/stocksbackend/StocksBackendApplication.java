package com.kodehaus.stocksbackend;

import com.kodehaus.stocksbackend.stock.Stock;
import com.kodehaus.stocksbackend.stock.StockRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StocksBackendApplication {

    private static final Logger log = LoggerFactory.getLogger(StocksBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StocksBackendApplication.class, args);
    }
}
