package com.kodehaus.stocksbackend.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodehaus.stocksbackend.dto.CreatePlanReq;
import com.kodehaus.stocksbackend.dto.PlanDTO;
import com.kodehaus.stocksbackend.model.Plan;
import com.kodehaus.stocksbackend.repository.PlanRepository;
import com.kodehaus.stocksbackend.utils.PlanMapper;

import jakarta.persistence.EntityNotFoundException;


@Service
public class PlanServiceImpl implements PlanService{
    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlanMapper planMapper;

    @Override
    public List<PlanDTO> findAll() {
        return planRepository.findAll().stream()
        .map(planMapper::toDto)
        .collect(Collectors.toList());
    }

    @Override
    public PlanDTO create(CreatePlanReq plazaReq) {
        Plan plan = new Plan().builder().
            tipo(plazaReq.tipo()).
            numModulos(plazaReq.numModulos()).
            numUsuarios(plazaReq.numUsuarios()).
            precio(plazaReq.precio()).build();

        Plan savedPlan = planRepository.save(plan);
        return planMapper.toDto(savedPlan);
    }

    @Override
    public PlanDTO update(Long id, CreatePlanReq plazaUpdateReq) {
        Plan existingPlan = planRepository.findById(id).
            orElseThrow(() -> new EntityNotFoundException("Plan no encontrado para actualizar con ID: " + id));

        if (plazaUpdateReq.tipo() != null) {
            existingPlan.setTipo(plazaUpdateReq.tipo());
        }
        Plan updatedPlan = planRepository.save(existingPlan);
        return planMapper.toDto(updatedPlan);
    }

    @Override
    public void delete(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Módulo no encontrado para eliminar con ID: " + id));

        planRepository.delete(plan);
    }
}
