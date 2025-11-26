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
    public ResponseEntity<?> create(@RequestBody CuentaGerenteRequest datos) {
        // Validar que la contraseña tenga al menos 6 caracteres
        if (datos.password() == null || datos.password().length() < 6) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "La contraseña debe tener al menos 6 caracteres");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        CuentaGerenteDTO newCuenta = cuentaService.create(datos);

        // Partir el nombre
        String nombre = newCuenta.nombre();
        String[] nameParts = nombre.split(" ");
        String firstName = nameParts[0];

        // Construir JSON EXACTO que el otro servicio espera
        Map<String, Object> body = new HashMap<>();
        body.put("externalId", String.valueOf(newCuenta.cedula()));   // lo ideal es usar la cédula como externalId
        body.put("nombre", newCuenta.nombre());
        body.put("email", newCuenta.correo());
        body.put("rol", "MANAGER");                                   // corresponde con req.getRol()
        
        // IMPORTANTE: plazaExternalId debe coincidir con lo que el otro servicio guarda en la BD
        body.put("plazaExternalId", String.valueOf(newCuenta.plaza_id())); 
        
        body.put("phoneNumber", "+1-555-0005");

        System.out.println("JSON enviado al otro servicio: " + body);

        // Llamada al servicio java-0
        restTemplate.postForObject(
            "https://backend-service-java-2-616328447495.us-central1.run.app/api/users/externo",
            //"http://localhost:8070/api/users/externo",
            body,
            Void.class
        );

        return new ResponseEntity<>(newCuenta, HttpStatus.CREATED);
    }

}
