package com.kodehaus.stocksbackend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kodehaus.stocksbackend.dto.ModuloDTO;
import com.kodehaus.stocksbackend.model.Modulo;
import com.kodehaus.stocksbackend.service.ModuloService;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ModuloService moduloService;
    @GetMapping("/modulos-activos")
    public Map<String, Object> getModulosActivos() {
        List<ModuloDTO> modulos = moduloService.findAll();

        long activos = modulos.stream()
                .filter(m -> "Activo".equalsIgnoreCase(m.estado()))
                .count();

        Map<String, Object> response = new HashMap<>();
        response.put("cantidad_modulos_activos", activos);
        return response;
    }

    
    @GetMapping("/modulo-mas-usado")
    public Map<String, Object> getModuloMasUsado() {
        List<ModuloDTO> modulos = moduloService.findAll();

        if (modulos.isEmpty()) {
            Map<String, Object> empty = new HashMap<>();
            empty.put("nombre", "Sin datos");
            empty.put("numeroPlazas", 0L);
            return empty;
        }

        // Encuentra el módulo con más plazas
        ModuloDTO masUsado = modulos.stream()
                .max(Comparator.comparingLong(ModuloDTO::numeroPlazas))
                .orElse(modulos.get(0));

        // Verifica si todos tienen el mismo número de plazas
        boolean todosIguales = modulos.stream()
                .allMatch(m -> m.numeroPlazas().equals(masUsado.numeroPlazas()));

        // Usa el primero si todos son iguales
        ModuloDTO seleccionado = todosIguales ? modulos.get(0) : masUsado;

        Map<String, Object> result = new HashMap<>();
        result.put("nombre", seleccionado.nombre());
        result.put("numeroPlazas", seleccionado.numeroPlazas());
        return result;
    }


    @GetMapping("/total-ganancias")
public Map<String, Object> getTotalGanancias() {
    String sql = """
        SELECT COALESCE(SUM(p.precio), 0) AS total
        FROM suscripcion s
        JOIN plan p ON s.id_plan = p.id
        WHERE s.estado = 'activa'
    """;

    Double total = jdbcTemplate.queryForObject(sql, Double.class);
    Map<String, Object> result = new HashMap<>();
    result.put("total", total);
    return result;
}

    @GetMapping("/plazas-activas")
    public Map<String, Object> getPlazasActivas() {
        String sql = "SELECT COUNT(*) AS cantidad_plazas_activas FROM plaza";
        return jdbcTemplate.queryForMap(sql);
    }
    
}
