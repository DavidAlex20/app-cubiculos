package com.um.appasistencias.services;

import java.time.LocalDate;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.um.appasistencias.models.Reportes;
import com.um.appasistencias.models.dto.ReportesListado;
import com.um.appasistencias.repositories.ReportesRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReportesService {
    private static final Logger log = LoggerFactory.getLogger(ReportesService.class);
    @Autowired private ReportesRepository reportesRepository;

    public Flux<ReportesListado> reportesListados(){
        return reportesRepository.reportesListados();
    }

    public Mono<Void> deleteById(UUID id){
        return reportesRepository.deleteById(id);
    }

    public Mono<Reportes> findById(UUID id){
        return reportesRepository.findById(id);
    }

    public Mono<Boolean> reporteExistente(UUID id){
        return reportesRepository.reporteExistente(id);
    }

    public Mono<Reportes> saveReporte(UUID usuario, LocalDate inicio, LocalDate fin){
        if (inicio == null && fin == null) {
            log.info("Guardando reporte");
            return stringToDuration(usuario, inicio, fin).flatMap(duracion -> {
                log.info("Data [usuario="+usuario+",inicio="+inicio+",fin="+fin+",duracion="+duracion+"]");
                return reportesRepository.guardar(usuario, duracion);
            });
        } else {
            log.info("Guardando reporte por fecha");
            return stringToDuration(usuario, inicio, fin).flatMap(duracion -> {
                log.info("Data [usuario="+usuario+",inicio="+inicio+",fin="+fin+",duracion="+duracion+"]");
                return reportesRepository.guardarPorFecha(usuario, inicio, fin, duracion);
            });
        }
        
    }

    public Mono<Reportes> updateReporte(UUID id){
        log.info("Actualizando reporte");
        return reportesRepository.findById(id).flatMap(repo -> {
            return stringToDuration(repo.getUsuario(), repo.getSemanainicio(), repo.getSemanafin())
            .flatMap(duracion -> {
                return reportesRepository.actualizar(repo.getId(), duracion);
            });
        });
    }

    private Mono<String> stringToDuration(UUID usuario, LocalDate inicio, LocalDate fin){
        if (inicio == null && fin == null){
            return reportesRepository.calcularPuntuales(usuario)
            // .flatMap(duracion -> {
            //     return Mono.just(IntervalParser.parsePostgresInterval(duracion));
            // })
            ;
        }else{
            return reportesRepository.calcularPuntualesPorFecha(usuario, inicio, fin)
            // .flatMap(duracion -> {
            //     return Mono.just(IntervalParser.parsePostgresInterval(duracion));
            // })
            ;
        }
    }
}
