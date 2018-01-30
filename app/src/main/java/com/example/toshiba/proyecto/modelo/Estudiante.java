package com.example.toshiba.proyecto.modelo;

/**
 * Created by PaulCalle on 14/01/18.
 */
import java.util.List;

public class Estudiante{

    private int est_id;//Declaración de la variable que representa el id dela tabla de tipo int.
    private String est_nombre;// Declaración de la variable que representa el nombre en este caso del estudiante de tipo String.
    private String est_apellido;//Declaración de la variable que representa el apellido en este caso del estudiante de tipo String.
    private String est_cedula;//Declaración de la variable que representa la cédula en este caso del estudiante de tipo String.

    private String est_telefono;//Declaración de la variable que representa el teléfono en este caso del estudiante de tipo String.

    private String est_mail;//Declaración de la variable que representa el mail en este caso del estudiante de tipo String.

    private String est_contrasena;//Declaración de la variable que representa la contraseña en este caso del estudiante de tipo String.


	private List<SolicitudCita> solicitudCitas;// Creación de una lista para poder almacenar varios datos que se registren producto de la relación antes creada.

    public int getEst_id() {// Métodos getters y setters Getters se utiliza para definir una propieda y Setters
        return est_id;
    }

    public void setEst_id(int est_id) {
        this.est_id = est_id;
    }

    public String getEst_nombre() {
        return est_nombre;
    }

    public void setEst_nombre(String est_nombre) {
        this.est_nombre = est_nombre;
    }

    public String getEst_apellido() {
        return est_apellido;
    }

    public void setEst_apellido(String est_apellido) {
        this.est_apellido = est_apellido;
    }

    public String getEst_cedula() {
        return est_cedula;
    }

    public void setEst_cedula(String est_cedula) {
        this.est_cedula = est_cedula;
    }

    public String getEst_telefono() {
        return est_telefono;
    }

    public void setEst_telefono(String est_telefono) {
        this.est_telefono = est_telefono;
    }

    public String getEst_mail() {
        return est_mail;
    }

    public void setEst_mail(String est_mail) {
        this.est_mail = est_mail;
    }

    public String getEst_contrasena() {
        return est_contrasena;
    }

    public void setEst_contrasena(String est_contrasena) {
        this.est_contrasena = est_contrasena;
    }

    public List<SolicitudCita> getSolicitudCitas() {
        return solicitudCitas;
    }

    public void setSolicitudCitas(List<SolicitudCita> solicitudCitas) {
        this.solicitudCitas = solicitudCitas;
    }


    @Override
    public String toString() {
        return "Estudiante [est_id=" + est_id + ", est_nombre=" + est_nombre + ", est_apellido=" + est_apellido
                + ", est_cedula=" + est_cedula + ", est_telefono=" + est_telefono + ", est_mail=" + est_mail
                + ", est_contrasena=" + est_contrasena + "]";
    }
}
