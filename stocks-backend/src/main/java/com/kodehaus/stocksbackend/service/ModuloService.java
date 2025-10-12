package com.kodehaus.stocksbackend.service;

import java.util.List;

import com.kodehaus.stocksbackend.dto.CreateModuloReq;
import com.kodehaus.stocksbackend.dto.ModuloDTO;
import com.kodehaus.stocksbackend.dto.UpdateModuloReq;

public interface ModuloService {
    List<ModuloDTO> findAll();
    ModuloDTO findById(Long id);
    ModuloDTO create(CreateModuloReq moduloReq);
    ModuloDTO update(Long id, UpdateModuloReq moduloUpdateReq);
    void delete(Long id);
}
