package com.um.appasistencias.repositories;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.um.appasistencias.models.Reportes;
import com.um.appasistencias.models.dto.ReportesListado;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReportesRepository extends R2dbcRepository<Reportes, UUID>{
    /** ESTA FUNCION SOLO ES FUNCIONAL USANDOLA DIRECTO EN LA BASE DE DATOS, 
     * LO POSTERIOR SON INTENTOS DE SEPARAR LA CONSULTA EN PARTES PARA HACERLA FUNCIOANR,
     * MAS NO PARECE DAR RESULTADO */

    // @Query(
    //     "DO $$ BEGIN  \r\n" + //
    //     "    IF EXISTS (  \r\n" + //
    //     "        SELECT 1 FROM reportes  \r\n" + //
    //     "        WHERE usuario = :usuario  \r\n" + //
    //     "            AND semanainicio = (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) - interval '1 day') \r\n" + //
    //     "            AND semanafin = (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) + interval '5 days') \r\n" + //
    //     "    ) \r\n" + //
    //     "    THEN \r\n" + //
    //     "        UPDATE reportes \r\n" + //
    //     "        SET puntuales = ( \r\n" + //
    //     "            SELECT COALESCE(SUM(fin - inicio - pausafin + pausainicio), INTERVAL '0 hours') \r\n" + //
    //     "            FROM paselista \r\n" + //
    //     "            WHERE paselista.usuario = :usuario  \r\n" + //
    //     "                AND paselista.fecha BETWEEN reportes.semanainicio AND reportes.semanafin \r\n" + //
    //     "        ) \r\n" + //
    //     "        WHERE EXISTS ( \r\n" + //
    //     "            SELECT 1 FROM paselista  \r\n" + //
    //     "            WHERE paselista.usuario = :usuario  \r\n" + //
    //     "                AND paselista.fecha BETWEEN reportes.semanainicio AND reportes.semanafin \r\n" + //
    //     "        ) AND usuario = :usuario; \r\n" + //
    //     "    ELSE \r\n" + //
    //     "        INSERT INTO reportes (usuario, puntuales) \r\n" + //
    //     "        SELECT \r\n" + //
    //     "            :usuario AS usuario, \r\n" + //
    //     "            COALESCE(SUM(fin - inicio - pausafin + pausainicio), INTERVAL '0 hours') AS puntuales \r\n" + //
    //     "        FROM paselista \r\n" + //
    //     "        WHERE paselista.usuario = :usuario  \r\n" + //
    //     "        AND paselista.fecha BETWEEN (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) - interval '1 day')  \r\n" + //
    //     "            AND (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) + interval '5 days'); \r\n" + //
    //     "    END IF; \r\n" + //
    //     "END $$;"
    // )
    // public Mono<Reportes> reportePorUsuario(@Param("usuario") UUID usuario);

    @Query("""
        SELECT rep.id, rep.usuario, usu.nombres, usu.apellidos, rep.semanainicio, rep.semanafin, rep.puntuales 
        FROM reportes rep 
        LEFT JOIN usuarios usu ON rep.usuario = usu.id;
    """)
    public Flux<ReportesListado> reportesListados();

    @Query("SELECT EXISTS (SELECT 1 FROM reportes WHERE id = :id);")
    public Mono<Boolean> reporteExistente(@Param("id") UUID id);

    @Query("""
        SELECT COALESCE(SUM(
            (paselista.fin - paselista.inicio) +
            CASE WHEN paselista.pausainicio IS NOT NULL AND paselista.pausafin IS NOT NULL
                AND paselista.pausainicio <> TIME '00:00' AND paselista.pausafin <> TIME '00:00'
            THEN paselista.pausainicio - paselista.pausafin
            ELSE INTERVAL '0'
            END
        ), INTERVAL '0')
        FROM paselista 
        WHERE paselista.usuario = :usuario
            AND paselista.fecha BETWEEN (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) - interval '1 day') 
            AND (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) + interval '5 days')
        ;
    """)
    public Mono<String> calcularPuntuales(@Param("usuario") UUID usuario);

    @Query("""
        SELECT COALESCE(SUM(
            (paselista.fin - paselista.inicio) +
            CASE WHEN paselista.pausainicio IS NOT NULL AND paselista.pausafin IS NOT NULL
                AND paselista.pausainicio <> TIME '00:00' AND paselista.pausafin <> TIME '00:00'
            THEN paselista.pausainicio - paselista.pausafin
            ELSE INTERVAL '0'
            END
        ), INTERVAL '0')
        FROM paselista 
        WHERE paselista.usuario = :usuario
            AND paselista.fecha BETWEEN :semanainicio AND :semanafin
        ;
    """)
    public Mono<String> calcularPuntualesPorFecha(
        @Param("usuario") UUID usuario,
        @Param("semanainicio") LocalDate semanainicio,
        @Param("semanafin") LocalDate semanafin
    );

    @Query("""
       INSERT INTO reportes (usuario, semanainicio, semanafin, puntuales)
       VALUES (
            :usuario,
            (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) - interval '1 day'),
            (date_trunc('week', (CURRENT_DATE)::timestamp with time zone) + interval '5 days'),
            :puntuales::interval
       );
    """)
    public Mono<Reportes> guardar(
        @Param("usuario") UUID usuario, 
        @Param("puntuales") String puntuales
    );

    @Query("""
       INSERT INTO reportes (usuario, semanainicio, semanafin, puntuales)
       VALUES (
            :usuario,
            :semanainicio,
            :semanafin,
            :puntuales::interval
       );
    """)
    public Mono<Reportes> guardarPorFecha(
        @Param("usuario") UUID usuario,
        @Param("semanainicio") LocalDate semanainicio,
        @Param("semanafin") LocalDate semanafin,
        @Param("puntuales") String puntuales
    );

    @Query("UPDATE reportes SET puntuales = :puntuales::interval WHERE id = :id;")
    public Mono<Reportes> actualizar(@Param("id") UUID id, @Param("puntuales") String puntuales);
}
