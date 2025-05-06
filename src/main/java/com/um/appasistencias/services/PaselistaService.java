package com.um.appasistencias.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.um.appasistencias.models.Paselista;
import com.um.appasistencias.repositories.CubiculosRepository;
import com.um.appasistencias.repositories.PaselistaRepository;
import com.um.appasistencias.repositories.UsuariosRepository;

import reactor.core.publisher.Mono;

@Service
public class PaselistaService {
    private static final Logger log = LoggerFactory.getLogger(PaselistaService.class);
    @Autowired private PaselistaRepository paselistaRepository;
    @Autowired private CubiculosRepository cubiculosRepository;
    @Autowired private UsuariosRepository usuariosRepository;

    public Mono<Paselista> cubiculoExistente(UUID usuario) {
        return cubiculosRepository.findByAsignacion(usuario).flatMap(c -> {
            return paselistaRepository.cubiculoExistente(c.getAsignacion(), c.getId())
            .switchIfEmpty(Mono.just(new Paselista()));
        });
    }

    public Mono<Paselista> cubiculoRegistro(String usuario) {
        log.info("VERIFICANDO REGISTRO DE CUBICULO");
        return usuariosRepository.findByUsername(usuario).flatMap(u -> {
            return cubiculosRepository.findByAsignacion(u.getId()).flatMap(c -> {
                return cubiculoExistente(c.getAsignacion()).flatMap(pase -> {
                    log.info(pase.toString());
                    if(pase.getId() == null){
                        return iniciarCubiculo(u.getId(), c.getId());
                    } else {
                        return finalizarPaselista(pase.getId());
                    }
                });
            });
        });
    }

    public Mono<Paselista> cubiculoPausa(String usuario) {
        log.info("VERIFICANDO PAUSA DEL REGISTRO");
        return usuariosRepository.findByUsername(usuario).flatMap(u -> {
            return cubiculosRepository.findByAsignacion(u.getId()).flatMap(c -> {
                return paselistaRepository.cubiculoExistente(c.getAsignacion(), c.getId())
                .flatMap(pase -> {
                    log.info(pase.toString());
                    if(pase.getPausainicio() == null){
                        return pausar(pase.getId());
                    } else {
                        return reanudar(pase.getId());
                    }
                });
            });
        });
    }

    public Mono<Paselista> eventoExistente(UUID usuario, UUID evento) {
        return paselistaRepository.eventoExistente(usuario, evento)
        .switchIfEmpty(Mono.just(new Paselista()));
    }

    public Mono<Paselista> eventoRegistro(String usuario, UUID evento) {
        log.info("VERIFICANDO REGISTRO DE EVENTO");
        return usuariosRepository.findByUsername(usuario).flatMap(u -> {
            return eventoExistente(u.getId(), evento).flatMap(pase -> {
                log.info(pase.toString());
                if(pase.getId() == null){
                    return iniciarEvento(u.getId(), evento);
                } else {
                    return finalizarPaselista(pase.getId());
                }
            });
        });
    }

    private Mono<Paselista> iniciarCubiculo(UUID usuario, UUID cubiculo){
        log.info("Iniciando registro por cubiculo");
        return paselistaRepository.cubiculoIniciar(usuario, cubiculo);
    }

    private Mono<Paselista> iniciarEvento(UUID usuario, UUID evento){
        log.info("Iniciando registro por evento");
        return paselistaRepository.eventoIniciar(usuario, evento);
    }

    private Mono<Paselista> pausar(UUID paselista){
        log.info("Pausando registro");
        return paselistaRepository.paselistaPausar(paselista);
    }

    private Mono<Paselista> reanudar(UUID paselista){
        log.info("Reanudando registro");
        return paselistaRepository.paselistaReanudar(paselista);
    }

    private Mono<Paselista> finalizarPaselista(UUID paselista){
        log.info("Finalizando registro");
        return paselistaRepository.paselistaTerminar(paselista);
    }
}
