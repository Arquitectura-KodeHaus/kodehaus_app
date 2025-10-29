package com.kodehaus.stocksbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodehaus.stocksbackend.model.Peticion;

public interface PeticionRepository extends JpaRepository<Peticion, Long>{}
