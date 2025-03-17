package com.um.appasistencias.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.um.appasistencias.models.Cubiculos;
import com.um.appasistencias.repositories.CubiculosRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CubiculosService {
    @Autowired private CubiculosRepository cubiculosRepository;
    
    public Flux<Cubiculos> findAll() {
        return cubiculosRepository.findAll();
    }

    public Mono<Cubiculos> findById(String id) {
        UUID uuid = UUID.fromString(id);
        return cubiculosRepository.findByUuid(uuid);
    }

    public Mono<Void> deleteById(String id) {
        UUID uuid = UUID.fromString(id);
        return cubiculosRepository.deleteByUuid(uuid);
    }

    public Mono<Boolean> exists(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return cubiculosRepository.exists(uuid);   
        } catch (Exception e) {
            return Mono.just(false);
        }
    }

    public Mono<Cubiculos> update(String id, Cubiculos cubiculo){
        UUID uuid = UUID.fromString(id);
        return cubiculosRepository.update(
            cubiculo.getNumero(), cubiculo.getEdificio(), cubiculo.isDisponible(), uuid
        );
    }

    public Mono<Cubiculos> save(String id, int numero, String edificio, boolean disponible) {
        return cubiculosRepository.save(new Cubiculos(numero, edificio, disponible));      
    }
}
