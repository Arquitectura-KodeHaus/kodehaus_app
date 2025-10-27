package com.kodehaus.stocksbackend.service;

import com.kodehaus.stocksbackend.dto.CreateGerenteReq;
import com.kodehaus.stocksbackend.dto.GerenteDTO;
import com.kodehaus.stocksbackend.dto.UpdateGerenteReq;
import com.kodehaus.stocksbackend.model.Gerente;
import com.kodehaus.stocksbackend.model.Plaza;
import com.kodehaus.stocksbackend.repository.GerenteRepository;
import com.kodehaus.stocksbackend.repository.PlazaRepository;
import com.kodehaus.stocksbackend.utils.GerenteMapper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GerenteServiceImpl implements GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;

    @Autowired
    private PlazaRepository plazaRepository;

    @Autowired
    private GerenteMapper gerenteMapper;

    @Override
    @Transactional
    public GerenteDTO crearGerente(CreateGerenteReq request) {
        // Validar que el email no esté registrado
        if (gerenteRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Ya existe un gerente con el email: " + request.getEmail());
        }

        // Validar que la identificación no esté registrada
        if (gerenteRepository.existsByIdentificacion(request.getIdentificacion())) {
            throw new IllegalArgumentException("Ya existe un gerente con la identificación: " + request.getIdentificacion());
        }

        Gerente gerente = new Gerente();
        gerente.setNombre(request.getNombre());
        gerente.setApellido(request.getApellido());
        gerente.setEmail(request.getEmail());
        gerente.setPassword(request.getPassword()); // TODO: Encriptar password en producción
        gerente.setTelefono(request.getTelefono());
        gerente.setIdentificacion(request.getIdentificacion());
        gerente.setEstado("ACTIVO");

        // Asignar plaza si se proporciona
        if (request.getIdPlaza() != null) {
            Plaza plaza = plazaRepository.findById(request.getIdPlaza())
                .orElseThrow(() -> new EntityNotFoundException("Plaza no encontrada con ID: " + request.getIdPlaza()));
            
            // Verificar que la plaza no tenga ya un gerente asignado
            if (gerenteRepository.findByPlazaId(request.getIdPlaza()).isPresent()) {
                throw new IllegalArgumentException("La plaza ya tiene un gerente asignado");
            }
            
            gerente.setPlaza(plaza);
        }

        Gerente savedGerente = gerenteRepository.save(gerente);
        return gerenteMapper.toDto(savedGerente);
    }

    @Override
    @Transactional
    public GerenteDTO actualizarGerente(Long id, UpdateGerenteReq request) {
        Gerente gerente = gerenteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Gerente no encontrado con ID: " + id));

        if (request.getNombre() != null) {
            gerente.setNombre(request.getNombre());
        }
        if (request.getApellido() != null) {
            gerente.setApellido(request.getApellido());
        }
        if (request.getTelefono() != null) {
            gerente.setTelefono(request.getTelefono());
        }
        if (request.getEstado() != null) {
            gerente.setEstado(request.getEstado());
        }
        if (request.getIdPlaza() != null) {
            Plaza plaza = plazaRepository.findById(request.getIdPlaza())
                .orElseThrow(() -> new EntityNotFoundException("Plaza no encontrada con ID: " + request.getIdPlaza()));
            
            // Verificar que la plaza no tenga ya otro gerente asignado
            gerenteRepository.findByPlazaId(request.getIdPlaza()).ifPresent(g -> {
                if (!g.getId().equals(id)) {
                    throw new IllegalArgumentException("La plaza ya tiene un gerente asignado");
                }
            });
            
            gerente.setPlaza(plaza);
        }

        Gerente updatedGerente = gerenteRepository.save(gerente);
        return gerenteMapper.toDto(updatedGerente);
    }

    @Override
    public GerenteDTO obtenerGerentePorId(Long id) {
        Gerente gerente = gerenteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Gerente no encontrado con ID: " + id));
        return gerenteMapper.toDto(gerente);
    }

    @Override
    public GerenteDTO obtenerGerentePorEmail(String email) {
        Gerente gerente = gerenteRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Gerente no encontrado con email: " + email));
        return gerenteMapper.toDto(gerente);
    }

    @Override
    public GerenteDTO obtenerGerentePorPlaza(Long idPlaza) {
        Gerente gerente = gerenteRepository.findByPlazaId(idPlaza)
            .orElseThrow(() -> new EntityNotFoundException("No hay gerente asignado a la plaza con ID: " + idPlaza));
        return gerenteMapper.toDto(gerente);
    }

    @Override
    public List<GerenteDTO> obtenerTodosLosGerentes() {
        return gerenteRepository.findAll().stream()
            .map(gerenteMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<GerenteDTO> obtenerGerentesPorEstado(String estado) {
        return gerenteRepository.findByEstado(estado).stream()
            .map(gerenteMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void asignarPlaza(Long idGerente, Long idPlaza) {
        Gerente gerente = gerenteRepository.findById(idGerente)
            .orElseThrow(() -> new EntityNotFoundException("Gerente no encontrado con ID: " + idGerente));
        
        Plaza plaza = plazaRepository.findById(idPlaza)
            .orElseThrow(() -> new EntityNotFoundException("Plaza no encontrada con ID: " + idPlaza));

        // Verificar que la plaza no tenga ya otro gerente asignado
        gerenteRepository.findByPlazaId(idPlaza).ifPresent(g -> {
            if (!g.getId().equals(idGerente)) {
                throw new IllegalArgumentException("La plaza ya tiene un gerente asignado");
            }
        });

        gerente.setPlaza(plaza);
        gerenteRepository.save(gerente);
    }

    @Override
    @Transactional
    public void cambiarEstado(Long idGerente, String nuevoEstado) {
        Gerente gerente = gerenteRepository.findById(idGerente)
            .orElseThrow(() -> new EntityNotFoundException("Gerente no encontrado con ID: " + idGerente));
        
        gerente.setEstado(nuevoEstado);
        gerenteRepository.save(gerente);
    }

    @Override
    @Transactional
    public void eliminarGerente(Long id) {
        if (!gerenteRepository.existsById(id)) {
            throw new EntityNotFoundException("Gerente no encontrado con ID: " + id);
        }
        gerenteRepository.deleteById(id);
    }
}
