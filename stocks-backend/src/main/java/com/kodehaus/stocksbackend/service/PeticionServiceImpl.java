package com.kodehaus.stocksbackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodehaus.stocksbackend.dto.CreatePeticionReq;
import com.kodehaus.stocksbackend.dto.PeticionDTO;
import com.kodehaus.stocksbackend.model.Peticion;
import com.kodehaus.stocksbackend.repository.PeticionRepository;
import com.kodehaus.stocksbackend.utils.PeticionMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PeticionServiceImpl implements PeticionService{
    @Autowired
    private PeticionRepository peticionRepository;

    @Autowired
    private PeticionMapper peticionMapper;

    @Override
    public List<PeticionDTO> findAll() {
        return peticionRepository.findAll().stream()
        .map(peticionMapper::toDto)
        .collect(Collectors.toList());
    }

    @Override
    public PeticionDTO findById(Long id) {
        Peticion peticion = peticionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Plan no encontrada con ID: " + id));
        return peticionMapper.toDto(peticion);
    }

    @Override
    public PeticionDTO create(CreatePeticionReq peticionReq) {
        // Crear la entidad Plaza
        Peticion peticion = new Peticion();
        peticion.setCorreo(peticionReq.correo());
        peticion.setIdPlan(peticionReq.idPlan());
        peticion.setPlaza(peticionReq.plaza());
        peticion.setTelefono(peticionReq.telefono());

        // Persistencia y Respuesta
        Peticion savedPeticion = peticionRepository.save(peticion);

        return peticionMapper.toDto(savedPeticion);
    }

    @Override
    public void delete(Long id) {
        Peticion peticion = peticionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Módulo no encontrado para eliminar con ID: " + id));

        peticionRepository.delete(peticion);
    }
}
