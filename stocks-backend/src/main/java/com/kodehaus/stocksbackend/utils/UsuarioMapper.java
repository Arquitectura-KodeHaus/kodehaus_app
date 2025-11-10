package com.kodehaus.stocksbackend.utils;

import org.springframework.stereotype.Component;

import com.kodehaus.stocksbackend.dto.UsuarioDTO;
import com.kodehaus.stocksbackend.model.Usuario;

@Component
public class UsuarioMapper {
    public UsuarioDTO toDto(Usuario usuario){
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getCedula(),
            usuario.getCorreo(),
            usuario.getPassword()
        );
    }
}
