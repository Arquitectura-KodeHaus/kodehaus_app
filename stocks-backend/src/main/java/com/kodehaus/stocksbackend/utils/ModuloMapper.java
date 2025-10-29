package com.kodehaus.stocksbackend.utils;

import org.springframework.stereotype.Component;

import com.kodehaus.stocksbackend.dto.ModuloDTO;
import com.kodehaus.stocksbackend.model.Modulo;

@Component
public class ModuloMapper {

    private Long calculatePlazaCount(Modulo modulo) {
        if (modulo.getSuscripciones() == null) {
            return 0L;
        }

        return modulo.getSuscripciones().stream()
                .map(suscripcion -> suscripcion.getPlaza())
                // Filtra para asegurar que la plaza no sea nula
                .filter(plaza -> plaza != null)
                // Esto es lo más importante: cuenta solo las Plazas distintas
                .distinct() 
                .count();
    }

    public ModuloDTO toDto(Modulo modulo) {
        Long numeroPlazas = calculatePlazaCount(modulo);
        return new ModuloDTO(
                modulo.getId(),
                modulo.getNombre(),
                modulo.getEstado(),
                modulo.getDescripcion(),
                numeroPlazas
        );
    }
}