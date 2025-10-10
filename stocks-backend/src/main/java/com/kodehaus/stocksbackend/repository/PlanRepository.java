package com.kodehaus.stocksbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodehaus.stocksbackend.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
