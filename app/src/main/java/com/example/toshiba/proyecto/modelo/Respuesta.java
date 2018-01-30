package com.example.toshiba.proyecto.modelo;

/**
 * Created by PaulCalle on 10/01/2018.
 */

public class Respuesta {

    private int codigo;
    private String mensaje;

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    @Override
    public String toString() {
        return "Respuesta [codigo=" + codigo + ", mensaje=" + mensaje + "]";
    }

}
