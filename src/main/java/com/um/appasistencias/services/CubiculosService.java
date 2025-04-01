package com.um.appasistencias.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.um.appasistencias.models.Cubiculos;
import com.um.appasistencias.models.dto.CubiculosDto;
import com.um.appasistencias.repositories.CubiculosRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CubiculosService {
    @Autowired private CubiculosRepository cubiculosRepository;
    
    public Flux<Cubiculos> findAll() {
        return cubiculosRepository.findAll();
    }

    public Flux<CubiculosDto> findAllWithAsignacion() {
        return cubiculosRepository.findAllWithAsignacion();
    }

    public Mono<Cubiculos> findById(UUID id) {
        return cubiculosRepository.findByUuid(id);
    }

    public Mono<Void> deleteById(UUID id) {
        return cubiculosRepository.deleteByUuid(id);
    }

    public Mono<Boolean> exists(UUID id) {
        try {
            return cubiculosRepository.exists(id);   
        } catch (Exception e) {
            return Mono.just(false);
        }
    }

    public Mono<Cubiculos> update(UUID id, Cubiculos cubiculo){
        return cubiculosRepository.update(
            cubiculo.getNumero(), cubiculo.getEdificio(), id
        );
    }

    public Mono<Cubiculos> updateDisponible(UUID id, boolean disponible){
        return cubiculosRepository.updateDisponible(
            disponible, id
        );
    }

    public Mono<Cubiculos> updateAsignacion(UUID id, UUID asignacion){
        return cubiculosRepository.updateAsignacion(
            asignacion, id
        );
    }

    public Mono<Cubiculos> save(int numero, String edificio, boolean disponible, UUID asignacion) {
        return cubiculosRepository.save(new Cubiculos(numero, edificio, disponible, asignacion));      
    }
}
