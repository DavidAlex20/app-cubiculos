package com.um.appasistencias.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import io.r2dbc.spi.Row;

public class Paselista {
    @Id private UUID id;
    private UUID usuario;
    private UUID evento;
    private UUID cubiculo;
    private LocalDate fecha;
    private LocalTime inicio;
    private LocalTime fin;
    private LocalTime pausainicio;
    private LocalTime pausafin;
    
    public Paselista(){
        this.usuario = null;
        this.evento = null;
        this.cubiculo = null;
        this.fecha = null;
        this.inicio = null;
        this.fin = null;
        this.pausainicio = null;
        this.pausafin = null;
    };
    public Paselista(UUID usuario, UUID evento, UUID cubiculo, LocalDate fecha, 
        LocalTime inicio, LocalTime fin, LocalTime pausainicio, LocalTime pausafin) {
        this.usuario = usuario;
        this.evento = evento;
        this.cubiculo = cubiculo;
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.pausainicio = pausainicio;
        this.pausafin = pausafin;
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

    public UUID getEvento() {
        return evento;
    }

    public void setEvento(UUID evento) {
        this.evento = evento;
    }

    public UUID getCubiculo() {
        return cubiculo;
    }

    public void setCubiculo(UUID cubiculo) {
        this.cubiculo = cubiculo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public LocalTime getPausainicio() {
        return pausainicio;
    }

    public void setPausainicio(LocalTime pausainicio) {
        this.pausainicio = pausainicio;
    }

    public LocalTime getPausafin() {
        return pausafin;
    }

    public void setPausafin(LocalTime pausafin) {
        this.pausafin = pausafin;
    }

    @Override
    public String toString() {
        return "Paselista [id=" + id + ", usuario=" + usuario + ", evento=" + evento + ", cubiculo=" + cubiculo
                + ", fecha=" + fecha + ", inicio=" + inicio + ", fin=" + fin + ", pausainicio=" + pausainicio + ", pausafin=" + pausafin + "]";
    }
}
