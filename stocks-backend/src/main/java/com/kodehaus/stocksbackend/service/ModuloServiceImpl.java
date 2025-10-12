package com.kodehaus.stocksbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodehaus.stocksbackend.dto.CreateModuloReq;
import com.kodehaus.stocksbackend.dto.ModuloDTO;
import com.kodehaus.stocksbackend.dto.UpdateModuloReq;
import com.kodehaus.stocksbackend.model.Modulo;
import com.kodehaus.stocksbackend.repository.ModuloRepository;
import com.kodehaus.stocksbackend.utils.ModuloMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ModuloServiceImpl implements ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private ModuloMapper moduloMapper;

    @Override
    public List<ModuloDTO> findAll() {
        return moduloRepository.findAll().stream()
                .map(moduloMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ModuloDTO findById(Long id) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Módulo no encontrado con ID: " + id));
        return moduloMapper.toDto(modulo);
    }

    @Override
    public ModuloDTO create(CreateModuloReq moduloReq) {
        Modulo modulo = new Modulo();
        modulo.setNombre(moduloReq.nombre());
        modulo.setEstado(moduloReq.estado());

        Modulo savedModulo = moduloRepository.save(modulo);
        return moduloMapper.toDto(savedModulo);
    }

    @Override
    public ModuloDTO update(Long id, UpdateModuloReq moduloReq) {
        Modulo existingModulo = moduloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Módulo no encontrado para actualizar con ID: " + id));

        if (moduloReq.nombre() != null) {
            existingModulo.setNombre(moduloReq.nombre());
        }
        if (moduloReq.estado() != null) {
            existingModulo.setEstado(moduloReq.estado());
        }

        Modulo updatedModulo = moduloRepository.save(existingModulo);
        return moduloMapper.toDto(updatedModulo);
    }

    @Override
    public void delete(Long id) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Módulo no encontrado para eliminar con ID: " + id));

        moduloRepository.delete(modulo);
    }
}
