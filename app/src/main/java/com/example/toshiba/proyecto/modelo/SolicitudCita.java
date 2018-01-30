package com.example.toshiba.proyecto.modelo;

/**
 * Created by PaulCalle on 14/01/18.
 */
import java.util.Date;

public class SolicitudCita{

    private int codigo;
    private Date fecha;
    private Tutor tutor;
    private Estudiante estudiante;
    private LugarNivelacion lugarNivelaciones;
    private Horarios horarios;
    private String aceptado;

    public String getAceptado() {
        return aceptado;
    }

    public void setAceptado(String aceptado) {
        this.aceptado = aceptado;
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public LugarNivelacion getLugarNivelaciones() {
        return lugarNivelaciones;
    }

    public void setLugarNivelaciones(LugarNivelacion lugarNivelaciones) {
        this.lugarNivelaciones = lugarNivelaciones;
    }

    public Horarios getHorarios() {
        return horarios;
    }

    public void setHorarios(Horarios horarios) {
        this.horarios = horarios;
    }
}