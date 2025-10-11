package com.kodehaus.stocksbackend.service;

import java.util.List;

import com.kodehaus.stocksbackend.dto.CreatePlazaReq;
import com.kodehaus.stocksbackend.dto.PlazaDTO;
import com.kodehaus.stocksbackend.dto.UpdatePlazaReq;

public interface PlazaService {
    List<PlazaDTO> findAll();
    PlazaDTO findById(Long id);
    PlazaDTO create(CreatePlazaReq plazaReq);
    PlazaDTO update(Long id, UpdatePlazaReq plazaUpdateReq);
    void delete(Long id);
}
