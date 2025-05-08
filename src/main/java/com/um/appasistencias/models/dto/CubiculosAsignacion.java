package com.um.appasistencias.models.dto;

import java.util.UUID;

public class CubiculosAsignacion {
    private UUID id;
    private int numero;
    private String edificio;
    private boolean disponible;
    private UUID asignacion = null;
    private String nombres;
    private String apellidos;

    public CubiculosAsignacion(int numero, String edificio, boolean disponible, UUID asignacion, String nombres, String apellidos) {
        this.numero = numero;
        this.edificio = edificio;
        this.disponible = disponible;
        this.asignacion = asignacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public UUID getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(UUID asignacion) {
        this.asignacion = asignacion;
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
        return "Cubiculos [id=" + id + ", numero=" + numero + ", edificio=" + edificio + ", disponible=" + disponible + ", asignacion=" 
                + asignacion + ", nombres=" + nombres + ", apellidos=" + apellidos
                + "]";
    }
}
