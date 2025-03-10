package com.um.appasistencias.controllers.admin;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.um.appasistencias.models.Cubiculos;
import com.um.appasistencias.services.CubiculosService;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/admin/cubiculos")
public class CubiculosController {
    private static final Logger log = LoggerFactory.getLogger(CubiculosController.class);
    @Autowired private CubiculosService cubiculosService;

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
                            return Mono.just(ResponseEntity.status(HttpStatus.OK)).body("Cubiculo actualizado: " + cubi.getNumero());
                        }).onErrorResume(error -> {
                            log.error(error.getMessage());
                            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar el registro."));
                        });
                    }else{
                        log.info("Realizando guardado");
                        return cubiculosService.save(cubiculo.getId(), cubiculo.getNumero(), cubiculo.getEdificio(), cubiculo.isDisponible())
                        .flatMap(cubi -> {
                            return Mono.just(ResponseEntity.status(HttpStatus.OK)).body("Cubiculo guardado: " + cubi.getNumero());
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
}
