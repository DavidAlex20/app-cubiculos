package com.um.appasistencias.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.um.appasistencias.models.Paselista;
import com.um.appasistencias.models.dto.PaselistaListado;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PaselistaRepository 
extends R2dbcRepository<Paselista, UUID> {
    /** CONSULTAS GENERALES */
    @Query("UPDATE paselista SET fin = CURRENT_TIME WHERE id = :paselista ;")
    public Mono<Paselista> paselistaTerminar(@Param("paselista") UUID paselista);

    @Query("UPDATE paselista SET pausainicio = CURRENT_TIME WHERE id = :paselista ;")
    public Mono<Paselista> paselistaPausar(@Param("paselista") UUID paselista);

    @Query("UPDATE paselista SET pausafin = CURRENT_TIME WHERE id = :paselista ;")
    public Mono<Paselista> paselistaReanudar(@Param("paselista") UUID paselista);

    /** CONSULTAS PARA REGISTROS DEL CUBICULO */
    @Query("INSERT INTO paselista (usuario, cubiculo, inicio) VALUES (:usuario, :cubiculo, CURRENT_TIME) ;")
    public Mono<Paselista> cubiculoIniciar(@Param("usuario") UUID usuario, @Param("cubiculo") UUID cubiculo);

    @Query("SELECT * FROM paselista WHERE usuario = :usuario AND cubiculo = :cubiculo AND fecha = CURRENT_DATE ;")
    public Mono<Paselista> cubiculoExistente(@Param("usuario") UUID usuario, @Param("cubiculo") UUID cubiculo);

    /** CONSULTAS PARA REGISTROS DE EVENTOS */
    @Query("INSERT INTO paselista (usuario, evento, inicio) VALUES (:usuario, :evento, CURRENT_TIME) ;")
    public Mono<Paselista> eventoIniciar(@Param("usuario") UUID usuario, @Param("evento") UUID evento);

    @Query("SELECT * FROM paselista WHERE usuario = :usuario AND evento = :evento AND fecha = CURRENT_DATE ;")
    public Mono<Paselista> eventoExistente(@Param("usuario") UUID usuario, @Param("evento") UUID evento);

    /** LISTADO DTO */
    @Query("""
            SELECT
                pas.id, 
                pas.usuario, 
                pas.evento, 
                pas.cubiculo, 
                pas.fecha, 
                pas.inicio, 
                pas.fin, 
                pas.pausainicio, 
                pas.pausafin,
                usu.nombres, 
                usu.apellidos, 
                CASE WHEN pas.evento IS NOT NULL THEN eve.titulo ELSE '' END AS titulo, 
                CASE WHEN pas.evento IS NOT NULL THEN eve.lugar ELSE '' END AS lugar,
                CASE WHEN pas.cubiculo IS NOT NULL THEN cub.numero::text ELSE '' END AS numero,
                CASE WHEN pas.cubiculo IS NOT NULL THEN cub.edificio ELSE '' END AS edificio, 
                COALESCE((pas.pausafin - pas.pausainicio)::time, TIME '00:00:00') AS pausa
            FROM paselista pas
            JOIN usuarios usu ON pas.usuario = usu.id
            LEFT JOIN cubiculos cub ON pas.cubiculo = cub.id
            LEFT JOIN eventos eve ON pas.evento = eve.id
            WHERE pas.inicio IS NOT NULL AND pas.fin IS NOT NULL
            GROUP BY 
                pas.id, pas.usuario, pas.evento, pas.cubiculo, pas.fecha, pas.inicio, pas.fin, pas.pausainicio, pas.pausafin, 
                usu.nombres, usu.apellidos, eve.titulo, eve.lugar, cub.numero, cub.edificio
            ORDER BY pas.fecha
            ;
            """)
    public Flux<PaselistaListado> paselistaListado();

    @Query("""
            UPDATE paselista
            SET usuario = :usuario, evento = :evento, cubiculo = :cubiculo, fecha = :fecha,
                inicio = :inicio, fin = :fin, pausainicio = :pausainicio, pausafin = :pausafin
            WHERE id = :id;
            """)
    public Mono<Paselista> update(
        @Param("usuario") UUID usuario,
        @Param("evento") UUID evento,
        @Param("cubiculo") UUID cubiculo,
        @Param("fecha") LocalDate fecha,
        @Param("inicio") LocalTime inicio,
        @Param("fin") LocalTime fin,
        @Param("pausainicio") LocalTime pausainicio,
        @Param("pausafin") LocalTime pausafin,
        @Param("id") UUID id
    );

    @Query("""
            INSERT INTO paselista (usuario, cubiculo, fecha, inicio, fin, pausainicio, pausafin)
            VALUES (:usuario, :cubiculo, :fecha, :inicio, :fin, :pausainicio, :pausafin);
            """)
    public Mono<Paselista> saveCubiculo(
        @Param("usuario") UUID usuario,
        @Param("cubiculo") UUID cubiculo,
        @Param("fecha") LocalDate fecha,
        @Param("inicio") LocalTime inicio,
        @Param("fin") LocalTime fin,
        @Param("pausainicio") LocalTime pausainicio,
        @Param("pausafin") LocalTime pausafin
    );

    @Query("""
            INSERT INTO paselista (usuario, evento, fecha, inicio, fin, pausainicio, pausafin)
            VALUES (:usuario, :evento, :fecha, :inicio, :fin, :pausainicio, :pausafin);
            """)
    public Mono<Paselista> saveEvento(
        @Param("usuario") UUID usuario,
        @Param("evento") UUID evento,
        @Param("fecha") LocalDate fecha,
        @Param("inicio") LocalTime inicio,
        @Param("fin") LocalTime fin,
        @Param("pausainicio") LocalTime pausainicio,
        @Param("pausafin") LocalTime pausafin
    );

    @Query("SELECT EXISTS (SELECT 1 FROM paselista WHERE id = :id);")
    public Mono<Boolean> paselistaExistente(@Param("id") UUID id);
}
