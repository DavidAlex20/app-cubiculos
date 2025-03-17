package com.um.appasistencias.models.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.um.appasistencias.models.Eventos;

public class EventosDto {
    private String id;
    private String titulo;
    private String lugar;
    private LocalTime inicio;
    private LocalTime fin;
    private String fecha;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public EventosDto(Eventos evento) {
        this.id = evento.getId();
        this.titulo = evento.getTitulo();
        this.lugar = evento.getLugar();
        this.inicio = evento.getInicio();
        this.fin = evento.getFin();
        this.fecha = evento.getFecha().format(formatter);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

    @Override
    public String toString() {
        return "EventosDto [id=" + id + ", titulo=" + titulo + ", lugar=" + lugar + ", inicio=" + inicio + ", fin="
                + fin + ", fecha=" + fecha + "]";
    }

}
