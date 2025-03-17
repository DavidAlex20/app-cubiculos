package com.um.appasistencias.models;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;

public class Eventos {
    @Id private String id;
    private String titulo;
    private String lugar;
    private LocalTime inicio;
    private LocalTime fin;
    private LocalDate fecha;

    public Eventos(String titulo, String lugar, LocalTime inicio, LocalTime fin, LocalDate fecha) {
        this.titulo = titulo;
        this.lugar = lugar;
        this.inicio = inicio;
        this.fin = fin;
        this.fecha = fecha;
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

    public LocalDate getFecha() {
        return this.fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Eventos [id=" + id + ", titulo=" + titulo + ", lugar=" + lugar + ", inicio=" + inicio + ", fin=" + fin
                + "]";
    }

}
