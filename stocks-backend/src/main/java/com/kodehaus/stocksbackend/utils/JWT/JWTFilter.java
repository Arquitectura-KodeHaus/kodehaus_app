package com.kodehaus.stocksbackend.utils.JWT;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kodehaus.stocksbackend.dto.UsuarioDTO;

import com.kodehaus.stocksbackend.service.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class JWTFilter extends OncePerRequestFilter{
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        final String authHeader = req.getHeader("Authorization");
        String correo = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                correo = jwtUtil.extractCorreo(jwt);
            } catch (Exception e) {
            }
        }

        if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsuarioDTO usuario = this.usuarioService.findByCorreo(correo);
            if (jwtUtil.validateToken(jwt, usuario.nombre())) {
                UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }

        chain.doFilter(req, res);
    }
}
