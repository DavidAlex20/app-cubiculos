package com.um.appasistencias.models.dto;

import com.um.appasistencias.models.Usuarios;

public class DatosVista {
    // datos de ususario en sesion
    private String usuario;
    private String nombres;
    private String apellidos;
    private String numempleado;
    private String role;
    private String status;

    // datos de interfaz
    private String nombreicon;
    private String pagina = "index";
    private String titulo;
    private boolean admin = false;

    public DatosVista(Usuarios user, String page, String titulo, boolean isAdmin) {
        this.usuario = user.getUsername();
        this.nombres = user.getNombres();
        this.apellidos = user.getApellidos();
        this.numempleado = user.getNumempleado();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.pagina = page;
        this.titulo = titulo;
        this.admin = isAdmin;
        this.nombreicon = getNombreicon(user.getNombres(), user.getApellidos());
    }

    private String getNombreicon(String nombres, String apellidos) {
        String result = "";
        int spaceIndex = nombres.indexOf(' ');
        if(spaceIndex != -1) {
            result += nombres.substring(0, spaceIndex);
        } else {
            result += nombres;
        }
        result += " " + apellidos.substring(0, 1);
        return result;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNumempleado() {
        return numempleado;
    }

    public String getRole() {
        return role;
    }

    public String getStatus() {
        return status;
    }

    public String getNombreicon() {
        return nombreicon;
    }

    public String getPagina() {
        return pagina;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isAdmin() {
        return admin;
    }

    @Override
    public String toString() {
        return "DatosVista [usuario=" + usuario + ", nombres=" + nombres + ", apellidos=" + apellidos + ", numempleado="
                + numempleado + ", role=" + role + ", status=" + status + ", nombreicon=" + nombreicon + ", pagina=" + pagina + ", titulo=" + titulo + ", admin="
                + admin + "]";
    }

}
