package com.um.appasistencias.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class PaselistaUser {
    // modelo
    private UUID id;
    private UUID usuario;
    private UUID evento = null;
    private UUID cubiculo = null;
    private String fecha;
    private LocalTime inicio;
    private LocalTime fin;
    //datos usuario
    private String nombres;
    private String apellidos;
    
    public PaselistaUser(UUID usuario, UUID evento, UUID cubiculo, String fecha, LocalTime inicio, LocalTime fin,
            String nombres, String apellidos) {
        this.usuario = usuario;
        this.evento = evento;
        this.cubiculo = cubiculo;
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.nombres = nombres;
        this.apellidos = apellidos;
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

    @Override
    public String toString() {
        return "PaselistaDto [id=" + id + ", usuario=" + usuario + ", evento=" + evento + ", cubiculo=" + cubiculo
                + ", fecha=" + fecha + ", inicio=" + inicio + ", fin=" + fin + ", nombres=" + nombres + ", apellidos="
                + apellidos + "]";
    }
}
