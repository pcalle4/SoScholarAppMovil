package com.example.toshiba.proyecto.modelo;

/**
 * Created by PaulCalle on 14/01/18.
 */
import java.util.List;

public class LugarNivelacion {

    private int codigo;

    private String lugar;

    private List<SolicitudCita> solicitudCitas;




    public List<SolicitudCita> getSolicitudCitas() {
        return solicitudCitas;
    }

    public void setSolicitudCitas(List<SolicitudCita> solicitudCitas) {
        this.solicitudCitas = solicitudCitas;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @Override
    public String toString() {
        return lugar ;
    }


}

