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
import org.springframework.web.bind.annotation.RequestMapping;

import com.um.appasistencias.models.Eventos;
import com.um.appasistencias.models.Paselista;
import com.um.appasistencias.models.Usuarios;
import com.um.appasistencias.models.dto.CubiculosAsignacion;
import com.um.appasistencias.models.dto.DatosVista;
import com.um.appasistencias.models.dto.PaselistaDto;
import com.um.appasistencias.models.dto.PaselistaListado;
import com.um.appasistencias.services.CubiculosService;
import com.um.appasistencias.services.EventosService;
import com.um.appasistencias.services.PaselistaService;
import com.um.appasistencias.services.UsuariosService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/admin/paselista")
public class PaselistaController {
    private static final Logger log = LoggerFactory.getLogger(PaselistaController.class);
    @Autowired private PaselistaService paselistaService;
    @Autowired private CubiculosService cubiculosService;
    @Autowired private EventosService eventosService;
    @Autowired private UsuariosService usuariosService;

    @GetMapping
    public String listado(@AuthenticationPrincipal Usuarios user, Model model) {
        Flux<PaselistaListado> paselista = paselistaService.paselistaListado();
        DatosVista datosVista = new DatosVista(user, "lista-paselista", "Listado de pases de lista", true);
        model.addAttribute("paselista", paselista);
        model.addAttribute("datosVista", datosVista);
        return "admin/paselista";
    }

    @GetMapping("/crear")
    public String crear(@AuthenticationPrincipal Usuarios user, Model model) {
        Paselista paselista = new Paselista();
        DatosVista datosVista = new DatosVista(user, "lista-paselista", "Listado de pases de lista", true);
        Flux<Usuarios> usuarios = usuariosService.findAll();
        Flux<Eventos> eventos = eventosService.findAll();
        Flux<CubiculosAsignacion> cubiculos = cubiculosService.findAllWithAsignacion();
        model.addAttribute("paselista", paselista);
        model.addAttribute("datosVista", datosVista);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("eventos", eventos);
        model.addAttribute("cubiculos", cubiculos);
        return "admin/paselista-form";
    }

    @GetMapping("/editar/{id}")
    public Mono<String> getMethodName(@AuthenticationPrincipal Usuarios user, @PathVariable UUID id, Model model) {
        return paselistaService.findById(id).map(PaselistaDto::new)
        .doOnNext(paselista -> {
            log.info(paselista.toString());
            DatosVista datosVista = new DatosVista(user, "lista-paselista", "Listado de pases de lista", true);
            Flux<Usuarios> usuarios = usuariosService.findAll();
            Flux<Eventos> eventos = eventosService.findAll();
            Flux<CubiculosAsignacion> cubiculos = cubiculosService.findAllWithAsignacion();
            model.addAttribute("paselista", paselista);
            model.addAttribute("datosVista", datosVista);
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("eventos", eventos);
            model.addAttribute("cubiculos", cubiculos);
        }).thenReturn("admin/paselista-form");
    }
    

    @PostMapping("/guardar")
    public Mono<ResponseEntity<String>> guardar(@ModelAttribute Paselista pase) {
        UUID uuid = pase.getId();
        log.info(pase.toString());
        boolean valid = true;
        log.info("Verificando uuid");
        if(uuid != null){
            try {
                paselistaService.findById(uuid);
            } catch (Exception e) {
                log.error("Error al verificar UUID");
                valid = false;
            }
        }
        log.info("Verificando campos");
        if(pase.getCubiculo() == null && pase.getEvento() == null){
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los campos EVENTO y CUBICULO no pueden estar vacios a la vez."));
        }
        if(pase.getCubiculo() != null && pase.getEvento() != null){
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los campos EVENTO y CUBICULO no pueden contener datos a la vez."));
        }
        log.info("Verificando registros");
        if(valid){
            try {
                return paselistaService.paselistaExistente(pase.getId())
                .flatMap(existente -> {
                    if(existente){
                        return paselistaService.update(pase.getId(), pase).flatMap(guardado -> {
                            log.info("Registro actualizado :: " + guardado.toString());
                            return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Pase de lista actualizado"));
                        }).onErrorResume(error -> {
                            log.error("Error al actualizar", error.getMessage());
                            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No fue posible actualizar"));
                        });
                    } else {
                        return paselistaService.save(pase.getUsuario(), pase.getEvento(), pase.getCubiculo(), pase.getFecha(), pase.getInicio(), pase.getFin(), pase.getPausainicio(), pase.getPausafin())
                        .flatMap(guardado -> {
                            log.info("Registro nuevo :: " + guardado.toString());
                            return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Pase de lista guardado"));
                        }).onErrorResume(error -> {
                            log.error("", error.getMessage());
                            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No fue posible guardar"));
                        });
                    }
                });
            } catch (Exception e) {
                log.error(e.getMessage());
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("¡Error inesperado!"));
            }
        }
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID no valido"));
    }

    @GetMapping("/eliminar")
    public Mono<ResponseEntity<String>> eliminar(@RequestParam UUID id) {
        try {
            log.info("Eliminando registro");
            return paselistaService.findById(id)
            .flatMap(pase -> paselistaService.deleteById(id)
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body("Registro eliminado"))
            ).defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro no existente"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("¡Error inesperado!"));
        }
    }
    
    
}
