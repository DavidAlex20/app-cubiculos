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
import org.springframework.web.bind.annotation.RequestParam;

import com.um.appasistencias.models.Usuarios;
import com.um.appasistencias.models.dto.DatosVista;
import com.um.appasistencias.services.UsuariosService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/admin/usuarios")
public class UsuariosController {
    private static final Logger log = LoggerFactory.getLogger(UsuariosController.class);
    @Autowired private UsuariosService usuariosService;

    @GetMapping
    public String listado(@AuthenticationPrincipal Usuarios user, Model model) {
        Flux<Usuarios> usuarios = usuariosService.findAll();
        model.addAttribute("usuarios", usuarios);
        DatosVista datosVista = new DatosVista(user, "lista-usuarios", "Listado de usuarios", true);
        //log.info(datosVista.toString());
        model.addAttribute("datosVista", datosVista);
        return "admin/usuarios";
    }

    @GetMapping("/crear")
    public String crear(@AuthenticationPrincipal Usuarios user, Model model) {
        Usuarios usuario = new Usuarios("", "", "USER", "", "", "", "", true, "");
        usuario.setId("0");
        model.addAttribute("usuario", usuario);
        DatosVista datosVista = new DatosVista(user, "lista-usuarios", "Registrar nuevo usuario", true);
        //log.info(datosVista.toString());
        model.addAttribute("datosVista", datosVista);
        return "admin/usuarios-form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@AuthenticationPrincipal Usuarios user, @PathVariable String id, Model model) {
        Mono<Usuarios> usuario = usuariosService.findById(id);
        model.addAttribute("usuario", usuario);
        DatosVista datosVista = new DatosVista(user, "lista-usuarios", "Editar usuario", true);
        //log.info(datosVista.toString());
        model.addAttribute("datosVista", datosVista);
        return "admin/usuarios-form";
    }

    // API RESPONSE
    @PostMapping("/guardar")
    public Mono<ResponseEntity<String>> guardar(@ModelAttribute Usuarios usuario) {
        String uuid = usuario.getId();
        log.info(usuario.toString());
        boolean valid = true; 
        if(!uuid.equals("0")){
            log.info("Verificando uuid");
            try {
                UUID.fromString(usuario.getId());
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage());
                valid = false;
            }
        }
        if(valid){
            try {
                log.info("Verificando existencia");
                return usuariosService.exists(usuario.getId())
                .flatMap(exists -> {
                    if(exists) {
                        log.info("Realizando actualizacion");
                        return usuariosService.update(usuario.getId(), usuario)
                            .flatMap(user -> {
                                return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Usuario actualizado: " + user.getUsername()));
                            })
                            .onErrorResume(error -> {
                                log.error(error.getMessage());
                                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo actualizar el usuario."));
                            });
                    } else {
                        log.info("Realizando guardado");
                        return usuariosService.register(usuario.getUsername(), usuario.getPassword(), usuario.getRole(), usuario.getNombres(), usuario.getApellidos(), usuario.getNumempleado(), usuario.getStatus(), usuario.getEmail())
                            .flatMap(user -> {
                                return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Usuario guardado: " + user.getUsername()));
                            })
                            .onErrorResume(error -> {
                                log.error(error.getMessage());
                                return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo guardar el usuario."));
                            });
                    }
                });
            } catch (Exception e) {
                log.error(e.getMessage());
                return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("¡Error inesperado!"));
            }
           
        }
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID no válido."));
    }
    

    @GetMapping("/eliminar")
    public Mono<ResponseEntity<String>> eliminar(@RequestParam String id) {
        try {
            log.info("Realizando eliminacion");
            return usuariosService.findById(id)
                .flatMap(user -> usuariosService.deleteById(id)
                    .thenReturn(ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado exitosamente.")))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no existente."));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("¡Error inesperado!"));
        }
    }
    
}
