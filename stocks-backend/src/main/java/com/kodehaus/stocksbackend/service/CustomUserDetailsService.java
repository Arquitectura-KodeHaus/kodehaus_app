package com.kodehaus.stocksbackend.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kodehaus.stocksbackend.dto.UsuarioDTO;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        UsuarioDTO usuario = usuarioService.findByCorreo(correo);
        System.out.println("Found user: " + usuario);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
        }

        // We are not encrypting passwords, so just use NoOp encoder
        return User.withUsername(usuario.correo())
                   .password(usuario.password())
                   .authorities("USER")
                   .build();
    }
}
