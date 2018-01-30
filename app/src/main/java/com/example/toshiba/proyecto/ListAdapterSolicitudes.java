package com.example.toshiba.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.toshiba.proyecto.modelo.SolicitudCita;

import java.util.ArrayList;

/**
 * Created by PaulCalle on 1/11/2018.
 */

public class ListAdapterSolicitudes extends ArrayAdapter<SolicitudCita>  {
    //Contenxto de la aplicacion que relaciona al ListView y el Adpater
    private Context context;

    /**
     * Inicializacion
     *
     * @param context Contexto de la App desde la que se invoca
     * @param items   //Coleccion de objetos a presentar
     */
    public ListAdapterSolicitudes(Context context, ArrayList<SolicitudCita> items) {
        super(context, R.layout.item_citas, items);
        this.context = context;
    }

    /**
     * View a presentar en pantalla correspondiente a un item de ListView
     *
     * @param position    //Indice del ListItem
     * @param convertView //Contexto o contenedor de View
     * @param parent      //Contendor padre
     * @return  Objeto View
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_citas, null);
        }

        //Objeto Solicitud Cita a visualizar segun position
        final SolicitudCita item = getItem(position);
        if (item != null) {
            // Recupera los elementos de vista y setea en funcion de valores de objeto
            TextView tutor = (TextView) view.findViewById(R.id.Itutor);
            TextView materia = (TextView) view.findViewById(R.id.Imateria);
            TextView fecha = (TextView) view.findViewById(R.id.Ifecha);
            TextView horario = (TextView) view.findViewById(R.id.Ihoraio);
            TextView lugar = (TextView) view.findViewById(R.id.Ilugar);
            TextView precio = (TextView) view.findViewById(R.id.Iprecio);

            if (tutor != null) {
                tutor.setText("Tutor: "+item.getTutor().getTut_nombre()+" "+item.getTutor().getTut_apellido());
                materia.setText("Area: "+item.getTutor().getTut_area()+"");
                horario.setText("Horario: "+item.getHorarios().getHorario()+"");
                lugar.setText("Lugar: "+item.getLugarNivelaciones().getLugar()+"");
                precio.setText("Precio: "+item.getHorarios().getPrecio()+"");
                fecha.setText("Fecha: 25/01/18");
            }
        }
        return view;
    }



}
