package com.kodehaus.stocksbackend.utils;

import org.springframework.stereotype.Component;

import com.kodehaus.stocksbackend.dto.GerenteDTO;
import com.kodehaus.stocksbackend.model.Gerente;

@Component
public class GerenteMapper {
    
    public GerenteDTO toDto(Gerente gerente) {
        GerenteDTO dto = new GerenteDTO();
        dto.setId(gerente.getId());
        dto.setNombre(gerente.getNombre());
        dto.setApellido(gerente.getApellido());
        dto.setEmail(gerente.getEmail());
        dto.setPassword(gerente.getPassword()); // Incluir password para login
        dto.setTelefono(gerente.getTelefono());
        dto.setIdentificacion(gerente.getIdentificacion());
        dto.setEstado(gerente.getEstado());
        dto.setFechaCreacion(gerente.getFechaCreacion());
        dto.setFechaUltimaActualizacion(gerente.getFechaUltimaActualizacion());
        
        if (gerente.getPlaza() != null) {
            dto.setIdPlaza(gerente.getPlaza().getId());
            dto.setNombrePlaza(gerente.getPlaza().getNombre());
        }
        
        return dto;
    }
}
