package com.kodehaus.stocksbackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodehaus.stocksbackend.dto.CreatePlazaReq;
import com.kodehaus.stocksbackend.dto.PlazaDTO;
import com.kodehaus.stocksbackend.dto.UpdatePlazaReq;
import com.kodehaus.stocksbackend.model.Plaza;
import com.kodehaus.stocksbackend.model.Ubicacion;
import com.kodehaus.stocksbackend.repository.PlazaRepository;
import com.kodehaus.stocksbackend.repository.UbicacionRepository;
import com.kodehaus.stocksbackend.utils.PlazaMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PlazaServiceImpl implements PlazaService {

    @Autowired
    private PlazaRepository plazaRepository;
    @Autowired
    private UbicacionRepository ubicacionRepository;
    @Autowired
    private PlazaMapper plazaMapper;

    @Override
    public List<PlazaDTO> findAll() {
        return plazaRepository.findAll().stream()
            .map(plazaMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public PlazaDTO findById(Long id) {
        Plaza plaza = plazaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Plaza no encontrada con ID: " + id));
        return plazaMapper.toDto(plaza);
    }

    @Override
    public PlazaDTO create(CreatePlazaReq plazaReq) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDepartamento(plazaReq.departamento());
        ubicacion.setCiudad(plazaReq.ciudad());
        ubicacion.setDireccion(plazaReq.direccion());

        // Guardamos explícitamente la Ubicacion para obtener un ID.
        Ubicacion savedUbicacion = ubicacionRepository.save(ubicacion);

        // 2. Crear la entidad Plaza
        Plaza plaza = new Plaza();
        plaza.setNombre(plazaReq.nombre());
        plaza.setContacto(plazaReq.contacto());
        plaza.setDominio(plazaReq.dominio());
        plaza.setFechaCreacion(LocalDateTime.now()); // Asignación de fecha en el Backend
        plaza.setUbicacion(savedUbicacion); // Enlace de la relación

        // 3. Persistencia y Respuesta
        Plaza savedPlaza = plazaRepository.save(plaza);
        return plazaMapper.toDto(savedPlaza);
    }

    @Override
    public PlazaDTO update(Long id, UpdatePlazaReq plazaReq) {
        Plaza existingPlaza = plazaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Plaza no encontrada para actualizar con ID: " + id));

        // 1. Manejar la Ubicación: Actualizamos la Ubicación existente
        Ubicacion ubicacion = existingPlaza.getUbicacion();
        
        // Asignación defensiva: en caso de que la Ubicacion fuera nula, creamos una nueva.
        if (ubicacion == null) {
            ubicacion = new Ubicacion();
            existingPlaza.setUbicacion(ubicacion);
        }
        
        // Actualizar los campos de la Ubicación con el DTO
        if(plazaReq.departamento() != null){
            ubicacion.setDepartamento(plazaReq.departamento());
        }
        if(plazaReq.ciudad() != null){
            ubicacion.setCiudad(plazaReq.ciudad());
        }
        if(plazaReq.direccion() != null){
            ubicacion.setDireccion(plazaReq.direccion());
        }

        // Guardar la Ubicación. Esto actualizará los datos en la base de datos.
        Ubicacion updatedUbicacion = ubicacionRepository.save(ubicacion);


        // 2. Actualizar los campos de Plaza
        if(plazaReq.nombre() != null){
            existingPlaza.setNombre(plazaReq.nombre());
        }
        if(plazaReq.contacto() != null){
            existingPlaza.setContacto(plazaReq.contacto());
        }
        if(plazaReq.dominio() != null){
            existingPlaza.setDominio(plazaReq.dominio());
        }
        existingPlaza.setUbicacion(updatedUbicacion); // Confirmar el enlace

        // 3. Persistencia y Respuesta
        Plaza updatedPlaza = plazaRepository.save(existingPlaza);
        
        return plazaMapper.toDto(updatedPlaza);
    }

    @Override
    public void delete(Long id) {
        Plaza plaza = plazaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Plaza no encontrada para eliminar con ID: " + id));

        Ubicacion ubicacionToDelete = plaza.getUbicacion();

        plazaRepository.delete(plaza);
        
        if (ubicacionToDelete != null) {
            ubicacionRepository.delete(ubicacionToDelete);
        }
    }    
}
