package com.um.appasistencias.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class EventosEstado {
    //evento
    private UUID id;
    private String titulo;
    private String lugar;
    private LocalTime inicio;
    private LocalTime fin;
    private LocalDate fecha;
    //paselista
    private boolean participacion;
    private LocalTime participacioninicio;
    private LocalTime participacionfin;

    public EventosEstado(String titulo, String lugar, LocalTime inicio, LocalTime fin, LocalDate fecha,
            boolean participacion, LocalTime participacioninicio, LocalTime participacionfin) {
        this.titulo = titulo;
        this.lugar = lugar;
        this.inicio = inicio;
        this.fin = fin;
        this.fecha = fecha;
        this.participacion = participacion;
        this.participacioninicio = participacioninicio;
        this.participacionfin = participacionfin;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isParticipacion() {
        return participacion;
    }

    public void setParticipacion(boolean participacion) {
        this.participacion = participacion;
    }

    public LocalTime getParticipacioninicio() {
        return participacioninicio;
    }

    public void setParticipacioninicio(LocalTime participacioninicio) {
        this.participacioninicio = participacioninicio;
    }

    public LocalTime getParticipacionfin() {
        return participacionfin;
    }

    public void setParticipacionfin(LocalTime participacionfin) {
        this.participacionfin = participacionfin;
    }

    @Override
    public String toString() {
        return "EventosEstado [id=" + id + ", titulo=" + titulo + ", lugar=" + lugar + ", inicio=" + inicio + ", fin="
                + fin + ", fecha=" + fecha + ", participacion=" + participacion + ", participacioninicio="
                + participacioninicio + ", participacionfin=" + participacionfin + "]";
    }
    
}
