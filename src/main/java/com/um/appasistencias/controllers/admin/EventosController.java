package com.um.appasistencias.controllers.admin;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.um.appasistencias.models.Eventos;
import com.um.appasistencias.models.Usuarios;
import com.um.appasistencias.models.dto.DatosVista;
import com.um.appasistencias.models.dto.EventosDto;
import com.um.appasistencias.services.EventosService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin/eventos")
public class EventosController {
    private static final Logger log = LoggerFactory.getLogger(EventosController.class);
    @Autowired private EventosService eventosService;
    @GetMapping
    public String listado(@AuthenticationPrincipal Usuarios user, Model model){
        Flux<Eventos> eventos = eventosService.findAll();
        DatosVista datosVista = new DatosVista(user, "lista-eventos", "Listado de eventos", true);
        model.addAttribute("eventos", eventos);
        model.addAttribute("datosVista", datosVista);
        return "admin/eventos";
    }

    @GetMapping("/crear")
    public String crear(@AuthenticationPrincipal Usuarios user, Model model){
        Eventos evento = new Eventos("", "", null, null, null);
        evento.setId(null);
        DatosVista datosVista = new DatosVista(user, "lista-eventos", "Listado de eventos", true);
        model.addAttribute("evento", evento);
        model.addAttribute("datosVista", datosVista);
        return "admin/eventos-form";
    }

    @GetMapping("/editar/{id}")
    public Mono<String> editar(@AuthenticationPrincipal Usuarios user, @PathVariable UUID id, Model model){
        //Mono<Eventos> evento = 
        return eventosService.findById(id).map(EventosDto::new)
        .doOnNext(evento -> {
            DatosVista datosVista = new DatosVista(user, "lista-eventos", "Listado de eventos", true);
            model.addAttribute("evento", evento);
            model.addAttribute("datosVista", datosVista);
        }).thenReturn("admin/eventos-form");
    }

    // API RESPONSE
    @PostMapping("/guardar")
    public Mono<ResponseEntity<String>> guardar(@ModelAttribute Eventos evento){
        UUID uuid = evento.getId();
        log.info(evento.toString());
        boolean valid = true;
        if(uuid != null){
            log.info("Verificando uuid");
            try {
                eventosService.findById(uuid);
            } catch (Exception e) {
                log.error(e.getMessage());
                valid = false;
            }
        }
        if(valid){
            try {
                log.info("Verificando existencia");
                return eventosService.exists(evento.getId())
                .flatMap(exists -> {
                    if(exists) {
                        log.info("Realizando actualizacion");
                        return eventosService.update(evento.getId(), evento)
                            .flatMap(ev -> {
                                return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Evento actualizado."));
                            })
                            .onErrorResume(error -> {
                                log.error(error.getMessage());
                                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar el evento."));
                            });
                    } else {
                        log.info("Realizando guardado");
                        return eventosService.save(evento.getTitulo(), evento.getLugar(), evento.getInicio(), evento.getFin(), evento.getFecha())
                        .flatMap(ev -> {
                            return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Evento guardado."));
                        })
                        .onErrorResume(error -> {
                            log.error(error.getMessage());
                            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo guardar el evento."));
                         });
                    }
                });
            } catch (Exception e) {
                log.error(e.getMessage());
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("¡Error inesperado!"));
            }
        }
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID no valido."));
    }

    @GetMapping("/eliminar")
    public Mono<ResponseEntity<String>> eliminar(@RequestParam UUID id) {
        try {
            log.info("Realizando eliminacion");
            return eventosService.findById(id)
                .flatMap(ev -> eventosService.deleteById(id)
                    .thenReturn(ResponseEntity.status(HttpStatus.OK).body("Evento eliminado exitosamente")))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento no existente"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("¡Error inesperado!"));
        }
    }
    
}
