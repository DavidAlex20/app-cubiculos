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

import com.um.appasistencias.models.Cubiculos;
import com.um.appasistencias.models.Paselista;
import com.um.appasistencias.models.Usuarios;
import com.um.appasistencias.models.dto.DatosVista;
import com.um.appasistencias.models.dto.EventosEstado;
import com.um.appasistencias.repositories.UsuariosRepository;
import com.um.appasistencias.services.CubiculosService;
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
    @Autowired CubiculosService cubiculosService;

    @GetMapping
    public Mono<String> index(@AuthenticationPrincipal Usuarios user, Model model){
        return usuariosRepository.findByUsername(user.getUsername())
        .flatMap(u -> {
            DatosVista datosVista = new DatosVista(user, "index", "Inicio", false);
            log.info(datosVista.toString());
            model.addAttribute("datosVista", datosVista);

            Flux<EventosEstado> eventos = eventosService.findByUserWithState(u.getId());
            model.addAttribute("eventos", eventos);
            
            Mono<Boolean> eventosToday = eventosService.existsToday();
            model.addAttribute("eventosToday", eventosToday);

            Mono<Cubiculos> cubiculo = cubiculosService.findByAsignacion(u.getId());
            model.addAttribute("cubiculo", cubiculo);

            Mono<Paselista> paselista = paselistaService.cubiculoExistente(u.getId());
            model.addAttribute("paselista", paselista);
            return Mono.just("index");
        });
    }

    // API RESPONSE
    @GetMapping("/evento")
    public Mono<ResponseEntity<String>> evento(@RequestParam String usuario, @RequestParam UUID evento) {
        try {
            log.info("ENVIANDO DATOS DE EVENTO");
            return paselistaService.eventoRegistro(usuario, evento).flatMap(pase -> {
                log.info(pase.toString());
                return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Evento: Pase de lista marcado!"));
            })
            .onErrorResume(error -> {
                log.error(error.getMessage());
                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/plain")  
                    .body("Error al marcar el pase de lista!"));
            });
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "text/plain")
                .body("Hubo un error inesperado."));
        }
    }

    @GetMapping("/cubiculo")
    public Mono<ResponseEntity<String>> cubiculo(@RequestParam String usuario) {
        try {
            log.info("ENVIANDO DATOS DE CUBICULO");
            return paselistaService.cubiculoRegistro(usuario).flatMap(pase -> {
                log.info(pase.toString());
                return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Cubiculo: Pase de lista marcado!"));
            })
            .onErrorResume(error -> {
                log.error(error.getMessage());
                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/plain")  
                    .body("Error al marcar el pase de lista!"));
            });
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "text/plain")
                .body("Hubo un error inesperado."));
        }
    }

    @GetMapping("/pausa")
    public Mono<ResponseEntity<String>> pausa(@RequestParam String usuario) {
        try {
            log.info("ENVIANDO DATOS PARA PAUSAR");
            return paselistaService.cubiculoPausa(usuario).flatMap(pase -> {
                log.info(pase.toString());
                return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Cubiculo: Pausa actualizada!"));
            })
            .onErrorResume(error -> {
                log.error(error.getMessage());
                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/plain")  
                    .body("Error al pausar!"));
            });
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type", "text/plain")
                .body("Hubo un error inesperado."));
        }
    }
    

}
