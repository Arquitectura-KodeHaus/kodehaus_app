package com.kodehaus.stocksbackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kodehaus.stocksbackend.model.Modulo;
import com.kodehaus.stocksbackend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    Usuario findByCorreo(String correo);
}
