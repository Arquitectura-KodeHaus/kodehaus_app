package com.kodehaus.stocksbackend.controller;

import com.kodehaus.stocksbackend.dto.CreatePeticionReq;
import com.kodehaus.stocksbackend.dto.PeticionDTO;
import com.kodehaus.stocksbackend.service.PeticionService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peticiones")
@CrossOrigin(origins = "http://localhost:4200")
public class PeticionController {
    @Autowired
    private PeticionService peticionService;

    @GetMapping
    public ResponseEntity<List<PeticionDTO>> getAllRequests() {
        try {
            List<PeticionDTO> peticiones = peticionService.findAll();
            return ResponseEntity.ok(peticiones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<PeticionDTO> create(@RequestBody CreatePeticionReq peticionReq) {
        PeticionDTO newRequest = peticionService.create(peticionReq);
        return new ResponseEntity<>(newRequest, HttpStatus.CREATED); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            peticionService.delete(id);
            // HTTP 204 No Content, la respuesta estándar para una eliminación exitosa sin cuerpo.
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found si no se encuentra para borrar.
        }
    }
}
