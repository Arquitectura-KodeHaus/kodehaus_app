package com.kodehaus.stocksbackend.controller;

import com.kodehaus.stocksbackend.dto.CreateSuscripcionReq;
import com.kodehaus.stocksbackend.dto.SuscripcionDTO;
import com.kodehaus.stocksbackend.dto.UpdateSuscripcionReq;
import com.kodehaus.stocksbackend.service.SuscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    @GetMapping
    public ResponseEntity<List<SuscripcionDTO>> getAllSuscripciones(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String estadoPago) {
        
        List<SuscripcionDTO> suscripciones;
        
        if (estado != null) {
            suscripciones = suscripcionService.findByEstado(estado);
        } else if (estadoPago != null) {
            suscripciones = suscripcionService.findByEstadoPago(estadoPago);
        } else {
            suscripciones = suscripcionService.findAll();
        }
        
        return ResponseEntity.ok(suscripciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuscripcionDTO> getSuscripcionById(@PathVariable Long id) {
        return suscripcionService.findById(id)
                .map(suscripcion -> ResponseEntity.ok(suscripcion))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SuscripcionDTO> createSuscripcion(@RequestBody CreateSuscripcionReq request) {
        try {
            SuscripcionDTO created = suscripcionService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuscripcionDTO> updateSuscripcion(
            @PathVariable Long id,
            @RequestBody UpdateSuscripcionReq request) {
        try {
            return suscripcionService.update(id, request)
                    .map(updated -> ResponseEntity.ok(updated))
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuscripcion(@PathVariable Long id) {
        if (suscripcionService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = Map.of(
            "vigentes", suscripcionService.countVigentes(),
            "enMora", suscripcionService.countEnMora(),
            "total", suscripcionService.findAll().size()
        );
        return ResponseEntity.ok(stats);
    }
}