package com.um.appasistencias.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.um.appasistencias.models.Usuarios;

import reactor.core.publisher.Mono;

@Repository
public interface UsuariosRepository extends R2dbcRepository<Usuarios, String> {
    Mono<Usuarios> findByUsername(String username);

    @Query("SELECT * FROM usuarios WHERE id = :id ;")
    Mono<Usuarios> findByUuid(@Param("id") UUID uuid);

    @Query("DELETE FROM usuarios WHERE id = :id ;")
    Mono<Void> deleteByUuid(@Param("id") UUID uuid);

    @Query("SELECT EXISTS (SELECT 1 FROM usuarios WHERE id = :id);")
    Mono<Boolean> exists(@Param("id") UUID uuid);

    @Query(
        "UPDATE usuarios " + //
        "SET username = :username, password = :password, role = :role, nombres = :nombres, "+
        "apellidos = :apellidos, numempleado = :numempleado, status = :status, activo = :activo, email = :email "+
        "WHERE id = :id ;"
    )
    Mono<Usuarios> update(
        @Param("username") String username, 
        @Param("password") String password, 
        @Param("role") String role, 
        @Param("nombres") String nombres, 
        @Param("apellidos") String apellidos, 
        @Param("numempleado") String numempleado,
        @Param("status") String status,
        @Param("activo") boolean activo,
        @Param("email") String email,
        @Param("id") UUID id
    );
}
