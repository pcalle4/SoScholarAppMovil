package com.example.toshiba.proyecto.modelo;

/**
 * Created by PaulCalle on 25/01/18.
 */

public class Calificacion {

    private int codigo;
    private int esp_calificacion;
    private String descripcion;
    private Tutor tutor;
    private Estudiante estudiante;
    private SolicitudCita solicitudCita;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getEsp_calificacion() {
        return esp_calificacion;
    }

    public void setEsp_calificacion(int esp_calificacion) {
        this.esp_calificacion = esp_calificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public SolicitudCita getSolicitudCita() {
        return solicitudCita;
    }

    public void setSolicitudCita(SolicitudCita solicitudCita) {
        this.solicitudCita = solicitudCita;
    }

    public String toString() {
        return descripcion;
    }
}
