package com.kodehaus.stocksbackend.service;

import com.kodehaus.stocksbackend.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO findByCorreo(String correo);
}
