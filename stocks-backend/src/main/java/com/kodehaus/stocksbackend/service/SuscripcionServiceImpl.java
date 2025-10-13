package com.kodehaus.stocksbackend.service;

import com.kodehaus.stocksbackend.dto.CreateSuscripcionReq;
import com.kodehaus.stocksbackend.dto.SuscripcionDTO;
import com.kodehaus.stocksbackend.dto.UpdateSuscripcionReq;
import com.kodehaus.stocksbackend.model.Plan;
import com.kodehaus.stocksbackend.model.Plaza;
import com.kodehaus.stocksbackend.model.Suscripcion;
import com.kodehaus.stocksbackend.repository.PlanRepository;
import com.kodehaus.stocksbackend.repository.PlazaRepository;
import com.kodehaus.stocksbackend.repository.SuscripcionRepository;
import com.kodehaus.stocksbackend.utils.SuscripcionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SuscripcionServiceImpl implements SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;
    private final PlazaRepository plazaRepository;
    private final PlanRepository planRepository;
    private final SuscripcionMapper suscripcionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionDTO> findAll() {
        return suscripcionRepository.findAll().stream()
                .map(suscripcionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionDTO> findByEstado(String estado) {
        return suscripcionRepository.findByEstado(estado).stream()
                .map(suscripcionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionDTO> findByEstadoPago(String estadoPago) {
        List<Suscripcion> suscripciones = suscripcionRepository.findAll();
        return suscripciones.stream()
                .filter(s -> estadoPago.equals(s.getEstadoPago()))
                .map(suscripcionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SuscripcionDTO> findById(Long id) {
        return suscripcionRepository.findById(id)
                .map(suscripcionMapper::toDTO);
    }

    @Override
    public SuscripcionDTO create(CreateSuscripcionReq request) {
        Suscripcion suscripcion = suscripcionMapper.toEntity(request);
        
        // Buscar y asignar plaza
        Plaza plaza = plazaRepository.findById(request.getIdPlaza())
                .orElseThrow(() -> new RuntimeException("Plaza no encontrada"));
        suscripcion.setPlaza(plaza);
        
        // Buscar y asignar plan
        Plan plan = planRepository.findById(request.getIdPlan())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        suscripcion.setPlan(plan);
        
        Suscripcion saved = suscripcionRepository.save(suscripcion);
        return suscripcionMapper.toDTO(saved);
    }

    @Override
    public Optional<SuscripcionDTO> update(Long id, UpdateSuscripcionReq request) {
        return suscripcionRepository.findById(id)
                .map(suscripcion -> {
                    suscripcionMapper.updateEntityFromRequest(suscripcion, request);
                    
                    // Actualizar plaza si se proporciona
                    if (request.getIdPlaza() != null) {
                        Plaza plaza = plazaRepository.findById(request.getIdPlaza())
                                .orElseThrow(() -> new RuntimeException("Plaza no encontrada"));
                        suscripcion.setPlaza(plaza);
                    }
                    
                    // Actualizar plan si se proporciona
                    if (request.getIdPlan() != null) {
                        Plan plan = planRepository.findById(request.getIdPlan())
                                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
                        suscripcion.setPlan(plan);
                    }
                    
                    Suscripcion updated = suscripcionRepository.save(suscripcion);
                    return suscripcionMapper.toDTO(updated);
                });
    }

    @Override
    public boolean delete(Long id) {
        if (suscripcionRepository.existsById(id)) {
            suscripcionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public long countVigentes() {
        return suscripcionRepository.countByEstado("Vigente");
    }

    @Override
    @Transactional(readOnly = true)
    public long countEnMora() {
        List<Suscripcion> suscripciones = suscripcionRepository.findAll();
        return suscripciones.stream()
                .filter(s -> "En mora".equals(s.getEstadoPago()))
                .count();
    }
}