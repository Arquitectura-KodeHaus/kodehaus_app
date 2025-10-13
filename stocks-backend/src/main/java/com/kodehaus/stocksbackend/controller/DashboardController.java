package com.kodehaus.stocksbackend.controller;

import com.kodehaus.stocksbackend.service.SuscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private SuscripcionService suscripcionService;

    @GetMapping("/modulos-activos")
    public Map<String, Object> getModulosActivos() {
        String sql = "SELECT COUNT(*) AS cantidad_modulos_activos FROM modulo WHERE estado = 'activo'";
        try {
            return jdbcTemplate.queryForMap(sql);
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("cantidad_modulos_activos", 0);
            return result;
        }
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
            WHERE s.estado = 'Vigente'
            """;

        try {
            Double total = jdbcTemplate.queryForObject(sql, Double.class);
            Map<String, Object> result = new HashMap<>();
            result.put("total", total != null ? total : 0.0);
            return result;
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            result.put("total", 0.0);
            return result;
        }
    }

    @GetMapping("/suscripciones-vigentes")
    public Map<String, Object> getSuscripcionesVigentes() {
        Map<String, Object> result = new HashMap<>();
        result.put("cantidad", suscripcionService.countVigentes());
        return result;
    }

    @GetMapping("/suscripciones-mora")
    public Map<String, Object> getSuscripcionesEnMora() {
        Map<String, Object> result = new HashMap<>();
        result.put("cantidad", suscripcionService.countEnMora());
        return result;
    }

    @GetMapping("/resumen")
    public Map<String, Object> getResumenDashboard() {
        Map<String, Object> resumen = new HashMap<>();
        
        // Módulos activos
        try {
            String sql = "SELECT COUNT(*) FROM modulo WHERE estado = 'activo'";
            Long modulosActivos = jdbcTemplate.queryForObject(sql, Long.class);
            resumen.put("modulosActivos", modulosActivos != null ? modulosActivos : 0);
        } catch (Exception e) {
            resumen.put("modulosActivos", 0);
        }
        
        // Plazas activas
        try {
            String sql = "SELECT COUNT(*) FROM plaza";
            Long plazasActivas = jdbcTemplate.queryForObject(sql, Long.class);
            resumen.put("plazasActivas", plazasActivas != null ? plazasActivas : 0);
        } catch (Exception e) {
            resumen.put("plazasActivas", 0);
        }
        
        // Suscripciones
        resumen.put("suscripcionesVigentes", suscripcionService.countVigentes());
        resumen.put("suscripcionesEnMora", suscripcionService.countEnMora());
        
        // Ganancias totales
        try {
            String sql = """
                SELECT COALESCE(SUM(p.precio), 0) AS total
                FROM suscripcion s
                JOIN plan p ON s.id_plan = p.id
                WHERE s.estado = 'Vigente'
                """;
            Double totalGanancias = jdbcTemplate.queryForObject(sql, Double.class);
            resumen.put("totalGanancias", totalGanancias != null ? totalGanancias : 0.0);
        } catch (Exception e) {
            resumen.put("totalGanancias", 0.0);
        }
        
        return resumen;
    }
}
