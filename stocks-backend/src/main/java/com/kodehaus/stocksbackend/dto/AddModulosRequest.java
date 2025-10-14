package com.kodehaus.stocksbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddModulosRequest {
    private List<Long> moduloIds;
}
