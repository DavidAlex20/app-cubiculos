package com.um.appasistencias.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.um.appasistencias.models.Eventos;
import com.um.appasistencias.repositories.EventosRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventosService {
    @Autowired private EventosRepository eventosRepository;

    public Flux<Eventos> findAll(){
        return eventosRepository.findAll();
    }

    public Mono<Eventos> findById(String id) {
        UUID uuid = UUID.fromString(id);
        return eventosRepository.findByUuid(uuid);
    }

    public Mono<Void> deleteById(String id) {
        UUID uuid = UUID.fromString(id);
        return eventosRepository.deleteByUuid(uuid);
    }

    public Mono<Boolean> exists(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return eventosRepository.exists(uuid);
        } catch (Exception e) {
            return Mono.just(false);
        }
    }

    public Mono<Eventos> update(String id, Eventos evento){
        UUID uuid = UUID.fromString(id);
        return eventosRepository.update(
            evento.getTitulo(), evento.getLugar(), evento.getInicio(), evento.getFin(), evento.getFecha(), uuid
        );
    }

    public Mono<Eventos> save(String titulo, String lugar, LocalTime inicio, LocalTime fin, LocalDate fecha) {
        return eventosRepository.save(new Eventos(titulo, lugar, inicio, fin, fecha));
    }
}
