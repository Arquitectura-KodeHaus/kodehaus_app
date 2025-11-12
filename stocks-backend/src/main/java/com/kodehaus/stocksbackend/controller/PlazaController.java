package com.kodehaus.stocksbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kodehaus.stocksbackend.dto.CreatePlazaReq;
import com.kodehaus.stocksbackend.dto.CuentaGerenteDTO;
import com.kodehaus.stocksbackend.dto.ModuloDTO;
import com.kodehaus.stocksbackend.dto.PlazaDTO;
import com.kodehaus.stocksbackend.dto.UpdatePlazaReq;
import com.kodehaus.stocksbackend.service.CuentaGerenteService;
import com.kodehaus.stocksbackend.service.PlazaService;

import jakarta.persistence.EntityNotFoundException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/plazas")
public class PlazaController {
    @Autowired
    private PlazaService plazaService;

    @Autowired
    private CuentaGerenteService cuentaService;

    @Autowired
    private RestTemplate restTemplate;

    private final String gestionPlazasUrl;

    public PlazaController(@Value("${gestion.plazas.url}") String gestionPlazasUrl){
        this.gestionPlazasUrl = gestionPlazasUrl;
    }

    @GetMapping("/find/activas")
    public ResponseEntity<List<PlazaDTO>> findAll() {
        List<PlazaDTO> plazas = plazaService.findAll();
        return ResponseEntity.ok(plazas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlazaDTO> findById(@PathVariable Long id) {
        try {
            PlazaDTO plaza = plazaService.findById(id);
            return ResponseEntity.ok(plaza);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PlazaDTO> create(@RequestBody CreatePlazaReq plazaReq) {
        System.out.println("Url:" + gestionPlazasUrl);

        PlazaDTO newPlaza = plazaService.create(plazaReq);

        //Json enviado al otro servicio
        Map<String, Object> body = new HashMap<>();
        body.put("name", newPlaza.nombre());
        body.put("description", "Plaza de mercado");
        body.put("address", newPlaza.direccion());
        body.put("phone_number", "+1-555-0005");
        body.put("email", newPlaza.contacto());
        body.put("opening_hours", "7am");
        body.put("closing_hours", "8pm");
        body.put("is_active",true);

        System.out.println("Informacion enviada: " + body);
        restTemplate.postForObject(gestionPlazasUrl + "/api/plazas", newPlaza, Void.class);

        return new ResponseEntity<>(newPlaza, HttpStatus.CREATED); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlazaDTO> update(@PathVariable Long id, @RequestBody UpdatePlazaReq plazaReq) {
        try {            
            PlazaDTO updatedPlaza = plazaService.update(id, plazaReq);
            return ResponseEntity.ok(updatedPlaza); // HTTP 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        }
    }

    // --- 5. DELETE: Eliminar una plaza por ID ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            // Eliminar gerente
            CuentaGerenteDTO cuenta = cuentaService.findByPlazaId(id);
            if(cuenta != null){
                cuentaService.delete(cuenta.id());
            }
        
            //Eliminar plaza
            plazaService.delete(id);
            // HTTP 204 No Content, la respuesta estándar para una eliminación exitosa sin cuerpo.
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found si no se encuentra para borrar.
        }
    }

    @GetMapping("/modulos/{id}")
    public ResponseEntity<List<ModuloDTO>> getModulos(@PathVariable Long id) {
        List<ModuloDTO> modulos = plazaService.getModulos(id);
        return ResponseEntity.ok(modulos);
    }
    
}
