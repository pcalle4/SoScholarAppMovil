package com.example.toshiba.proyecto.modelo;

import java.util.List;

/**
 * Created by PaulCalle on 11/01/18.
 */

public class Tutor {

    private int tut_id;
    private String tut_nombre;
    private String tut_apellido;
    private String tut_cedula;
    private String tut_telefono;
    private String tut_mail;
    private String tut_contrasena;
    private String tut_area;
    private String tut_especialidad;
    private String tut_anivel;
    private double tut_precio;
    private List<Horarios> horarios;

    public List<Horarios> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horarios> horarios) {
        this.horarios = horarios;
    }

    public int getTut_id() {
        return tut_id;
    }

    public void setTut_id(int tut_id) {
        this.tut_id = tut_id;
    }

    public String getTut_nombre() {
        return tut_nombre;
    }

    public void setTut_nombre(String tut_nombre) {
        this.tut_nombre = tut_nombre;
    }

    public String getTut_apellido() {
        return tut_apellido;
    }

    public void setTut_apellido(String tut_apellido) {
        this.tut_apellido = tut_apellido;
    }

    public String getTut_cedula() {
        return tut_cedula;
    }

    public void setTut_cedula(String tut_cedula) {
        this.tut_cedula = tut_cedula;
    }

    public String getTut_telefono() {
        return tut_telefono;
    }

    public void setTut_telefono(String tut_telefono) {
        this.tut_telefono = tut_telefono;
    }

    public String getTut_mail() {
        return tut_mail;
    }

    public void setTut_mail(String tut_mail) {
        this.tut_mail = tut_mail;
    }

    public String getTut_contrasena() {
        return tut_contrasena;
    }

    public void setTut_contrasena(String tut_contrasena) {
        this.tut_contrasena = tut_contrasena;
    }

    public String getTut_area() {
        return tut_area;
    }

    public void setTut_area(String tut_area) {
        this.tut_area = tut_area;
    }

    public String getTut_especialidad() {
        return tut_especialidad;
    }

    public void setTut_especialidad(String tut_especialidad) {
        this.tut_especialidad = tut_especialidad;
    }

    public String getTut_anivel() {
        return tut_anivel;
    }

    public void setTut_anivel(String tut_anivel) {
        this.tut_anivel = tut_anivel;
    }

    public double getTut_precio() {
        return tut_precio;
    }

    public void setTut_precio(double tut_precio) {
        this.tut_precio = tut_precio;
    }

    @Override
    public String toString() {
        return tut_nombre + " " + tut_apellido;
    }
}
