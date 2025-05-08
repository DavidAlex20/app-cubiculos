package com.um.appasistencias.models.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.um.appasistencias.models.Reportes;

public class ReportesDto {
    private UUID id;
    private UUID usuario;
    private String semanainicio;
    private String semanafin;
    private Duration puntuales;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ReportesDto(Reportes reporte) {
        this.id = reporte.getId();
        this.usuario = reporte.getUsuario();
        this.semanainicio = reporte.getSemanainicio().format(formatter);
        this.semanafin = reporte.getSemanafin().format(formatter);
        this.puntuales = reporte.getPuntuales();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUsuario() {
        return usuario;
    }

    public void setUsuario(UUID usuario) {
        this.usuario = usuario;
    }

    public String getSemanainicio() {
        return semanainicio;
    }

    public void setSemanainicio(String semanainicio) {
        this.semanainicio = semanainicio;
    }

    public String getSemanafin() {
        return semanafin;
    }

    public void setSemanafin(String semanafin) {
        this.semanafin = semanafin;
    }

    public Duration getPuntuales() {
        return puntuales;
    }

    public void setPuntuales(Duration puntuales) {
        this.puntuales = puntuales;
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    @Override
    public String toString() {
        return "ReportesDto [id=" + id + ", usuario=" + usuario + ", semanainicio=" + semanainicio + ", semanafin="
                + semanafin + ", puntuales=" + puntuales + "]";
    }

    
}
