package com.um.appasistencias.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class EventosEstado {
    private UUID id;
    private String titulo;
    private String lugar;
    private LocalTime inicio;
    private LocalTime fin;
    private LocalDate fecha;
    private LocalTime pasinicio;
    private LocalTime pasfin;
    private int estado;
    
    public EventosEstado(String titulo, String lugar, LocalTime inicio, LocalTime fin, LocalDate fecha, LocalTime pasinicio, LocalTime pasfin, int estado) {
        this.titulo = titulo;
        this.lugar = lugar;
        this.inicio = inicio;
        this.fin = fin;
        this.fecha = fecha;
        this.pasinicio = pasinicio;
        this.pasfin = pasfin;
        this.estado = estado;
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getLugar() {
        return lugar;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getPasinicio() {
        return pasinicio;
    }

    public LocalTime getPasfin() {
        return pasfin;
    }

    public int getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "EventosEstado [id=" + id + ", titulo=" + titulo + ", lugar=" + lugar + ", inicio=" + inicio + ", fin="
                + fin + ", fecha=" + fecha + ", pasinicio=" + pasinicio + ", pasfin=" + pasfin + ", estado=" + estado + "]";
    }

}
