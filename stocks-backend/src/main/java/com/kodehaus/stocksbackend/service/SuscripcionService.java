package com.kodehaus.stocksbackend.service;

import com.kodehaus.stocksbackend.dto.SuscripcionDTO;
import com.kodehaus.stocksbackend.dto.AddModulosRequest;
import java.util.List;

public interface SuscripcionService {
    List<SuscripcionDTO> getAllSuscripciones();
    SuscripcionDTO getSuscripcionById(Long id);
    void addModulosToSuscripcion(Long suscripcionId, AddModulosRequest request);
    void removeModuloFromSuscripcion(Long suscripcionId, Long moduloId);
}
