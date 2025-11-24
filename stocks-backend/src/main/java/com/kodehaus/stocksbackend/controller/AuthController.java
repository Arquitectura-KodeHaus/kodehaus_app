package com.kodehaus.stocksbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kodehaus.stocksbackend.dto.LoginRequest;
import com.kodehaus.stocksbackend.dto.LoginResponse;
import com.kodehaus.stocksbackend.dto.GerenteDTO;
import com.kodehaus.stocksbackend.service.GerenteService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private GerenteService gerenteService;

    /**
     * Login básico - valida email y password
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            // Buscar gerente por email
            GerenteDTO gerente = gerenteService.obtenerGerentePorEmail(request.getEmail());
            
            // Validar que el gerente esté activo
            if (!"ACTIVO".equals(gerente.getEstado())) {
                LoginResponse response = new LoginResponse(
                    false, 
                    "Cuenta " + gerente.getEstado().toLowerCase() + ". Contacte al administrador.", 
                    null
                );
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            
            // Validar password (sin encriptar por ahora - solo para desarrollo)
            if (!gerente.getPassword().equals(request.getPassword())) {
                LoginResponse response = new LoginResponse(false, "Credenciales inválidas", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            // Login exitoso - no enviar password en la respuesta
            gerente.setPassword(null);
            LoginResponse response = new LoginResponse(true, "Login exitoso", gerente);
            return ResponseEntity.ok(response);
            
        } catch (EntityNotFoundException e) {
            LoginResponse response = new LoginResponse(false, "Credenciales inválidas", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            LoginResponse response = new LoginResponse(false, "Error en el servidor", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
