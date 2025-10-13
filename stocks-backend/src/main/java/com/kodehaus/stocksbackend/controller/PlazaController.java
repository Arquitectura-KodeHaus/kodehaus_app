package com.kodehaus.stocksbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodehaus.stocksbackend.dto.CreatePlazaReq;
import com.kodehaus.stocksbackend.dto.PlazaDTO;
import com.kodehaus.stocksbackend.dto.UpdatePlazaReq;
import com.kodehaus.stocksbackend.service.PlazaService;

import jakarta.persistence.EntityNotFoundException;
@RestController
@RequestMapping("/api/plazas")
public class PlazaController {
    @Autowired
    private PlazaService plazaService;

    @GetMapping
    public ResponseEntity<List<PlazaDTO>> findAll() {
        List<PlazaDTO> plazas = plazaService.findAll();
        return ResponseEntity.ok(plazas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlazaDTO> findById(@PathVariable Long id) {
        try {
            PlazaDTO plaza = plazaService.findById(id);
            return ResponseEntity.ok(plaza);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PlazaDTO> create(@RequestBody CreatePlazaReq plazaReq) {
        PlazaDTO newPlaza = plazaService.create(plazaReq);
        return new ResponseEntity<>(newPlaza, HttpStatus.CREATED); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlazaDTO> update(@PathVariable Long id, @RequestBody UpdatePlazaReq plazaReq) {
        try {            
            PlazaDTO updatedPlaza = plazaService.update(id, plazaReq);
            return ResponseEntity.ok(updatedPlaza); // HTTP 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        }
    }

    // --- 5. DELETE: Eliminar una plaza por ID ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            plazaService.delete(id);
            // HTTP 204 No Content, la respuesta estándar para una eliminación exitosa sin cuerpo.
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found si no se encuentra para borrar.
        }
    }

}
