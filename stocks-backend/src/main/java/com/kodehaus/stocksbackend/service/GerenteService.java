package com.kodehaus.stocksbackend.service;

import com.kodehaus.stocksbackend.dto.CreateGerenteReq;
import com.kodehaus.stocksbackend.dto.GerenteDTO;
import com.kodehaus.stocksbackend.dto.UpdateGerenteReq;

import java.util.List;

public interface GerenteService {
    
    GerenteDTO crearGerente(CreateGerenteReq request);
    
    GerenteDTO actualizarGerente(Long id, UpdateGerenteReq request);
    
    GerenteDTO obtenerGerentePorId(Long id);
    
    GerenteDTO obtenerGerentePorEmail(String email);
    
    GerenteDTO obtenerGerentePorPlaza(Long idPlaza);
    
    List<GerenteDTO> obtenerTodosLosGerentes();
    
    List<GerenteDTO> obtenerGerentesPorEstado(String estado);
    
    void asignarPlaza(Long idGerente, Long idPlaza);
    
    void cambiarEstado(Long idGerente, String nuevoEstado);
    
    void eliminarGerente(Long id);
}
