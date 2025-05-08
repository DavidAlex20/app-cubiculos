package com.um.appasistencias;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.um.appasistencias.services.ReportesService;
import com.um.appasistencias.services.UsuariosService;

@Component
public class ScheduleComponent {
    private static final Logger log = LoggerFactory.getLogger(ScheduleComponent.class);
    private static final LocalTime TARGET_TIME = LocalTime.of(18, 46, 0);
    @Autowired private UsuariosService usuariosService;
    @Autowired private ReportesService reportesService;

    @Scheduled(cron = "0 * * * * *")
    public void consoleMessage(){
        //log.info("Hora del dia => " + java.time.LocalDateTime.now());

        LocalTime time = LocalTime.now().withNano(0);
        if(time.equals(TARGET_TIME)) {
            log.info("Dia finalizado - Realizando reporte del dia");
            usuariosService.findAll()
            .doOnNext(usuario -> {
                log.info("CREANDO REPORTE :: " + usuario.getId());
                try {
                    reportesService.updateByUsuario(usuario.getId());
                } catch (Exception e) {
                    log.error("ERROR :: ", e);
                }
            }).subscribe();
        }
    }

}