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

import java.util.ArrayList;
import java.util.List;

public class NuevaSolicitudFragment extends Fragment implements OnTaskCompleted, View.OnClickListener {

    private static final int INGRESAR_SOLICITUD = 1;
    private static final int SOLICITUD_TUTORES = 2;
    private static final int SOLICITUD_HORARIOS = 3;
    private static final int SOLICITUD_LUGARES = 4;
    private Context globalContext = null;
    private Spinner tutor;
    Tutor tutoraux;
    private Spinner lugar;
    private Spinner horario;
    private List<Tutor> tutores;
    private List<Horarios> horarios;
    private List<LugarNivelacion> lugares;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getApplicationContext();
        globalContext=this.getActivity();
        tutoraux = new Tutor();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nueva_solicitud, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();

        consultaListas(); /*liskta tutores*/
        //lista lugares
        consultaListas2();
        // lista horarios
      // consultaListas3();

        ((Button) this.getActivity().findViewById(R.id.sol_guardar)).setOnClickListener(this);
        ((Button) this.getActivity().findViewById(R.id.sol_datosS)).setOnClickListener(this);
        ((Button) this.getActivity().findViewById(R.id.parche)).setOnClickListener(this);
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        switch (idSolicitud){
            case INGRESAR_SOLICITUD:
                if(result!=null){
                    try {
                        Respuesta res = ClienteRest.getResult(result, Respuesta.class);
                        Util.showMensaje(globalContext, res.getMensaje());
                        if (res.getCodigo() == 1) {
                            Util.showMensaje(globalContext,"REGISTRADO!!!");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Util.showMensaje(globalContext,R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(globalContext,R.string.msj_error_clienrest);
                break;
            case SOLICITUD_TUTORES:
                if(result!=null){
                    try {
                        List<Tutor> res =  ClienteRest.getResults(result, Tutor.class);
                        //Util.showMensaje(globalContext, res.get(0).getNombre()+"<<<<<");
                        tutores=res;
                        tutor = (Spinner) this.getActivity().findViewById(R.id.sol_tutor);
                        tutor.setAdapter(new ArrayAdapter<Tutor>(globalContext, android.R.layout.simple_spinner_item, tutores));

                    }catch (Exception e){
                        Log.i("MainActivity", "Error en carga de categorias", e);
                        Util.showMensaje(globalContext, R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(globalContext, R.string.msj_error_clienrest);
                break;
            case SOLICITUD_LUGARES:
                if(result!=null){
                    try {
                        List<LugarNivelacion> res =  ClienteRest.getResults(result, LugarNivelacion.class);
                        //Util.showMensaje(globalContext, res.get(0).getNombre()+"<<<<<");
                        lugares=res;
                        lugar = (Spinner) this.getActivity().findViewById(R.id.sol_lugar);
                        lugar.setAdapter(new ArrayAdapter<LugarNivelacion>(globalContext, android.R.layout.simple_spinner_item, lugares));
                    }catch (Exception e){
                        Log.i("MainActivity", "Error en carga de categorias", e);
                        Util.showMensaje(globalContext, R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(globalContext, R.string.msj_error_clienrest);
                break;
            case SOLICITUD_HORARIOS:
                if(result!=null){
                    try {



                        List<Horarios> res=new ArrayList<>();
                        res =  ClienteRest.getResults(result, Horarios.class);
                        //Util.showMensaje(globalContext, res.get(0).getNombre()+"<<<<<");

                        horarios=res;
                        horario = (Spinner) this.getActivity().findViewById(R.id.sol_horario);
                        horario.setAdapter(new ArrayAdapter<Horarios>(globalContext, android.R.layout.simple_spinner_item, horarios));

                    }catch (Exception e){
                        Log.i("MainActivity", "Error en carga de categorias", e);
                        Util.showMensaje(globalContext, R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(globalContext, R.string.msj_error_clienrest);
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
                                try{
                                guardarSol();
                                }catch(Exception e){
                                    Util.showMensaje(globalContext, R.string.msj_error_clienrest1);
                                    e.printStackTrace();
                                }
                            }
                        })
                .show();
    }


    private void guardarSol(){
        SolicitudCita solicitudCita =new SolicitudCita();
        Horarios horarios= (Horarios) horario.getSelectedItem();
        LugarNivelacion lugarNivelacion= (LugarNivelacion) lugar.getSelectedItem();
        Tutor tutor = (Tutor) this.tutor.getSelectedItem();

        solicitudCita.setHorarios(horarios);
        solicitudCita.setLugarNivelaciones(lugarNivelacion);
        solicitudCita.setTutor(tutor);
        solicitudCita.setAceptado("0");

        String fecha = ((EditText) this.getActivity().findViewById(R.id.sol_fecha)).getText().toString();

        try {
            String URL = Util.URL_SRV + "estudiante/newSolicitud";
            ClienteRest clienteRest = new ClienteRest(this);
            SharedPreferences miPreferencia = this.getActivity().getSharedPreferences("MisPreferencias", globalContext.MODE_PRIVATE);
            String idUsuario = miPreferencia.getString("idUsuario","No de pudo obtener");
            clienteRest.doPost2(URL, solicitudCita,"?id="+idUsuario + "&fecha="+fecha , INGRESAR_SOLICITUD, true);
        }catch(Exception e){
            Util.showMensaje(globalContext, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sol_guardar:
                confirmarGuardarEmp();
                break;
            case R.id.sol_datosS:
                try{
                Tutor tutor = (Tutor) this.tutor.getSelectedItem();
                Horarios horarios= (Horarios) horario.getSelectedItem();
                ((TextView) this.getActivity().findViewById(R.id.sol_mateira)).setText(""+tutor.getTut_area());
                ((TextView) this.getActivity().findViewById(R.id.sol_precio)).setText(""+horarios.getPrecio());
                }catch (Exception e){
                    Util.showMensaje(globalContext, R.string.msj_error_clienrest2);
                    e.printStackTrace();
                }
                break;
            case R.id.parche:
                Tutor tutor1 = (Tutor) this.tutor.getSelectedItem();
                tutoraux= (Tutor) tutor1;

              // System.out.println(tutor1.getTut_apellido());

                consultaListas3();
                break;

            default:
                break;
        }
    }




    protected void consultaListas() {
        try {
            String URL = Util.URL_SRV + "tutor/listadotut";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "", SOLICITUD_TUTORES, true);

           /* String URL1 = Util.URL_SRV + "lugar/listalug";
            clienteRest.doGet(URL1, "", SOLICITUD_LUGARES, true);

            String URL2 = Util.URL_SRV + "lugar/listahor";
            clienteRest.doGet(URL2, "", SOLICITUD_HORARIOS, true);*/

        }catch(Exception e){
            Util.showMensaje(globalContext, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }
    protected void consultaListas2() {
        try {
           /* String URL = Util.URL_SRV + "tutor/listadotut";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "", SOLICITUD_TUTORES, true);*/

            String URL1 = Util.URL_SRV + "lugar/listalug";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL1, "", SOLICITUD_LUGARES, true);

           /* String URL2 = Util.URL_SRV + "lugar/listahor";
             ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL2, "", SOLICITUD_HORARIOS, true);*/

        }catch(Exception e){
            Util.showMensaje(globalContext, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }
    protected void consultaListas3() {
        try {
           /* String URL = Util.URL_SRV + "tutor/listadotut";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "", SOLICITUD_TUTORES, true);

            String URL1 = Util.URL_SRV + "lugar/listalug";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL1, "", SOLICITUD_LUGARES, true);*/


            String URL2 = Util.URL_SRV + "tutor/listatutorid";
             ClienteRest clienteRest = new ClienteRest(this);

             SharedPreferences miPreferencia = this.getActivity().getSharedPreferences("MisPreferencias", globalContext.MODE_PRIVATE);
            String idUsuario = miPreferencia.getString("idUsuario","No de pudo obtener");


            clienteRest.doGet(URL2, "?id="+tutoraux.getTut_id(), SOLICITUD_HORARIOS, true);

        }catch(Exception e){
            Util.showMensaje(globalContext, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }


}
