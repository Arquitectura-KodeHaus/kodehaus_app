package com.kodehaus.stocksbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kodehaus.stocksbackend.service.CuentaGerenteService;
import com.kodehaus.stocksbackend.dto.CuentaGerenteDTO;
import com.kodehaus.stocksbackend.dto.CuentaGerenteRequest;

import java.util.List;




@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cuentas/gerente")
public class CuentaGerenteController {
    @Autowired
    private CuentaGerenteService cuentaService;

    @GetMapping("/find")
    public ResponseEntity<List<CuentaGerenteDTO>> findAll() {
        List<CuentaGerenteDTO> cuentas = cuentaService.findAll();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CuentaGerenteDTO> findById(@PathVariable Long id) {
        CuentaGerenteDTO cuenta = cuentaService.findById(id);
        return ResponseEntity.ok(cuenta);
    }

    @PostMapping("/create")
    public ResponseEntity<CuentaGerenteDTO> create(@RequestBody CuentaGerenteRequest datos) {
        CuentaGerenteDTO newCuenta = cuentaService.create(datos);
        return new ResponseEntity<>(newCuenta, HttpStatus.CREATED); 
    } 
}
