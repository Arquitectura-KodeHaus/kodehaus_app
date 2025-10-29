package com.kodehaus.stocksbackend.utils;

import org.springframework.stereotype.Component;

import com.kodehaus.stocksbackend.dto.PlanDTO;
import com.kodehaus.stocksbackend.model.Plan;

@Component
public class PlanMapper {
    public PlanDTO toDto(Plan plan) {
        return new PlanDTO(
                plan.getId(),
                plan.getTipo(),
                plan.getNumModulos(),
                plan.getNumUsuarios(),
                plan.getPrecio()
        );
    }
}
