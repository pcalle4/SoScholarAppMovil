package com.example.toshiba.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.toshiba.proyecto.modelo.Calificacion;

import java.util.ArrayList;

/**
 * Created by PaulCalle on 1/11/2018.
 */

public class ListAdapterComentarios extends ArrayAdapter<Calificacion>  {
    //Contenxto de la aplicacion que relaciona al ListView y el Adpater
    private Context context;

    /**
     * Inicializacion
     *
     * @param context Contexto de la App desde la que se invoca
     * @param items   //Coleccion de objetos a presentar
     */
    public ListAdapterComentarios(Context context, ArrayList<Calificacion> items) {
        super(context, R.layout.item_comentario, items);
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
            view = inflater.inflate(R.layout.item_comentario, null);
        }

        //Objeto Categoria a visualizar segun position
        final Calificacion item = getItem(position);
        if (item != null) {
            // Recupera los elementos de vista y setea en funcion de valores de objeto
         //   TextView calificacion = (TextView) view.findViewById(R.id.CCali);
            TextView comentario = (TextView) view.findViewById(R.id.CComentario);
;

            if (comentario != null) {
               // comentario.setText("Calificacion: "+item.getEsp_calificacion());
                comentario.setText("Comentario: "+item.getDescripcion()+"");
            }
        }
        return view;
    }



}
