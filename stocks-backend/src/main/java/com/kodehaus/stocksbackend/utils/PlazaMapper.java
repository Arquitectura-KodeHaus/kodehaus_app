package com.kodehaus.stocksbackend.utils;

import org.springframework.stereotype.Component;

import com.kodehaus.stocksbackend.dto.PlazaDTO;
import com.kodehaus.stocksbackend.model.Plaza;
import com.kodehaus.stocksbackend.model.Suscripcion;

@Component
public class PlazaMapper {
    public PlazaDTO toDto(Plaza plaza) {
        // Safely get plan type from first subscription
        String planTipo = null;
        if (plaza.getSuscripciones() != null && !plaza.getSuscripciones().isEmpty()) {
            Suscripcion primeraSuscripcion = plaza.getSuscripciones().get(0);
            if (primeraSuscripcion != null && primeraSuscripcion.getPlan() != null) {
                planTipo = primeraSuscripcion.getPlan().getTipo();
            }
        }
        
        return new PlazaDTO(
            plaza.getId(),
            plaza.getNombre(),
            plaza.getContacto(),
            plaza.getDominio(),
            plaza.getFechaCreacion(),
            plaza.getUbicacion() != null ? plaza.getUbicacion().getDepartamento() : null,
            plaza.getUbicacion() != null ? plaza.getUbicacion().getCiudad() : null,      
            plaza.getUbicacion() != null ? plaza.getUbicacion().getDireccion() : null,
            planTipo
        );
    }
}
