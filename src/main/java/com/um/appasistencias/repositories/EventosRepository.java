package com.um.appasistencias.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.um.appasistencias.models.Eventos;
import com.um.appasistencias.models.dto.EventosEstado;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EventosRepository extends R2dbcRepository<Eventos, UUID>{
    
    @Query("SELECT * FROM eventos WHERE id = :id ;")
    Mono<Eventos> findByUuid(@Param("id") UUID uuid);

    @Query("SELECT * FROM eventos WHERE fecha = CURRENT_DATE;")
    Flux<Eventos> findAllToday();

    @Query("DELETE FROM eventos WHERE id = :id ;")
    Mono<Void> deleteByUuid(@Param("id") UUID uuid);

    @Query("SELECT EXISTS (SELECT 1 FROM eventos WHERE id = :id);")
    Mono<Boolean> exists(@Param("id") UUID uuid);

    @Query("SELECT EXISTS (SELECT 1 FROM eventos WHERE fecha = :fecha);")
    Mono<Boolean> existsToday(@Param("fecha") LocalDate fecha);

    @Query(
        "UPDATE eventos "+
        "SET titulo = :titulo, lugar = :lugar, inicio = :inicio, fin = :fin, fecha = :fecha "+
        "WHERE id = :id ;"
    )
    Mono<Eventos> update(
        @Param("titulo") String titulo,
        @Param("lugar") String lugar,
        @Param("inicio") LocalTime inicio,
        @Param("fin") LocalTime fin,
        @Param("fecha") LocalDate fecha,
        @Param("id") UUID id
    );

    @Query(
        "SELECT\r\n" + //
        "    eventos.id, eventos.titulo, eventos.lugar, eventos.inicio, eventos.fin, eventos.fecha,\r\n" + //
        "    CASE \r\n" + //
        "        WHEN paselista.id IS NOT NULL THEN TRUE\r\n" + //
        "        ELSE FALSE\r\n" + //
        "    END AS participacion,\r\n" + //
        "    paselista.inicio AS participacioninicio,\r\n" + //
        "    paselista.fin AS participacionfin\r\n" + //
        "FROM eventos\r\n" + //
        "LEFT JOIN paselista ON eventos.id = paselista.evento AND paselista.usuario = :usuario "+
        "WHERE eventos.fecha = CURRENT_DATE ;"
    )
    Flux<EventosEstado> findByUserWithState(@Param("usuario") UUID usuario);
}
