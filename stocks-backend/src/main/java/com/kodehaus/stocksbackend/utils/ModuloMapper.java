package com.kodehaus.stocksbackend.utils;

import org.springframework.stereotype.Component;

import com.kodehaus.stocksbackend.dto.ModuloDTO;
import com.kodehaus.stocksbackend.model.Modulo;

@Component
public class ModuloMapper {

    public ModuloDTO toDto(Modulo modulo) {
        return new ModuloDTO(
                modulo.getId(),
                modulo.getNombre(),
                modulo.getEstado()
        );
    }
}