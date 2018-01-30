package com.example.toshiba.proyecto.modelo;

/**
 * Created by PaulCalle on 14/01/18.
 */

public class Horarios{

    private int codigo;

    private String horario;

    private Double precio;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return horario;
    }


}
