package com.cloud.jml.dto;

import java.time.LocalDateTime;

public class ClienteDTO {

    private String identificacion;
    private String identificacionOriginal;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String direccion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public ClienteDTO() {
        // Constructor
    }

    public ClienteDTO(String identificacion, String identificacionOriginal, String nombres, String apellidos, String telefono, String direccion, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.identificacion = identificacion;
        this.identificacionOriginal = identificacionOriginal;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    // Getters y Setters
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getIdentificacionOriginal() {
        return identificacionOriginal;
    }

    public void setIdentificacionOriginal(String identificacionOriginal) {
        this.identificacionOriginal = identificacionOriginal;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
