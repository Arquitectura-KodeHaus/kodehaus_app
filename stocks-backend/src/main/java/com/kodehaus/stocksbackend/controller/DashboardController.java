package com.kodehaus.stocksbackend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/modulos-activos")
    public Map<String, Object> getModulosActivos() {
         String sql = "SELECT COUNT(*) AS cantidad_modulos_activos FROM modulo WHERE estado = 'activo'";
        return jdbcTemplate.queryForMap(sql);
    }
    @GetMapping("/modulo-mas-usado")
    public Map<String, Object> getModuloMasUsado() {
    String sql = """
        SELECT m.nombre, COUNT(pm.id_plaza) AS usos
        FROM plaza_modulo pm
        JOIN modulo m ON pm.id_modulo = m.id
        GROUP BY m.nombre
        ORDER BY usos DESC
        LIMIT 1
        """;

    Map<String, Object> result = new HashMap<>();
    try {
        Map<String, Object> row = jdbcTemplate.queryForMap(sql);
        result.put("nombre", row.get("nombre"));
        result.put("usos", row.get("usos"));
    } catch (Exception e) {
        result.put("nombre", "Sin datos");
        result.put("usos", 0);
    }
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

    
}
