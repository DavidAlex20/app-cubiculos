package com.um.appasistencias.models.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.um.appasistencias.models.Paselista;

public class PaselistaDto {
    private UUID id;
    private UUID usuario;
    private UUID evento;
    private UUID cubiculo;
    private String fecha;
    private LocalTime inicio;
    private LocalTime fin;
    private LocalTime pausainicio;
    private LocalTime pausafin;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PaselistaDto(Paselista pase) {
        this.id = pase.getId();
        this.usuario = pase.getUsuario();
        this.evento = pase.getEvento();
        this.cubiculo = pase.getCubiculo();
        this.fecha = pase.getFecha().format(formatter);
        this.inicio = pase.getInicio();
        this.fin = pase.getFin();
        this.pausainicio = pase.getPausainicio();
        this.pausafin = pase.getPausafin();
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
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
        return "PaselistaDto [id=" + id + ", usuario=" + usuario + ", evento=" + evento + ", cubiculo=" + cubiculo
                + ", fecha=" + fecha + ", inicio=" + inicio + ", fin=" + fin + ", pausainicio=" + pausainicio + ", pausafin=" + pausafin + "]";
    }
    
}
