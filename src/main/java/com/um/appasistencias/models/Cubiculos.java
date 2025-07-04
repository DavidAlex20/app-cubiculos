package com.um.appasistencias.models;

import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Cubiculos {
    @Id private UUID id;
    private int numero;
    private String edificio;
    private boolean disponible;
    private UUID asignacion = null;

    public Cubiculos(int numero, String edificio, boolean disponible, UUID asignacion) {
        this.numero = numero;
        this.edificio = edificio;
        this.disponible = disponible;
        this.asignacion = asignacion;
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

    @Override
    public String toString() {
        return "Cubiculos [id=" + id + ", numero=" + numero + ", edificio=" + edificio + ", disponible=" + disponible + ", asignacion=" + asignacion
                + "]";
    }
}
