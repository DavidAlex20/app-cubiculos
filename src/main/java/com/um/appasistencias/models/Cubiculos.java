package com.um.appasistencias.models;

import org.springframework.data.annotation.Id;

public class Cubiculos {
    @Id private String id;
    private int numero;
    private String edificio;
    private boolean disponible;

    public Cubiculos(int numero, String edificio, boolean disponible) {
        this.numero = numero;
        this.edificio = edificio;
        this.disponible = disponible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return "Cubiculos [id=" + id + ", numero=" + numero + ", edificio=" + edificio + ", disponible=" + disponible
                + "]";
    }
}
