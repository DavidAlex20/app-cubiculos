package com.um.appasistencias.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.um.appasistencias.models.Cubiculos;

import reactor.core.publisher.Mono;

@Repository
public interface CubiculosRepository extends R2dbcRepository<Cubiculos, String>{
    @Query("SELECT * FROM cubiculos WHERE id = :id ;")
    Mono<Cubiculos> findByUuid(@Param("id") UUID uuid);

    @Query("DELETE FROM cubiculos WHERE id = :id ;")
    Mono<Void> deleteByUuid(@Param("id") UUID uuid);

    @Query("SELECT EXISTS (SELECT 1 FROM cubiculos WHERE id = :id);")
    Mono<Boolean> exists(@Param("id") UUID id);

    @Query(
        "UPDATE cubiculos "+
        "SET numero= :numero, edificio= :edificio, disponible= :disponible "+
        "WHERE id= :id ;"
    )
    Mono<Cubiculos> update(
        @Param("numero") int numero,
        @Param("edificio") String edificio,
        @Param("disponible") boolean disponible,
        @Param("id") UUID id
    );
}
