package com.kodehaus.stocksbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.kodehaus.stocksbackend.service.CuentaGerenteService;
import com.kodehaus.stocksbackend.dto.CuentaGerenteDTO;
import com.kodehaus.stocksbackend.dto.CuentaGerenteRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/cuentas/gerente")
public class CuentaGerenteController {
    private final String gestionPlazasUrl;

    public CuentaGerenteController(@Value("${gestion.plazas.url}") String gestionPlazasUrl){
        this.gestionPlazasUrl = gestionPlazasUrl;
    }
    
    @Autowired
    private CuentaGerenteService cuentaService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/find")
    public ResponseEntity<List<CuentaGerenteDTO>> findAll() {
        List<CuentaGerenteDTO> cuentas = cuentaService.findAll();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CuentaGerenteDTO> findById(@PathVariable Long id) {
        CuentaGerenteDTO cuenta = cuentaService.findById(id);
        return ResponseEntity.ok(cuenta);
    }

    @GetMapping("/plaza/{id}")
    public ResponseEntity<CuentaGerenteDTO> findByPlazaId(@PathVariable Long id) {
        CuentaGerenteDTO cuenta = cuentaService.findByPlazaId(id);
        return ResponseEntity.ok(cuenta);
    }

    @PostMapping("/create")
    public ResponseEntity<CuentaGerenteDTO> create(@RequestBody CuentaGerenteRequest datos) {
        CuentaGerenteDTO newCuenta = cuentaService.create(datos);

        //Json enviado al otro servicio
        Map<String, Object> body = new HashMap<>();
        body.put("username", newCuenta.correo());
        body.put("email", newCuenta.correo());
        body.put("password", newCuenta.password());
        body.put("firstName", newCuenta.nombre());
        body.put("lastName", "");
        body.put("phoneNumber", "3008522478");
        body.put("plazaId", newCuenta.plaza_id());
        body.put("externalId", newCuenta.id());

        System.out.println("Informacion enviada: " + body);
        restTemplate.postForObject(gestionPlazasUrl + "/api/auth/external-register", body, Void.class);

        return new ResponseEntity<>(newCuenta, HttpStatus.CREATED); 
    } 
}
