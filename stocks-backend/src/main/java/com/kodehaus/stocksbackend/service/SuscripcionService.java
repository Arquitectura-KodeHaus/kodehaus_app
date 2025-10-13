package com.kodehaus.stocksbackend.service;

import com.kodehaus.stocksbackend.dto.CreateSuscripcionReq;
import com.kodehaus.stocksbackend.dto.SuscripcionDTO;
import com.kodehaus.stocksbackend.dto.UpdateSuscripcionReq;

import java.util.List;
import java.util.Optional;

public interface SuscripcionService {
    List<SuscripcionDTO> findAll();
    List<SuscripcionDTO> findByEstado(String estado);
    List<SuscripcionDTO> findByEstadoPago(String estadoPago);
    Optional<SuscripcionDTO> findById(Long id);
    SuscripcionDTO create(CreateSuscripcionReq request);
    Optional<SuscripcionDTO> update(Long id, UpdateSuscripcionReq request);
    boolean delete(Long id);
    long countVigentes();
    long countEnMora();
}