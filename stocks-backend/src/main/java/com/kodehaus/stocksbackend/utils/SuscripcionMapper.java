package com.kodehaus.stocksbackend.utils;

import com.kodehaus.stocksbackend.dto.CreateSuscripcionReq;
import com.kodehaus.stocksbackend.dto.SuscripcionDTO;
import com.kodehaus.stocksbackend.dto.UpdateSuscripcionReq;
import com.kodehaus.stocksbackend.model.Suscripcion;
import org.springframework.stereotype.Component;

@Component
public class SuscripcionMapper {

    public SuscripcionDTO toDTO(Suscripcion suscripcion) {
        if (suscripcion == null) return null;
        
        return new SuscripcionDTO(
            suscripcion.getId(),
            suscripcion.getPeriodicidad(),
            suscripcion.getFechaUltimoPago(),
            suscripcion.getFechaRenovacion(),
            suscripcion.getEstado(),
            suscripcion.getEstadoPago(),
            suscripcion.getPlan() != null ? suscripcion.getPlan().getTipo() : null,
            suscripcion.getPlaza() != null ? suscripcion.getPlaza().getNombre() : null
        );
    }

    public Suscripcion toEntity(CreateSuscripcionReq request) {
        if (request == null) return null;
        
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.setPeriodicidad(request.getPeriodicidad());
        suscripcion.setFechaUltimoPago(request.getFechaUltimoPago());
        suscripcion.setFechaRenovacion(request.getFechaRenovacion());
        suscripcion.setEstado(request.getEstado());
        return suscripcion;
    }

    public void updateEntityFromRequest(Suscripcion suscripcion, UpdateSuscripcionReq request) {
        if (suscripcion == null || request == null) return;
        
        if (request.getPeriodicidad() != null) {
            suscripcion.setPeriodicidad(request.getPeriodicidad());
        }
        if (request.getFechaUltimoPago() != null) {
            suscripcion.setFechaUltimoPago(request.getFechaUltimoPago());
        }
        if (request.getFechaRenovacion() != null) {
            suscripcion.setFechaRenovacion(request.getFechaRenovacion());
        }
        if (request.getEstado() != null) {
            suscripcion.setEstado(request.getEstado());
        }
    }
}