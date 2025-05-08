package com.um.appasistencias.models.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

public class ReportesListado {
    private UUID id;
    private UUID usuario;
    private String nombres;
    private String apellidos;
    private LocalDate semanainicio;
    private LocalDate semanafin;
    private Duration puntuales;
    
    public ReportesListado(UUID id, UUID usuario, String nombres, String apellidos, LocalDate semanainicio,
            LocalDate semanafin, Duration puntuales) {
        this.id = id;
        this.usuario = usuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.semanainicio = semanainicio;
        this.semanafin = semanafin;
        this.puntuales = puntuales;
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

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public Duration getPuntuales() {
        return puntuales;
    }

    public void setPuntuales(Duration puntuales) {
        this.puntuales = puntuales;
    }

    @Override
    public String toString() {
        return "ReportesListado [id=" + id + ", usuario=" + usuario + ", nombres=" + nombres + ", apellidos="
                + apellidos + ", semanainicio=" + semanainicio + ", semanafin=" + semanafin + ", puntuales=" + puntuales
                + "]";
    }
}
