package com.kodehaus.stocksbackend.controller;

import com.kodehaus.stocksbackend.dto.AuthRequest;
import com.kodehaus.stocksbackend.dto.AuthResponse;
import com.kodehaus.stocksbackend.utils.JWT.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/cuentas")
public class UserController {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager AuthManager;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){ 
        try {
            Authentication auth = AuthManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
            );
            String token = jwtUtil.generateToken(request.getCorreo());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            // THIS MUST BE 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body("Usuario o contraseña incorrecta");
        }
    }
}
