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

import com.um.appasistencias.models.Cubiculos;
import com.um.appasistencias.models.Usuarios;
import com.um.appasistencias.models.dto.DatosVista;
import com.um.appasistencias.services.CubiculosService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin/cubiculos")
public class CubiculosController {
    private static final Logger log = LoggerFactory.getLogger(CubiculosController.class);
    @Autowired private CubiculosService cubiculosService;

    @GetMapping
    public String listado(@AuthenticationPrincipal Usuarios user, Model model) {
        Flux<Cubiculos> cubiculos = cubiculosService.findAll();
        model.addAttribute("cubiculos", cubiculos);
        DatosVista datosVista = new DatosVista(user, "lista-cubiculos", "Listado de cubiculos", true);
        model.addAttribute("datosVista", datosVista);
        return "admin/cubiculos";
    }

    @GetMapping("/crear")
    public String crear(@AuthenticationPrincipal Usuarios user, Model model) {
        DatosVista datosVista = new DatosVista(user, "lista-cubiculos", "Registrar nuevo cubiculo", true);
        model.addAttribute("datosVista", datosVista);
        Cubiculos cubiculo = new Cubiculos(0, "", true);
        cubiculo.setId("0");
        model.addAttribute("cubiculo", cubiculo);
        return "admin/cubiculos-form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@AuthenticationPrincipal Usuarios user,@PathVariable String id, Model model) {
        DatosVista datosVista = new DatosVista(user, "lista-cubiculos", "Editar cubiculo", true);
        model.addAttribute("datosVista", datosVista);
        Mono<Cubiculos> cubiculo = cubiculosService.findById(id);
        model.addAttribute("cubiculo", cubiculo);
        return "admin/cubiculos-form";
    }

    // API RESPONSE
    @PostMapping("/guardar")
    public Mono<ResponseEntity<String>> guardar(@ModelAttribute Cubiculos cubiculo){
        String uuid = cubiculo.getId();
        log.info(cubiculo.toString());
        boolean valid = true;
        if(!uuid.equals("0")){
            log.info("Verificando uuid");
            try {
                UUID.fromString(cubiculo.getId());
            } catch (Exception e) {
                log.error(e.getMessage());
                valid = false;
            }
        }
        if(valid){
            try {
                log.info("Verificando existencia");
                return cubiculosService.exists(cubiculo.getId())
                .flatMap(exists -> {
                    if(exists){
                        log.info("Realizando actualizacion");
                        return cubiculosService.update(cubiculo.getId(), cubiculo)
                        .flatMap(cubi -> {
                            return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Cubiculo actualizado"));
                        }).onErrorResume(error -> {
                            log.error(error.getMessage());
                            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar el registro."));
                        });
                    }else{
                        log.info("Realizando guardado");
                        return cubiculosService.save(cubiculo.getId(), cubiculo.getNumero(), cubiculo.getEdificio(), cubiculo.isDisponible())
                        .flatMap(cubi -> {
                            return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Cubiculo guardado"));
                        }).onErrorResume(error -> {
                            log.error(error.getMessage());
                            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo guardar el registro."));
                        });
                    }
                });
            } catch (Exception e) {
                log.error(e.getMessage());
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hubo un error inesperado."));
            }
        }
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID no valido."));
    }

    @GetMapping("/eliminar")
    public Mono<ResponseEntity<String>> eliminar(@RequestParam String id) {
        try {
            log.info("Realizando eliminacion");
            return cubiculosService.findById(id)
            .flatMap(cubi -> cubiculosService.deleteById(id)
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body("Cubiculo eliminado exitosamente.")))
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cubiculo no existente."));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("¡Error inesperado!"));
        }
    }
    
}
