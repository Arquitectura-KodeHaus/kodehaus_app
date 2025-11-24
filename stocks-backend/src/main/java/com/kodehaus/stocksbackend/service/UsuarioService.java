package com.kodehaus.stocksbackend.service;

import org.springframework.stereotype.Service;

import com.kodehaus.stocksbackend.model.Usuario;
import com.kodehaus.stocksbackend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public Usuario findByUsername(String username) {
        return repo.findById(username).orElse(null);
    }
}
