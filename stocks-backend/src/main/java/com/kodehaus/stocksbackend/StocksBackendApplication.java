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

    @Bean
    CommandLineRunner seedStocks(StockRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                log.info("Skipping seed data creation because stocks already exist");
                return;
            }

            log.info("Seeding CockroachDB with starter stock data");
            List<Stock> starterStocks = List.of(
                    new Stock(null, "GOOGL", "Alphabet Inc.", new BigDecimal("132.47"), Instant.now()),
                    new Stock(null, "MSFT", "Microsoft Corporation", new BigDecimal("356.81"), Instant.now()),
                    new Stock(null, "TSLA", "Tesla, Inc.", new BigDecimal("252.75"), Instant.now())
            );

            repository.saveAll(starterStocks);
            log.info("Seed data created successfully");
        };
    }
}
