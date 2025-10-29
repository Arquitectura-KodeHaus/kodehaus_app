package com.kodehaus.stocksbackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kodehaus.stocksbackend.dto.CreateGerenteReq;
import com.kodehaus.stocksbackend.dto.GerenteDTO;
import com.kodehaus.stocksbackend.dto.UpdateGerenteReq;
import com.kodehaus.stocksbackend.service.GerenteService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/gerentes")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    /**
     * Obtener todos los gerentes
     */
    @GetMapping
    public ResponseEntity<List<GerenteDTO>> obtenerTodos() {
        List<GerenteDTO> gerentes = gerenteService.obtenerTodosLosGerentes();
        return ResponseEntity.ok(gerentes);
    }

    /**
     * Obtener gerente por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<GerenteDTO> obtenerPorId(@PathVariable Long id) {
        try {
            GerenteDTO gerente = gerenteService.obtenerGerentePorId(id);
            return ResponseEntity.ok(gerente);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener gerente por email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<GerenteDTO> obtenerPorEmail(@PathVariable String email) {
        try {
            GerenteDTO gerente = gerenteService.obtenerGerentePorEmail(email);
            return ResponseEntity.ok(gerente);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener gerente por plaza
     */
    @GetMapping("/plaza/{idPlaza}")
    public ResponseEntity<GerenteDTO> obtenerPorPlaza(@PathVariable Long idPlaza) {
        try {
            GerenteDTO gerente = gerenteService.obtenerGerentePorPlaza(idPlaza);
            return ResponseEntity.ok(gerente);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener gerentes por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<GerenteDTO>> obtenerPorEstado(@PathVariable String estado) {
        List<GerenteDTO> gerentes = gerenteService.obtenerGerentesPorEstado(estado);
        return ResponseEntity.ok(gerentes);
    }

    /**
     * Crear un nuevo gerente
     */
    @PostMapping
    public ResponseEntity<?> crearGerente(@RequestBody CreateGerenteReq request) {
        try {
            GerenteDTO nuevoGerente = gerenteService.crearGerente(request);
            return new ResponseEntity<>(nuevoGerente, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Actualizar un gerente existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarGerente(@PathVariable Long id, @RequestBody UpdateGerenteReq request) {
        try {
            GerenteDTO gerenteActualizado = gerenteService.actualizarGerente(id, request);
            return ResponseEntity.ok(gerenteActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Asignar plaza a un gerente
     */
    @PutMapping("/{idGerente}/asignar-plaza/{idPlaza}")
    public ResponseEntity<?> asignarPlaza(@PathVariable Long idGerente, @PathVariable Long idPlaza) {
        try {
            gerenteService.asignarPlaza(idGerente, idPlaza);
            return ResponseEntity.ok(Map.of("mensaje", "Plaza asignada exitosamente"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Cambiar estado de un gerente
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String nuevoEstado = body.get("estado");
            if (nuevoEstado == null || nuevoEstado.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "El estado es requerido"));
            }
            gerenteService.cambiarEstado(id, nuevoEstado);
            return ResponseEntity.ok(Map.of("mensaje", "Estado actualizado exitosamente"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar un gerente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGerente(@PathVariable Long id) {
        try {
            gerenteService.eliminarGerente(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
