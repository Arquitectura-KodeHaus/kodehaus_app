package com.kodehaus.stocksbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kodehaus.stocksbackend.dto.CuentaGerenteDTO;
import com.kodehaus.stocksbackend.dto.CuentaGerenteRequest;
import com.kodehaus.stocksbackend.model.CuentaGerente;
import com.kodehaus.stocksbackend.repository.CuentaGerenteRepository;
import com.kodehaus.stocksbackend.utils.CuentaGerenteMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CuentaGerenteServiceImpl implements CuentaGerenteService {
    @Autowired
    private CuentaGerenteRepository cuentaRepository;

    @Autowired
    private CuentaGerenteMapper cuentaMapper;

    @Override
    public List<CuentaGerenteDTO>findAll(){
        return cuentaRepository.findAll().stream()
        .map(cuentaMapper::toDto)
        .collect(Collectors.toList());
    }

    @Override
    public CuentaGerenteDTO findById(Long id){
        CuentaGerente cuenta = cuentaRepository.findById(id)
            .orElseThrow(()-> new EntityNotFoundException("Cuenta no encontrada con ID: " + id));
        return cuentaMapper.toDto(cuenta);
    }
    
    @Override
    public CuentaGerenteDTO findByPlazaId(Long id){
        CuentaGerente cuenta = cuentaRepository.findByPlazaId(id);

        if (cuenta == null) {
            return null;
        }

        return cuentaMapper.toDto(cuenta);
    }

    @Override
    @Transactional
    public CuentaGerenteDTO create(CuentaGerenteRequest datos){
        CuentaGerente cuenta = new CuentaGerente();
        cuenta.setCorreo(datos.correo());
        cuenta.setPassword(datos.password());
        cuenta.setCedula(datos.cedula());
        cuenta.setNombre(datos.nombre());
        cuenta.setPlaza_id(datos.plaza_id());

        CuentaGerente saved = cuentaRepository.save(cuenta);
        return cuentaMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void delete(Long id){
        CuentaGerente gerente = cuentaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Gerente no encontrado para eliminar con ID: " + id));

        cuentaRepository.delete(gerente);
    }
}
