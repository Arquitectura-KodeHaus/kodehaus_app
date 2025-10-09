package com.kodehaus.stocksbackend.health;

import java.util.Map;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final HealthEndpoint healthEndpoint;

    public HealthController(HealthEndpoint healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        HealthComponent health = healthEndpoint.health();
        return ResponseEntity.ok(Map.of(
                "status", health.getStatus().getCode()
        ));
    }
}
