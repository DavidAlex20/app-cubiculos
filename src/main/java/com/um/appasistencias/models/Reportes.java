package com.um.appasistencias.models;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import io.r2dbc.postgresql.codec.Interval;

public class Reportes {
    @Id private UUID id;
    private UUID usuario;
    private LocalDate semanainicio;
    private LocalDate semanafin;
    private Interval puntuales;

    public Reportes(){
        this.id = null;
        this.usuario = null;
        this.semanainicio = null;
        this.semanafin = null;
        this.puntuales = null;
    }
    public Reportes(UUID usuario, LocalDate semanainicio, LocalDate semanafin, Interval puntuales) {
        this.usuario = usuario;
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

    public Interval getPuntuales() {
        return puntuales;
    }

    public void setPuntuales(Interval puntuales) {
        this.puntuales = puntuales;
    }

    @Override
    public String toString() {
        return "Reportes [id=" + id + ", usuario=" + usuario + ", semanainicio=" + semanainicio + ", semanafin="
                + semanafin + ", puntuales=" + puntuales + "]";
    }

}
