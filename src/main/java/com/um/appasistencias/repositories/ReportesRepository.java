package com.um.appasistencias.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.um.appasistencias.models.Reportes;

@Repository
public interface ReportesRepository extends R2dbcRepository<Reportes, UUID>{
    
}
