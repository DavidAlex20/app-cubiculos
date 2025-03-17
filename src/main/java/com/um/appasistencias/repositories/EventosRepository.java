package com.um.appasistencias.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.um.appasistencias.models.Eventos;

import reactor.core.publisher.Mono;

@Repository
public interface EventosRepository extends R2dbcRepository<Eventos, String>{
    
    @Query("SELECT * FROM eventos WHERE id = :id ;")
    Mono<Eventos> findByUuid(@Param("id") UUID uuid);

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
}
