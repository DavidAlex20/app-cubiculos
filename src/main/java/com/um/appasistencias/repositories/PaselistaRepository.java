package com.um.appasistencias.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.um.appasistencias.models.Paselista;

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
}
