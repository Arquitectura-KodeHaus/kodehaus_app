package com.kodehaus.stocksbackend.controller;

import com.kodehaus.stocksbackend.dto.SuscripcionDTO;
import com.kodehaus.stocksbackend.dto.AddModulosRequest;
import com.kodehaus.stocksbackend.service.SuscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
@CrossOrigin(origins = "http://localhost:4200")
public class SuscripcionController {

    @Autowired
    private SuscripcionService suscripcionService;

    @GetMapping
    public ResponseEntity<List<SuscripcionDTO>> getAllSuscripciones() {
        try {
            List<SuscripcionDTO> suscripciones = suscripcionService.getAllSuscripciones();
            return ResponseEntity.ok(suscripciones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuscripcionDTO> getSuscripcionById(@PathVariable Long id) {
        try {
            SuscripcionDTO suscripcion = suscripcionService.getSuscripcionById(id);
            return ResponseEntity.ok(suscripcion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/{id}/modulos")
    public ResponseEntity<String> addModulosToSuscripcion(@PathVariable Long id, @RequestBody AddModulosRequest request) {
        try {
            suscripcionService.addModulosToSuscripcion(id, request);
            return ResponseEntity.ok("Módulos agregados exitosamente a la suscripción");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }

    @DeleteMapping("/{suscripcionId}/modulos/{moduloId}")
    public ResponseEntity<String> removeModuloFromSuscripcion(@PathVariable Long suscripcionId, @PathVariable Long moduloId) {
        try {
            suscripcionService.removeModuloFromSuscripcion(suscripcionId, moduloId);
            return ResponseEntity.ok("Módulo eliminado exitosamente de la suscripción");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno del servidor");
        }
    }
}
