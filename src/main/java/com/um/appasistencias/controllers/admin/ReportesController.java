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

import com.um.appasistencias.models.Usuarios;
import com.um.appasistencias.models.dto.DatosVista;
import com.um.appasistencias.models.dto.ReportesDto;
import com.um.appasistencias.models.dto.ReportesListado;
import com.um.appasistencias.services.ReportesService;
import com.um.appasistencias.services.UsuariosService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin/reportes")
public class ReportesController {
    private static final Logger log = LoggerFactory.getLogger(ReportesController.class);
    @Autowired private ReportesService reportesService;
    @Autowired private UsuariosService usuariosService;

    @GetMapping
    public String listado(@AuthenticationPrincipal Usuarios user, Model model) {
        Flux<ReportesListado> reportes = reportesService.reportesListados();
        DatosVista datosVista = new DatosVista(user, "lista-reportes", "Listado de reportes", true);
        model.addAttribute("reportes", reportes);
        model.addAttribute("datosVista", datosVista);
        return "admin/reportes";
    }

    @GetMapping("/crear")
    public String crear(@AuthenticationPrincipal Usuarios user, Model model) {
        ReportesDto reporte = new ReportesDto();
        Flux<Usuarios> usuarios = usuariosService.findAll();
        DatosVista datosVista = new DatosVista(user, "lista-reportes", "Crear reporte", true);
        model.addAttribute("reporte", reporte);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("datosVista", datosVista);
        return "admin/reportes-form";
    }

    @PostMapping("/guardar")
    public Mono<ResponseEntity<String>> guardar(@ModelAttribute ReportesDto reporte) {
        UUID uuid = reporte.getId();
        log.info(reporte.toString());
        boolean valid = true;
        log.info("Verificando uuid");
        if(uuid != null){
            try {
                reportesService.findById(uuid);
            } catch (Exception e) {
                log.error("Error al verificar UUID");
                valid = false;
            }
        }
        log.info("Verificando campos");
        if(reporte.getSemanainicio() == null && reporte.getSemanafin() != null){
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los campos INICIO y FIN deben estar vacios o llenados a la vez."));
        }
        if(reporte.getSemanainicio() != null && reporte.getSemanafin() == null){
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los campos INICIO y FIN deben estar vacios o llenados a la vez."));
        }
        log.info("Verificando regitros");
        if(valid){
            try {
                return reportesService.reporteExistente(reporte.getId())
                .flatMap(existente -> {
                    if (existente) {
                        return reportesService.updateReporte(reporte.getId()).flatMap(guardado -> {
                            log.info("Registro actualizado :: " + guardado.toString());
                            return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Reporte actualizado"));
                        }).onErrorResume(error -> {
                            log.error(error.getMessage());
                            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No fue posible actualizar"));
                        });
                    } else {
                        return reportesService.saveReporte(reporte.getUsuario(), reporte.getSemanainicio(), reporte.getSemanafin())
                        .flatMap(guardado -> {
                            log.info("Registro guardado :: " + guardado.toString());
                            return Mono.just(ResponseEntity.status(HttpStatus.OK).body("Reporte guardado"));
                        }).onErrorResume(error -> {
                            log.error(error.getMessage());
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
            return reportesService.findById(id)
            .flatMap(repo -> reportesService.deleteById(id)
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body("Registro eliminado"))
            ).defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro no existente"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado!"));
        }
    }

    @GetMapping("/actualizar")
    public Mono<ResponseEntity<String>> actualizar(@RequestParam UUID id) {
        try {
            log.info("Actualizando registro");
            return reportesService.findById(id)
            .flatMap(repo -> reportesService.updateReporte(id)
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body("Registro actualizado"))
            ).defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro no existente"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado!"));
        }
    }
    
}
