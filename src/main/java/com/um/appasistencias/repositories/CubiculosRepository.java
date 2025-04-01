package com.um.appasistencias.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.um.appasistencias.models.Cubiculos;
import com.um.appasistencias.models.dto.CubiculosDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CubiculosRepository extends R2dbcRepository<Cubiculos, UUID>{
    @Query("SELECT * FROM cubiculos WHERE id = :id ;")
    Mono<Cubiculos> findByUuid(@Param("id") UUID uuid);

    @Query("DELETE FROM cubiculos WHERE id = :id ;")
    Mono<Void> deleteByUuid(@Param("id") UUID uuid);

    @Query("SELECT EXISTS (SELECT 1 FROM cubiculos WHERE id = :id);")
    Mono<Boolean> exists(@Param("id") UUID id);

    @Query(
        "UPDATE cubiculos "+
        "SET numero= :numero, edificio= :edificio "+
        "WHERE id= :id ;"
    )
    Mono<Cubiculos> update(
        @Param("numero") int numero,
        @Param("edificio") String edificio,
        @Param("id") UUID id
    );

    @Query(
        "UPDATE cubiculos "+
        "SET disponible= :disponible "+
        "WHERE id= :id ;"
    )
    Mono<Cubiculos> updateDisponible(@Param("disponible") boolean disponible, @Param("id") UUID id);

    @Query(
        "UPDATE cubiculos "+
        "SET asignacion= :asignacion "+
        "WHERE id= :id ;"
    )
    Mono<Cubiculos> updateAsignacion(@Param("asignacion") UUID asignacion, @Param("id") UUID id);

    @Query(
        "SELECT cub.id, cub.numero, cub.edificio, cub.disponible, cub.asignacion, usu.nombres, usu.apellidos \r\n" + //
        "FROM cubiculos AS cub\r\n" + //
        "INNER JOIN usuarios AS usu ON usu.id = cub.asignacion;"
    )
    Flux<CubiculosDto> findAllWithAsignacion();

    @Query("SELECT * FROM cubiculos WHERE asignacion = :asignacion ;")
    Mono<Cubiculos> findByAsignacion(@Param("asignacion") UUID asignacion);
}
