package com.kodehaus.stocksbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodehaus.stocksbackend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
}