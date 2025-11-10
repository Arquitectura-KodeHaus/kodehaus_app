package com.kodehaus.stocksbackend.service;

import com.kodehaus.stocksbackend.dto.UsuarioDTO;
import com.kodehaus.stocksbackend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kodehaus.stocksbackend.repository.UsuarioRepository;
import com.kodehaus.stocksbackend.utils.UsuarioMapper;
import jakarta.persistence.EntityNotFoundException;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioMapper usuarioMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;

    UsuarioServiceImpl(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public UsuarioDTO findByCorreo(String correo){
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        return usuarioMapper.toDto(usuario);
    }
}
