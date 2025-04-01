package com.um.appasistencias.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.um.appasistencias.models.Eventos;
import com.um.appasistencias.models.dto.EventosEstado;
import com.um.appasistencias.repositories.EventosRepository;
import com.um.appasistencias.repositories.UsuariosRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventosService {
    @Autowired private EventosRepository eventosRepository;
    @Autowired private UsuariosRepository usuariosRepository;

    public Flux<Eventos> findAll(){
        return eventosRepository.findAll();
    }

    public Flux<Eventos> findAllToday() {
        return eventosRepository.findAllToday();
    }

    public Mono<Eventos> findById(UUID id) {
        return eventosRepository.findByUuid(id);
    }

    public Mono<Void> deleteById(UUID id) {
        return eventosRepository.deleteByUuid(id);
    }

    public Mono<Boolean> exists(UUID id) {
        try {
            return eventosRepository.exists(id);
        } catch (Exception e) {
            return Mono.just(false);
        }
    }

    public Mono<Eventos> update(UUID id, Eventos evento){
        return eventosRepository.update(
            evento.getTitulo(), evento.getLugar(), evento.getInicio(), evento.getFin(), evento.getFecha(), id
        );
    }

    public Mono<Eventos> save(String titulo, String lugar, LocalTime inicio, LocalTime fin, LocalDate fecha) {
        return eventosRepository.save(new Eventos(titulo, lugar, inicio, fin, fecha));
    }

    public Flux<EventosEstado> findByUserWithState(UUID usuario) {
        LocalDate fecha = LocalDate.now();
        return eventosRepository.findByUserWithState(usuario, fecha);
    }
}
