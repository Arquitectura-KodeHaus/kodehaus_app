package com.kodehaus.stocksbackend.service;

import java.util.List;

import com.kodehaus.stocksbackend.dto.CreatePlanReq;
import com.kodehaus.stocksbackend.dto.PlanDTO;

public interface PlanService {
    List<PlanDTO> findAll();
    PlanDTO create(CreatePlanReq plazaReq);
    PlanDTO update(Long id, CreatePlanReq plazaUpdateReq);
    void delete(Long id);
}
