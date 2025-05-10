package com.um.appasistencias.models.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ReportesDto {
    private UUID id;
    private UUID usuario;
    private LocalDate semanainicio;
    private LocalDate semanafin;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ReportesDto() {
        this.id = null;
        this.usuario = null;
        this.semanainicio = null;
        this.semanafin = null;
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

    public LocalDate getSemanainicio() {
        return semanainicio;
    }

    public void setSemanainicio(LocalDate semanainicio) {
        this.semanainicio = semanainicio;
    }

    public LocalDate getSemanafin() {
        return semanafin;
    }

    public void setSemanafin(LocalDate semanafin) {
        this.semanafin = semanafin;
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    @Override
    public String toString() {
        return "ReportesDto [id=" + id + ", usuario=" + usuario + ", semanainicio=" + semanainicio + ", semanafin="
                + semanafin + "]";
    }

    
}
