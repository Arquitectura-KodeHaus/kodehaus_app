package com.kodehaus.stocksbackend.service;

import com.kodehaus.stocksbackend.dto.SuscripcionDTO;
import com.kodehaus.stocksbackend.dto.AddModulosRequest;
import com.kodehaus.stocksbackend.model.*;
import com.kodehaus.stocksbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SuscripcionServiceImpl implements SuscripcionService {

    @Autowired
    private SuscripcionRepository suscripcionRepository;
    
    @Autowired
    private ModuloRepository moduloRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionDTO> getAllSuscripciones() {
        List<Suscripcion> suscripciones = suscripcionRepository.findAllWithPlazaAndPlan();
        return suscripciones.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SuscripcionDTO getSuscripcionById(Long id) {
        Suscripcion suscripcion = suscripcionRepository.findByIdWithFullInfo(id);
        if (suscripcion == null) {
            throw new RuntimeException("Suscripción no encontrada con ID: " + id);
        }
        return convertToFullDTO(suscripcion);
    }

    @Override
    public void addModulosToSuscripcion(Long suscripcionId, AddModulosRequest request) {
        Suscripcion suscripcion = suscripcionRepository.findById(suscripcionId)
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada con ID: " + suscripcionId));
        
        // Verificar capacidad del plan
        Plan plan = suscripcion.getPlan();
        int currentModulosCount = suscripcion.getModulos() != null ? suscripcion.getModulos().size() : 0;
        int newModulosCount = request.getModuloIds().size();
        
        if (currentModulosCount + newModulosCount > plan.getNumModulos()) {
            throw new RuntimeException("No se pueden agregar " + newModulosCount + 
                    " módulos. La suscripción ya tiene " + currentModulosCount + 
                    " módulos y el plan permite máximo " + plan.getNumModulos() + " módulos.");
        }
        
        // Obtener los módulos por IDs
        List<Modulo> modulosToAdd = moduloRepository.findAllById(request.getModuloIds());
        
        if (modulosToAdd.size() != request.getModuloIds().size()) {
            throw new RuntimeException("Algunos módulos no fueron encontrados");
        }
        
        // Agregar módulos a la suscripción
        if (suscripcion.getModulos() == null) {
            suscripcion.setModulos(new java.util.ArrayList<>());
        }
        
        for (Modulo modulo : modulosToAdd) {
            if (!suscripcion.getModulos().contains(modulo)) {
                suscripcion.getModulos().add(modulo);
            }
        }
        
        suscripcionRepository.save(suscripcion);
    }

    @Override
    public void removeModuloFromSuscripcion(Long suscripcionId, Long moduloId) {
        Suscripcion suscripcion = suscripcionRepository.findById(suscripcionId)
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada con ID: " + suscripcionId));
        
        if (suscripcion.getModulos() == null || suscripcion.getModulos().isEmpty()) {
            throw new RuntimeException("La suscripción no tiene módulos asignados");
        }
        
        boolean removed = suscripcion.getModulos().removeIf(modulo -> modulo.getId().equals(moduloId));
        
        if (!removed) {
            throw new RuntimeException("El módulo con ID " + moduloId + " no está asignado a esta suscripción");
        }
        
        suscripcionRepository.save(suscripcion);
    }

    private SuscripcionDTO convertToDTO(Suscripcion suscripcion) {
        SuscripcionDTO dto = new SuscripcionDTO();
        dto.setId(suscripcion.getId());
        dto.setPeriodicidad(suscripcion.getPeriodicidad());
        dto.setFechaUltimoPago(suscripcion.getFechaUltimoPago());
        dto.setFechaRenovacion(suscripcion.getFechaRenovacion());
        dto.setEstado(suscripcion.getEstado());
        
        // Plaza info
        if (suscripcion.getPlaza() != null) {
            SuscripcionDTO.PlazaInfoDTO plazaInfo = new SuscripcionDTO.PlazaInfoDTO();
            plazaInfo.setId(suscripcion.getPlaza().getId());
            plazaInfo.setNombre(suscripcion.getPlaza().getNombre());
            plazaInfo.setContacto(suscripcion.getPlaza().getContacto());
            plazaInfo.setDominio(suscripcion.getPlaza().getDominio());
            plazaInfo.setFechaCreacion(suscripcion.getPlaza().getFechaCreacion());
            
            // Ubicación info
            if (suscripcion.getPlaza().getUbicacion() != null) {
                SuscripcionDTO.UbicacionInfoDTO ubicacionInfo = new SuscripcionDTO.UbicacionInfoDTO();
                ubicacionInfo.setId(suscripcion.getPlaza().getUbicacion().getId());
                ubicacionInfo.setNombre(suscripcion.getPlaza().getUbicacion().getDepartamento());
                ubicacionInfo.setDireccion(suscripcion.getPlaza().getUbicacion().getDireccion());
                ubicacionInfo.setCiudad(suscripcion.getPlaza().getUbicacion().getCiudad());
                ubicacionInfo.setPais(suscripcion.getPlaza().getUbicacion().getDepartamento());
                plazaInfo.setUbicacion(ubicacionInfo);
            }
            
            dto.setPlaza(plazaInfo);
        }
        
        // Plan info
        if (suscripcion.getPlan() != null) {
            SuscripcionDTO.PlanInfoDTO planInfo = new SuscripcionDTO.PlanInfoDTO();
            planInfo.setId(suscripcion.getPlan().getId());
            planInfo.setTipo(suscripcion.getPlan().getTipo());
            planInfo.setNumModulos(suscripcion.getPlan().getNumModulos());
            planInfo.setNumUsuarios(suscripcion.getPlan().getNumUsuarios());
            planInfo.setPrecio(suscripcion.getPlan().getPrecio());
            dto.setPlan(planInfo);
        }
        
        // Módulos info
        if (suscripcion.getModulos() != null) {
            List<SuscripcionDTO.ModuloInfoDTO> modulosInfo = suscripcion.getModulos().stream()
                    .map(modulo -> {
                        SuscripcionDTO.ModuloInfoDTO moduloInfo = new SuscripcionDTO.ModuloInfoDTO();
                        moduloInfo.setId(modulo.getId());
                        moduloInfo.setNombre(modulo.getNombre());
                        moduloInfo.setEstado(modulo.getEstado());
                        return moduloInfo;
                    })
                    .collect(Collectors.toList());
            dto.setModulos(modulosInfo);
        }
        
        return dto;
    }

    private SuscripcionDTO convertToFullDTO(Suscripcion suscripcion) {
        SuscripcionDTO dto = convertToDTO(suscripcion);
        
        // Módulos info
        if (suscripcion.getModulos() != null) {
            List<SuscripcionDTO.ModuloInfoDTO> modulosInfo = suscripcion.getModulos().stream()
                    .map(modulo -> {
                        SuscripcionDTO.ModuloInfoDTO moduloInfo = new SuscripcionDTO.ModuloInfoDTO();
                        moduloInfo.setId(modulo.getId());
                        moduloInfo.setNombre(modulo.getNombre());
                        moduloInfo.setEstado(modulo.getEstado());
                        return moduloInfo;
                    })
                    .collect(Collectors.toList());
            dto.setModulos(modulosInfo);
        }
        
        return dto;
    }
}