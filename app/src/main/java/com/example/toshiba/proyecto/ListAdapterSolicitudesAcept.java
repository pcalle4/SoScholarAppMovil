package com.example.toshiba.proyecto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class ListAdapterSolicitudesAcept extends ArrayAdapter<SolicitudCita> implements OnTaskCompleted {
    //Contenxto de la aplicacion que relaciona al ListView y el Adpater
    private Context context;
    private static final int CALIFICAR_CITA = 1;
    Spinner puntos;
    TextView comentario;

    /**
     * Inicializacion
     *
     * @param context Contexto de la App desde la que se invoca
     * @param items   //Coleccion de objetos a presentar
     */
    public ListAdapterSolicitudesAcept(Context context, ArrayList<SolicitudCita> items) {
        super(context, R.layout.item_citas_acept, items);
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
            view = inflater.inflate(R.layout.item_citas_acept, null);
        }

        //Objeto Solicitud Aceptadas a visualizar segun position
        final SolicitudCita item = getItem(position);
        if (item != null) {

            Button calificar = (Button) view.findViewById(R.id.tut_calificacion);
            // Capture button clicks
            calificar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    SharedPreferences miPreferencia = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = miPreferencia.edit();
                    editor.putString("idCita", item.getCodigo()+"");
                    editor.commit();
                    showInputDialog();
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

    protected void showInputDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View promptView = layoutInflater.inflate(R.layout.dialog_calificar, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        puntos =(Spinner) promptView.findViewById(R.id.cal_puntuacion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.puntos, R.layout.support_simple_spinner_dropdown_item);
        puntos.setAdapter(adapter);
        comentario = (TextView) promptView.findViewById(R.id.cal_cometario);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Guardar g=new Guardar(context);
                        confirmarGuardarCalificacion();

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }






    private void confirmarGuardarCalificacion() {
        String pregunta = "Esto seguro de guardar Calificacion";
        new android.app.AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.msj_confirmacion))
                .setMessage(pregunta)
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(context.getResources().getString(R.string.lbl_aceptar),
                        new DialogInterface.OnClickListener() {//un listener que al pulsar, solicite el WS de Transsaccion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                guardarCal();
                            }
                        })
                .show();
    }

    private void guardarCal(){
        SharedPreferences miPreferencia = context.getSharedPreferences("MisPreferencias", context.MODE_PRIVATE);
        String idCita = miPreferencia.getString("idCita","No de pudo obtener");
        String idUsuario = miPreferencia.getString("idUsuario","No de pudo obtener");
        String puntos = this.puntos.getSelectedItem().toString();
        String coment = this.comentario.getText().toString();

        try {
            String URL = Util.URL_SRV + "tutor/calificacion";
            ClienteRest clienteRest = new ClienteRest(this);
            Respuesta r=new Respuesta();
            r.setCodigo(2);
            r.setMensaje(coment);
            //Util.showMensaje(context, idCita);
            //Util.showMensaje(context, "?id="+idCita+"&idE="+idUsuario+"&msm="+coment+"&cal="+puntos);
            //clienteRest.doGet(URL, "?id="+idCita+"&idE="+idUsuario+"&cal="+puntos,CALIFICAR_CITA,true );
            clienteRest.doPost2(URL,r, "?id="+idCita+"&idE="+idUsuario+"&cal="+puntos,CALIFICAR_CITA,true );
        }catch(Exception e){
            Util.showMensaje(context, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        switch (idSolicitud){
            case CALIFICAR_CITA:
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
