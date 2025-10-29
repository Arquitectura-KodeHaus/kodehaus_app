package com.kodehaus.stocksbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.kodehaus.stocksbackend.dto.CreatePlanReq;
import com.kodehaus.stocksbackend.dto.PlanDTO;
import com.kodehaus.stocksbackend.service.PlanService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/planes")
public class PlanController {
    @Autowired
    private PlanService planService;

    @PostMapping
    public ResponseEntity<PlanDTO> create(@RequestBody CreatePlanReq plazaReq) {
        PlanDTO newPlaza = planService.create(plazaReq);
        return new ResponseEntity<>(newPlaza, HttpStatus.CREATED); 
    }

    @GetMapping
    public ResponseEntity<List<PlanDTO>> findAll() {
        List<PlanDTO> plazas = planService.findAll();
        return ResponseEntity.ok(plazas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanDTO> update(@PathVariable Long id, @RequestBody CreatePlanReq plazaReq) {
        try {            
            PlanDTO updatedPlaza = planService.update(id, plazaReq);
            return ResponseEntity.ok(updatedPlaza);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); 
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            planService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
