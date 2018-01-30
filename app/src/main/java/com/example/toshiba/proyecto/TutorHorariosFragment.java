package com.example.toshiba.proyecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.toshiba.proyecto.modelo.Horarios;
import com.example.toshiba.proyecto.modelo.LugarNivelacion;
import com.example.toshiba.proyecto.modelo.Respuesta;
import com.example.toshiba.proyecto.modelo.SolicitudCita;
import com.example.toshiba.proyecto.modelo.Tutor;
import com.example.toshiba.proyecto.utilidades.ClienteRest;
import com.example.toshiba.proyecto.utilidades.OnTaskCompleted;
import com.example.toshiba.proyecto.utilidades.Util;

import java.util.List;

public class TutorHorariosFragment extends Fragment implements OnTaskCompleted, View.OnClickListener {

    private static final int INGRESAR_HORARIO = 1;
    private Context globalContext = null;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getApplicationContext();
        globalContext=this.getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutor_horarios, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();

        ((Button) this.getActivity().findViewById(R.id.hor_guardar)).setOnClickListener(this);

    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        switch (idSolicitud){
            case INGRESAR_HORARIO:
                if(result!=null){
                    try {
                        Respuesta res = ClienteRest.getResult(result, Respuesta.class);
                        Util.showMensaje(globalContext, res.getMensaje());
                        if (res.getCodigo() == 1) {
                            ((EditText) this.getActivity().findViewById(R.id.hor_precio)).setText("");
                            ((EditText) this.getActivity().findViewById(R.id.hor_horario)).setText("");
                           // Util.showMensaje(globalContext,"REGISTRADO!!!");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Util.showMensaje(globalContext,R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(globalContext,R.string.msj_error_clienrest);
                break;
            default:
                break;
        }

    }
    private void confirmarGuardarEmp() {
        String pregunta = "Esto seguro de realizar el registro?";
        new AlertDialog.Builder(globalContext)
                .setTitle(getResources().getString(R.string.msj_confirmacion))
                .setMessage(pregunta)
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(getResources().getString(R.string.lbl_aceptar),
                        new DialogInterface.OnClickListener() {//un listener que al pulsar, solicite el WS de Transsaccion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                guardarHor();
                            }
                        })
                .show();
    }

    private void guardarHor(){

        Horarios horarios = new Horarios();
        String horario = ((EditText) this.getActivity().findViewById(R.id.hor_horario)).getText().toString();
        String precio = ((EditText) this.getActivity().findViewById(R.id.hor_precio)).getText().toString();
        horarios.setHorario(horario);
        horarios.setPrecio(Double.parseDouble(precio));

        try {
            String URL = Util.URL_SRV + "tutor/newHorario";
            ClienteRest clienteRest = new ClienteRest(this);
            SharedPreferences miPreferencia = this.getActivity().getSharedPreferences("MisPreferencias", globalContext.MODE_PRIVATE);
            String idUsuario = miPreferencia.getString("idUsuario","No de pudo obtener");
            clienteRest.doPost2(URL, horarios,"?id="+idUsuario , INGRESAR_HORARIO, true);
        }catch(Exception e){
            Util.showMensaje(globalContext, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hor_guardar:
                confirmarGuardarEmp();
                break;
        }
    }
}
