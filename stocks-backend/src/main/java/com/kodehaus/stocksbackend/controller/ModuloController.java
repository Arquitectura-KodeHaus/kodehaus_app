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

import com.kodehaus.stocksbackend.dto.CreateModuloReq;
import com.kodehaus.stocksbackend.dto.ModuloDTO;
import com.kodehaus.stocksbackend.dto.UpdateModuloReq;
import com.kodehaus.stocksbackend.service.ModuloService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/modulos")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    // --- 1. GET: Listar todos los módulos ---
    @GetMapping
    public ResponseEntity<List<ModuloDTO>> findAll() {
        List<ModuloDTO> modulos = moduloService.findAll();
        return ResponseEntity.ok(modulos);
    }

    // --- 2. GET: Buscar un módulo por ID ---
    @GetMapping("/{id}")
    public ResponseEntity<ModuloDTO> findById(@PathVariable Long id) {
        try {
            ModuloDTO modulo = moduloService.findById(id);
            return ResponseEntity.ok(modulo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- 3. POST: Crear un nuevo módulo ---
    @PostMapping
    public ResponseEntity<ModuloDTO> create(@RequestBody CreateModuloReq moduloReq) {
        ModuloDTO newModulo = moduloService.create(moduloReq);
        return new ResponseEntity<>(newModulo, HttpStatus.CREATED);
    }

    // --- 4. PUT: Actualizar un módulo existente ---
    @PutMapping("/{id}")
    public ResponseEntity<ModuloDTO> update(@PathVariable Long id, @RequestBody UpdateModuloReq moduloReq) {
        try {
            ModuloDTO updatedModulo = moduloService.update(id, moduloReq);
            return ResponseEntity.ok(updatedModulo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // --- 5. DELETE: Eliminar un módulo ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            moduloService.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


