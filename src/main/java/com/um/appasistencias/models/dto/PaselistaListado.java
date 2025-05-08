package com.um.appasistencias.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class PaselistaListado {
    // modelo
    private UUID id;
    private UUID usuario;
    private UUID evento;
    private UUID cubiculo;
    private LocalDate fecha;
    private LocalTime inicio;
    private LocalTime fin;
    private LocalTime pausainicio;
    private LocalTime pausafin;
    // usuario info
    private String nombres;
    private String apellidos;
    // evento info
    private String titulo;
    private String lugar;
    // cubiculo info
    private String numero;
    private String edificio;
    // tempo
    private LocalTime pausa;

    public PaselistaListado() {}
    public PaselistaListado(UUID usuario, UUID evento, UUID cubiculo, LocalDate fecha, LocalTime inicio, LocalTime fin,
            LocalTime pausainicio, LocalTime pausafin, String nombres, String apellidos, String titulo, String lugar,
            String numero, String edificio, LocalTime pausa) {
        this.usuario = usuario;
        this.evento = evento;
        this.cubiculo = cubiculo;
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.pausainicio = pausainicio;
        this.pausafin = pausafin;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.titulo = titulo;
        this.lugar = lugar;
        this.numero = numero;
        this.edificio = edificio;
        this.pausa = pausa;
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
    public UUID getEvento() {
        return evento;
    }
    public void setEvento(UUID evento) {
        this.evento = evento;
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
    public UUID getCubiculo() {
        return cubiculo;
    }
    public void setCubiculo(UUID cubiculo) {
        this.cubiculo = cubiculo;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getEdificio() {
        return edificio;
    }
    public void setEdificio(String edificio) {
        this.edificio = edificio;
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
    public LocalTime getPausa() {
        return pausa;
    }
    public void setPausa(LocalTime pausa) {
        this.pausa = pausa;
    }
    @Override
    public String toString() {
        return "PaselistaListado [id=" + id + ", usuario=" + usuario + ", nombres=" + nombres + ", apellidos=" + apellidos
                + ", evento=" + evento + ", titulo=" + titulo + ", lugar=" + lugar + ", cubiculo=" + cubiculo
                + ", numero=" + numero + ", edificio=" + edificio + ", fecha=" + fecha + ", inicio=" + inicio + ", fin="
                + fin + ", pausainicio=" + pausainicio + ", pausafin=" + pausafin + ", pausa=" + pausa + "]";
    }
    
}