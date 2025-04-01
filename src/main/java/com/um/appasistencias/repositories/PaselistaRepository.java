package com.um.appasistencias.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.um.appasistencias.models.Paselista;

import reactor.core.publisher.Mono;

@Repository
public interface PaselistaRepository extends R2dbcRepository<Paselista, UUID>{
    @Query(
        "SELECT * FROM paselista WHERE usuario= :usuario AND fecha= :fecha ;"
    )
    Mono<Paselista> findByUsuarioFecha(
        @Param("usuario") UUID usuario,
        @Param("fecha") LocalDate fecha
    );

    @Query("SELECT EXISTS (SELECT 1 FROM paselista WHERE usuario = :usuario AND cubiculo = :cubiculo AND fecha = :date );")
    Mono<Boolean> findByCubiculo(@Param("usuario") UUID usuario, @Param("cubiculo") UUID id, @Param("date") LocalDate date);

    @Query("SELECT EXISTS (SELECT 1 FROM paselista WHERE usuario = :usuario AND evento = :evento AND fecha = :date );")
    Mono<Boolean> findByEvento(@Param("usuario") UUID usuario, @Param("evento") UUID evento, @Param("date") LocalDate date);

    @Query("SELECT * FROM paselista WHERE usuario = :usuario AND cubiculo = :cubiculo AND fecha = :date ;")
    Mono<Paselista> findByCubiculoData(@Param("usuario") UUID usuario, @Param("cubiculo") UUID id, @Param("date") LocalDate date);

    @Query("SELECT * FROM paselista WHERE usuario = :usuario AND evento = :evento AND fecha = :date ;")
    Mono<Paselista> findByEventoData(@Param("usuario") UUID usuario, @Param("evento") UUID evento, @Param("date") LocalDate date);

    @Query(
        "INSERT INTO paselista (usuario, cubiculo, fecha, inicio) \n"+
        "VALUES(:usuario, :cubiculo, :fecha, :inicio);"
    )
    Mono<Paselista> createCubiculo(
        @Param("usuario") UUID usuario,
        @Param("cubiculo") UUID cubiculo,
        @Param("fecha") LocalDate fecha,
        @Param("inicio") LocalTime inicio
    );

    @Query(
        "INSERT INTO paselista (usuario, cubiculo, fecha, fin) \n"+
        "VALUES(:usuario, :cubiculo, :fecha, :fin);"
    )
    Mono<Paselista> updateCubiculo(
        @Param("usuario") UUID usuario,
        @Param("cubiculo") UUID cubiculo,
        @Param("fecha") LocalDate fecha,
        @Param("fin") LocalTime fin
    );

    @Query(
        "INSERT INTO paselista (usuario, evento, fecha, inicio) \n"+
        "VALUES(:usuario, :evento, :fecha, :inicio);"
    )
    Mono<Paselista> createEvento(
        @Param("usuario") UUID usuario,
        @Param("evento") UUID evento,
        @Param("fecha") LocalDate fecha,
        @Param("inicio") LocalTime inicio
    );

    @Query(
        "INSERT INTO paselista (usuario, evento, fecha, fin) \n"+
        "VALUES(:usuario, :evento, :fecha, :fin);"
    )
    Mono<Paselista> updateEvento(
        @Param("usuario") UUID usuario,
        @Param("evento") UUID evento,
        @Param("fecha") LocalDate fecha,
        @Param("fin") LocalTime fin
    );
}
