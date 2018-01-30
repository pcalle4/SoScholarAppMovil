package com.example.toshiba.proyecto.modelo;

import java.util.Date;

/**
 * Created by PaulCalle on 1/16/2018.
 */

public class Solicitudes {
    private int id;
    private Date fecha;
    private String horarios;
    private String tutor;
    private String lugar;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public String getHorarios() {
        return horarios;
    }
    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }
    public String getTutor() {
        return tutor;
    }
    public void setTutor(String tutor) {
        this.tutor = tutor;
    }
    public String getLugar() {
        return lugar;
    }
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }


}