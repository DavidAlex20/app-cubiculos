package com.um.appasistencias.models;

import org.springframework.data.annotation.Id;

public class Eventos {
    @Id private String id;
    private String titulo;
    private String lugar;
    private String inicio;
    private String fin;

    public Eventos(String titulo, String lugar, String inicio, String fin) {
        this.titulo = titulo;
        this.lugar = lugar;
        this.inicio = inicio;
        this.fin = fin;
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

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Eventos [id=" + id + ", titulo=" + titulo + ", lugar=" + lugar + ", inicio=" + inicio + ", fin=" + fin
                + "]";
    }

}
