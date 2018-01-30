package com.example.toshiba.proyecto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.toshiba.proyecto.modelo.Tutor;

import java.util.ArrayList;

/**
 * Created by PaulCalle on 1/11/2018.
 */

public class ListAdapterTutorList extends ArrayAdapter<Tutor>  {
    //Contenxto de la aplicacion que relaciona al ListView y el Adpater
    private Context context;

    /**
     * Inicializacion
     *
     * @param context Contexto de la App desde la que se invoca
     * @param items   //Coleccion de objetos a presentar
     */
    public ListAdapterTutorList(Context context, ArrayList<Tutor> items) {
        super(context, R.layout.item_tutor, items);
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
            view = inflater.inflate(R.layout.item_tutor, null);
        }

        //Objeto Tutores a visualizar segun position
        final Tutor item = getItem(position);
        if (item != null) {

            Button calificar = (Button) view.findViewById(R.id.tut_resena);
            // Capture button clicks
            calificar.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    SharedPreferences miPreferencia = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = miPreferencia.edit();
                    editor.putString("idUsuario", item.getTut_id()+"");
                  //  editor.putString("idUsuario", tutor.getTut_id()+"");
                    editor.commit();

                    Intent myIntent = new Intent(context, ComentariosActivity.class);
                    context.startActivity(myIntent);
                 //   startActivity(myIntent);


                }
            });


            // Recupera los elementos de vista y setea en funcion de valores de objeto
            TextView tutor = (TextView) view.findViewById(R.id.Ttutor);
            TextView materia = (TextView) view.findViewById(R.id.Tmateria);
            TextView telenono = (TextView) view.findViewById(R.id.TTelefono);
            TextView email = (TextView) view.findViewById(R.id.TEmail);
            TextView espe = (TextView) view.findViewById(R.id.TEspecialidad);
            TextView nivel = (TextView) view.findViewById(R.id.TNivel);


            if (tutor != null) {
                tutor.setText("Tutor: "+item.getTut_nombre()+" "+item.getTut_apellido());
                materia.setText("Area: "+item.getTut_area()+"");
                telenono.setText("Telefono: "+item.getTut_telefono()+"");
                email.setText("Email: "+item.getTut_mail()+"");
                espe.setText("Especialidad: "+item.getTut_especialidad()+"");
                nivel.setText("Nivel: "+item.getTut_anivel()+"");
            }
        }
        return view;
    }


}
