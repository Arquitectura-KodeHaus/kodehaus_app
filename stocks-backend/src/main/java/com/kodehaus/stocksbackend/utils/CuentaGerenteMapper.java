package com.kodehaus.stocksbackend.utils;

import com.kodehaus.stocksbackend.dto.CuentaGerenteDTO;
import com.kodehaus.stocksbackend.model.CuentaGerente;
import org.springframework.stereotype.Component;

@Component
public class CuentaGerenteMapper {
    public CuentaGerenteDTO toDto(CuentaGerente cuenta){
        return new CuentaGerenteDTO(
            cuenta.getId(),
            cuenta.getCorreo(),
            cuenta.getPassword(),
            cuenta.getCedula(),
            cuenta.getNombre(),
            cuenta.getPlaza_id()
        );
    }
}
