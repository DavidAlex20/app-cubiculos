package com.um.appasistencias.models;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Reportes {
    @Id private UUID id;
    private UUID usuario;
    private LocalDate semanainicio;
    private LocalDate semanafin;
    private Duration puntuales;
    private Duration impuntuales;
    private int faltas;

    public Reportes(UUID usuario, LocalDate semanainicio, LocalDate semanafin, Duration puntuales, Duration impuntuales,
            int faltas) {
        this.usuario = usuario;
        this.semanainicio = semanainicio;
        this.semanafin = semanafin;
        this.puntuales = puntuales;
        this.impuntuales = impuntuales;
        this.faltas = faltas;
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

    public Duration getPuntuales() {
        return puntuales;
    }

    public void setPuntuales(Duration puntuales) {
        this.puntuales = puntuales;
    }

    public Duration getImpuntuales() {
        return impuntuales;
    }

    public void setImpuntuales(Duration impuntuales) {
        this.impuntuales = impuntuales;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    @Override
    public String toString() {
        return "Reportes [id=" + id + ", usuario=" + usuario + ", semanainicio=" + semanainicio + ", semanafin="
                + semanafin + ", puntuales=" + puntuales + ", impuntuales=" + impuntuales + ", faltas=" + faltas + "]";
    }

}
