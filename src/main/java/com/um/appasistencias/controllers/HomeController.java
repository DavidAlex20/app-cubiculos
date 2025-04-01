package com.um.appasistencias.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.um.appasistencias.models.Usuarios;
import com.um.appasistencias.models.dto.DatosVista;
import com.um.appasistencias.models.dto.EventosEstado;
import com.um.appasistencias.repositories.UsuariosRepository;
import com.um.appasistencias.services.EventosService;
import com.um.appasistencias.services.PaselistaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/dashboard")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired PaselistaService paselistaService;
    @Autowired EventosService eventosService;
    @Autowired UsuariosRepository usuariosRepository;

    @GetMapping
    public Mono<String> index(@AuthenticationPrincipal Usuarios user, Model model){
        return usuariosRepository.findByUsername(user.getUsername())
        .flatMap(u -> {
            DatosVista datosVista = new DatosVista(user, "index", "Inicio", false);
            log.info(datosVista.toString());
            model.addAttribute("datosVista", datosVista);
            Flux<EventosEstado> eventos = eventosService.findByUserWithState(u.getId());
            model.addAttribute("eventos", eventos);
            return Mono.just("index");
        });
        
    }

    // API RESPONSE
    @GetMapping("/paselista")
    public Mono<ResponseEntity<String>> paselista(@RequestParam String usuario, @RequestParam UUID id) {
        try {
            return paselistaService.findByType(usuario, id)
            .flatMap(exists -> {
                if(exists) {
                    return paselistaService.findByTypeData(usuario, id)
                    .flatMap(data -> {
                        if(data.getInicio() != null && data.getFin() == null) {
                            paselistaService.updateByType(usuario, id)
                            .flatMap(pass -> {
                                log.info(pass.toString());
                                return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Pase de lista finalizado."));
                            })
                            .onErrorResume(error -> {
                                log.error(error.getMessage());
                                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo finalizar el pase de lista."));
                            });
                        } else if (data.getInicio() != null && data.getFin() != null) {
                            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pase de lista ya finalizado."));
                        }
                        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en los datos!"));
                        
                    });
                } else {
                    return paselistaService.createByType(usuario, id)
                    .flatMap(pass -> {
                        log.info(pass.toString());
                        return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Pase de lista iniciado."));
                    })
                    .onErrorResume(error -> {
                        log.error(error.getMessage());
                        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo iniciar el pase de lista."));
                    });
                }
            });
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado!"));
        }
    }
    
}
