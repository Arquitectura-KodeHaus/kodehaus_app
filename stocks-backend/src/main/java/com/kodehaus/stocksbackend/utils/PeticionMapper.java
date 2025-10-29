package com.kodehaus.stocksbackend.utils;

import org.springframework.stereotype.Component;

import com.kodehaus.stocksbackend.dto.PeticionDTO;
import com.kodehaus.stocksbackend.model.Peticion;

@Component
public class PeticionMapper {
    public PeticionDTO toDto(Peticion peticion) {
        return new PeticionDTO(
                peticion.getId(),
                peticion.getCorreo(),
                peticion.getTelefono(),
                peticion.getPlaza(),
                peticion.getIdPlan()
        );
    }
}
