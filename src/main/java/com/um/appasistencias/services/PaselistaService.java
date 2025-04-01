package com.um.appasistencias.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.um.appasistencias.models.Paselista;
import com.um.appasistencias.repositories.CubiculosRepository;
import com.um.appasistencias.repositories.PaselistaRepository;
import com.um.appasistencias.repositories.ReportesRepository;
import com.um.appasistencias.repositories.UsuariosRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PaselistaService {
    @Autowired private PaselistaRepository paselistaRepository;
    @Autowired private ReportesRepository reportesRepository;
    @Autowired private CubiculosRepository cubiculosRepository;
    @Autowired private UsuariosRepository usuariosRepository;

    public Flux<Paselista> findAll() {
        return paselistaRepository.findAll();
    }

    public Mono<Paselista> findById(UUID id) {
        return paselistaRepository.findById(id);
    }

    public Mono<Boolean> findByType(String usuario, UUID id) {
        return usuariosRepository.findByUsername(usuario)
        .flatMap(u -> {
            LocalDate date = LocalDate.now();
            if(id == null) { 
                return cubiculosRepository.findByAsignacion(u.getId())
                .flatMap(c -> {
                    return paselistaRepository.findByCubiculo(u.getId(), c.getId(), date); 
                });
            }
            else { 
                return paselistaRepository.findByEvento(u.getId(), id, date); 
            }
        });
    }

    public Mono<Paselista> findByTypeData(String usuario, UUID id) {
        return usuariosRepository.findByUsername(usuario)
        .flatMap(u -> {
            LocalDate date = LocalDate.now();
            if(id == null) { 
                return cubiculosRepository.findByAsignacion(u.getId())
                .flatMap(c -> {
                    return paselistaRepository.findByCubiculoData(u.getId(), c.getId(), date); 
                });
            }
            else { 
                return paselistaRepository.findByEventoData(u.getId(), id, date); 
            }
        });
    }

    public Mono<Paselista> createByType(String usuario, UUID id) {
        return usuariosRepository.findByUsername(usuario)
        .flatMap(u -> {
            LocalDate fecha = LocalDate.now();
            LocalTime inicio = LocalTime.now();
            if(id == null) { 
                return cubiculosRepository.findByAsignacion(u.getId())
                .flatMap(c -> {
                    return paselistaRepository.createCubiculo(u.getId(), c.getId(), fecha, inicio); 
                });
            }
            else { 
                return paselistaRepository.createEvento(u.getId(), id, fecha, inicio); 
            }
        });
    }

    public Mono<Paselista> updateByType(String usuario, UUID id) {
        return usuariosRepository.findByUsername(usuario)
        .flatMap(u -> {
            LocalDate fecha = LocalDate.now();
            LocalTime fin = LocalTime.now();
            if (id == null) {
                return cubiculosRepository.findByAsignacion(u.getId())
                .flatMap(c -> {
                    return paselistaRepository.updateCubiculo(u.getId(), c.getId(), fecha, fin);
                });
            } else {
                return paselistaRepository.updateEvento(u.getId(), id, fecha, fin);
            }
        });
    }

}
