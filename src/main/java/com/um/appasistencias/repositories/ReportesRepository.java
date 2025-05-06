package com.um.appasistencias.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.um.appasistencias.models.Reportes;

import reactor.core.publisher.Mono;

@Repository
public interface ReportesRepository extends R2dbcRepository<Reportes, UUID>{
    @Query(
        "DO $$ BEGIN  \r\n" + //
        "    IF EXISTS (  \r\n" + //
        "        SELECT 1 FROM reportes  \r\n" + //
        "        WHERE usuario = :usuario  \r\n" + //
        "            AND semanainicio = (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) - interval '1 day') \r\n" + //
        "            AND semanafin = (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) + interval '5 days') \r\n" + //
        "    ) \r\n" + //
        "    THEN \r\n" + //
        "        UPDATE reportes \r\n" + //
        "        SET puntuales = ( \r\n" + //
        "            SELECT COALESCE(SUM(fin - inicio - pausafin + pausainicio), INTERVAL '0 hours') \r\n" + //
        "            FROM paselista \r\n" + //
        "            WHERE paselista.usuario = :usuario  \r\n" + //
        "                AND paselista.fecha BETWEEN reportes.semanainicio AND reportes.semanafin \r\n" + //
        "        ) \r\n" + //
        "        WHERE EXISTS ( \r\n" + //
        "            SELECT 1 FROM paselista  \r\n" + //
        "            WHERE paselista.usuario = :usuario  \r\n" + //
        "                AND paselista.fecha BETWEEN reportes.semanainicio AND reportes.semanafin \r\n" + //
        "        ) AND usuario = :usuario; \r\n" + //
        "    ELSE \r\n" + //
        "        INSERT INTO reportes (usuario, puntuales) \r\n" + //
        "        SELECT \r\n" + //
        "            :usuario AS usuario, \r\n" + //
        "            COALESCE(SUM(fin - inicio - pausafin + pausainicio), INTERVAL '0 hours') AS puntuales \r\n" + //
        "        FROM paselista \r\n" + //
        "        WHERE paselista.usuario = :usuario  \r\n" + //
        "        AND paselista.fecha BETWEEN (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) - interval '1 day')  \r\n" + //
        "            AND (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) + interval '5 days'); \r\n" + //
        "    END IF; \r\n" + //
        "END $$;"
    )
    public Mono<Reportes> reportePorUsuario(@Param("usuario") UUID usuario);
}
