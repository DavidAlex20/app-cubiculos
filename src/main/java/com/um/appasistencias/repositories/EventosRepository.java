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

    @Query("DELETE FROM eventos WHERE id = : id ;")
    Mono<Void> deleteByUuid(@Param("id") UUID uuid);

    @Query("SELECT EXISTS (SELECT 1 FROM eventos WHERE id = :id);")
    Mono<Boolean> exists(@Param("id") UUID uuid);

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
        "SELECT eve.id, eve.titulo, eve.lugar, eve.inicio, eve.fin, eve.fecha, pas.inicio AS pasinicio, pas.fin AS pasfin " + //
        "    COALESCE(     " + //
        "        CASE " + //
        "            WHEN pas.inicio IS NULL AND pas.fin IS NULL THEN 0 " + //
        "            WHEN pas.inicio IS NOT NULL AND pas.fin IS NULL THEN 1 " + //
        "            WHEN pas.inicio IS NOT NULL AND pas.fin IS NOT NULL THEN 2 " + //
        "        END, " + //
        "        0  " + //
        "    ) AS estado " + //
        "FROM usuarios AS usu " + //
        "CROSS JOIN eventos AS eve  " + //
        "LEFT JOIN paselista AS pas  " + //
        "ON eve.id = pas.evento AND usu.id = pas.usuario " + //
        "WHERE usu.id = :usuario AND eve.fecha = :fecha " + //
        "ORDER BY eve.inicio;"
    )
    Flux<EventosEstado> findByUserWithState(@Param("usuario") UUID usuario, @Param("fecha") LocalDate fecha);
}
