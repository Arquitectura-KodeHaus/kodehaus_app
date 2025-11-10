package com.kodehaus.stocksbackend.service;

import java.util.List;

import com.kodehaus.stocksbackend.dto.CuentaGerenteDTO;
import com.kodehaus.stocksbackend.dto.CuentaGerenteRequest;

public interface CuentaGerenteService {
    List<CuentaGerenteDTO>findAll();
    CuentaGerenteDTO findById(Long id);
    CuentaGerenteDTO create(CuentaGerenteRequest datos);
}
