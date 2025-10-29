package com.kodehaus.stocksbackend.service;

import java.util.List;

import com.kodehaus.stocksbackend.dto.CreatePeticionReq;
import com.kodehaus.stocksbackend.dto.PeticionDTO;

public interface PeticionService{ 
   List<PeticionDTO> findAll();
   PeticionDTO findById(Long id);
   PeticionDTO create(CreatePeticionReq requestReq);
   void delete(Long id);
}
