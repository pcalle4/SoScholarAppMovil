package com.example.toshiba.proyecto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.toshiba.proyecto.modelo.Respuesta;
import com.example.toshiba.proyecto.modelo.SolicitudCita;
import com.example.toshiba.proyecto.utilidades.ClienteRest;
import com.example.toshiba.proyecto.utilidades.OnTaskCompleted;
import com.example.toshiba.proyecto.utilidades.Util;

import java.util.ArrayList;

/**
 * Created by PaulCalle on 1/11/2018.
 */

public class ListAdapterSolicitudesPendientes extends ArrayAdapter<SolicitudCita> implements OnTaskCompleted {
    //Contenxto de la aplicacion que relaciona al ListView y el Adpater
    private Context context;
    private static final int ACEPTAR_CITA = 2;
    private static final int NEGAR_CITA = 1;

    /**
     * Inicializacion
     *
     * @param context Contexto de la App desde la que se invoca
     * @param items   //Coleccion de objetos a presentar
     */
    public ListAdapterSolicitudesPendientes(Context context, ArrayList<SolicitudCita> items) {
        super(context, R.layout.item_cita_pendiente, items);
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
            view = inflater.inflate(R.layout.item_cita_pendiente, null);


        }

        //Objeto Solicitud Pendientes a visualizar segun position
        final SolicitudCita item = getItem(position);
        if (item != null) {

            Button aceptar = (Button) view.findViewById(R.id.Iaceptar);
            // Capture button clicks
            aceptar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    SharedPreferences miPreferencia = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = miPreferencia.edit();
                    editor.putString("idCita", item.getCodigo()+"");
                    editor.commit();
                    confirmarAceptar();
                   }
            });

            Button negar = (Button) view.findViewById(R.id.Inegar);
            // Capture button clicks
            negar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    SharedPreferences miPreferencia = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = miPreferencia.edit();
                    editor.putString("idCita", item.getCodigo()+"");
                    editor.commit();
                    confirmarNegar();
                }
            });
            // Recupera los elementos de vista y setea en funcion de valores de objeto
            TextView tutor = (TextView) view.findViewById(R.id.Itutor);
            TextView materia = (TextView) view.findViewById(R.id.Imateria);
            TextView fecha = (TextView) view.findViewById(R.id.Ifecha);
            TextView horario = (TextView) view.findViewById(R.id.Ihoraio);
            TextView lugar = (TextView) view.findViewById(R.id.Ilugar);
            TextView precio = (TextView) view.findViewById(R.id.Iprecio);

            if (tutor != null) {
                tutor.setText("Est: "+item.getEstudiante().getEst_nombre()+" "+item.getEstudiante().getEst_apellido());
                materia.setText("CI.: "+item.getEstudiante().getEst_cedula()+"");
                horario.setText("Horario: "+item.getHorarios().getHorario()+"");
                lugar.setText("Lugar: "+item.getLugarNivelaciones().getLugar()+"");
                precio.setText("Precio: "+item.getHorarios().getPrecio()+"");
                fecha.setText("Fecha: 25/01/18");
            }
        }
        return view;
    }

    private void confirmarAceptar() {
        String pregunta = "Esto seguro de aceptar la cita";
        new android.app.AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.msj_confirmacion))
                .setMessage(pregunta)
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(context.getResources().getString(R.string.lbl_aceptar),
                        new DialogInterface.OnClickListener() {//un listener que al pulsar, solicite el WS de Transsaccion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                guardarG();
                            }
                        })
                .show();
    }
    private void confirmarNegar() {
        String pregunta = "Esto seguro de negar la cita";
        new android.app.AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.msj_confirmacion))
                .setMessage(pregunta)
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(context.getResources().getString(R.string.lbl_aceptar),
                        new DialogInterface.OnClickListener() {//un listener que al pulsar, solicite el WS de Transsaccion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                guardarN();
                            }
                        })
                .show();
    }
    private void guardarG(){
        SharedPreferences miPreferencia = context.getSharedPreferences("MisPreferencias", context.MODE_PRIVATE);
        String idCita = miPreferencia.getString("idCita","No de pudo obtener");
        String idUsuario = miPreferencia.getString("idUsuario","No de pudo obtener");
        try {
            String URL = Util.URL_SRV + "tutor/aceptado";
            ClienteRest clienteRest = new ClienteRest(this);
           // Util.showMensaje(context, idCita);

            clienteRest.doGet(URL, "?id="+idCita+"&acep=1",ACEPTAR_CITA,true );
        }catch(Exception e){
            Util.showMensaje(context, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }
    private void guardarN(){
        SharedPreferences miPreferencia = context.getSharedPreferences("MisPreferencias", context.MODE_PRIVATE);
        String idCita = miPreferencia.getString("idCita","No de pudo obtener");
        String idUsuario = miPreferencia.getString("idUsuario","No de pudo obtener");
        try {
            String URL = Util.URL_SRV + "tutor/aceptado";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "?id="+idCita+"&acep=-1",NEGAR_CITA,true );
        }catch(Exception e){
            Util.showMensaje(context, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        switch (idSolicitud){
            case ACEPTAR_CITA:
                if(result!=null){
                    try {
                        Respuesta res = ClienteRest.getResult(result, Respuesta.class);
                        Util.showMensaje(context, res.getMensaje());
                        if (res.getCodigo() == 1) {
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Util.showMensaje(context,R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(context,R.string.msj_error_clienrest);
                break;
            case NEGAR_CITA:
                if(result!=null){
                    try {
                        Respuesta res = ClienteRest.getResult(result, Respuesta.class);
                        Util.showMensaje(context, res.getMensaje());
                        if (res.getCodigo() == 1) {
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Util.showMensaje(context,R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(context,R.string.msj_error_clienrest);
                break;
            default:
                break;
        }

    }


}
