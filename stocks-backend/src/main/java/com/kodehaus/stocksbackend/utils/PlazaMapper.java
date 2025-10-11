package com.kodehaus.stocksbackend.utils;

import org.springframework.stereotype.Component;

import com.kodehaus.stocksbackend.dto.PlazaDTO;
import com.kodehaus.stocksbackend.model.Plaza;

@Component
public class PlazaMapper {
    public PlazaDTO toDto(Plaza plaza) {
        return new PlazaDTO(
            plaza.getId(),
            plaza.getNombre(),
            plaza.getContacto(),
            plaza.getDominio(),
            plaza.getFechaCreacion(),
            
            plaza.getUbicacion().getDepartamento(),
            plaza.getUbicacion().getCiudad(),      
            plaza.getUbicacion().getDireccion()    
        );
    }
}
