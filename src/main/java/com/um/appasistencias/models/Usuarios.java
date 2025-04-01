package com.um.appasistencias.models;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Usuarios implements UserDetails {

    @Id private UUID id;
    private String username;
    private String password;
    private String role;
    private String nombres;
    private String apellidos;
    private String numempleado;
    private String status;
    private boolean activo;
    private String email;
    
    public Usuarios(String username, String password, String role, String nombres, String apellidos, String numempleado, String status, boolean activo, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numempleado = numempleado;
        this.status = status;
        this.activo = activo;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getNumempleado() {
        return numempleado;
    }

    public void setNumempleado(String numempleado) {
        this.numempleado = numempleado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role
                + ", nombres=" + nombres + ", apellidos=" + apellidos + ", numempleado=" + numempleado + ", status="
                + status + ", activo=" + activo + ", email=" + email + "]";
    }

}
