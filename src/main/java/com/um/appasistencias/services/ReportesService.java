package com.um.appasistencias.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.um.appasistencias.models.Reportes;
import com.um.appasistencias.repositories.ReportesRepository;

import reactor.core.publisher.Mono;

@Service
public class ReportesService {
    private static final Logger log = LoggerFactory.getLogger(ReportesService.class);
    @Autowired private ReportesRepository reportesRepository;

    public Mono<Reportes> updateByUsuario(UUID id){
        log.info("Reporte de usuario :: " + id);
        return reportesRepository.reportePorUsuario(id);
    }
}
