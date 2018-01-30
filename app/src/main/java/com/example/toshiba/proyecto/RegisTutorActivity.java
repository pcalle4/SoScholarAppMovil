package com.example.toshiba.proyecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.toshiba.proyecto.modelo.Horarios;
import com.example.toshiba.proyecto.modelo.Respuesta;
import com.example.toshiba.proyecto.modelo.Tutor;
import com.example.toshiba.proyecto.utilidades.ClienteRest;
import com.example.toshiba.proyecto.utilidades.OnTaskCompleted;
import com.example.toshiba.proyecto.utilidades.Util;

public class RegisTutorActivity extends AppCompatActivity implements View.OnClickListener, OnTaskCompleted {

    private static final int SOLICITUD_GUARDAR_ADMINISTRADOR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_tutor);
        Button button = (Button) findViewById(R.id.btn_canc);

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(RegisTutorActivity.this,
                        LoginActivity.class);
                startActivity(myIntent);
            }
        });

        ((Button) findViewById(R.id.btn_reg)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_reg:
                confirmarGuardarAdmin();
                break;
            default:
                break;
        }
    }

    private void confirmarGuardarAdmin() {
        String pregunta = "Esto seguro de realizar el registro?";
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.msj_confirmacion))
                .setMessage(pregunta)
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(getResources().getString(R.string.lbl_aceptar),
                        new DialogInterface.OnClickListener() {//un listener que al pulsar, solicite el WS de Transsaccion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                guardarAdmin();
                            }
                        })
                .show();
    }
    private void guardarAdmin(){
        int id;
        try {
            //id = Integer.parseInt(((EditText) findViewById(R.id.admNombres)).getText().toString());
        }catch (Exception e){
            // Util.showMensaje(this, "Formato incorrecto en código, revisar...");
            //return;
        }
        String nombres = ((EditText) findViewById(R.id.tutNombres)).getText().toString();
        String apellidos = ((EditText) findViewById(R.id.tutApellidos)).getText().toString();
        String cedula = ((EditText) findViewById(R.id.tutCedula)).getText().toString();
        String telefono = ((EditText) findViewById(R.id.tutTelefono)).getText().toString();
        String area = ((EditText) findViewById(R.id.tutArea)).getText().toString();
        String especialidad = ((EditText) findViewById(R.id.tutEspecialidad)).getText().toString();
        String nivel = ((EditText) findViewById(R.id.tutNivel)).getText().toString();
        String email = ((EditText) findViewById(R.id.tutEmail)).getText().toString();
        String pasword = ((EditText) findViewById(R.id.tutPass)).getText().toString();

        try {
            String URL = Util.URL_SRV + "tutor/guardar";
            ClienteRest clienteRest = new ClienteRest(this);
            Tutor tutor = new Tutor();
            tutor.setTut_nombre(nombres);
            tutor.setTut_apellido(apellidos);
            tutor.setTut_cedula(cedula);
            tutor.setTut_telefono(telefono);
            tutor.setTut_area(area);
            tutor.setTut_especialidad(especialidad);
            tutor.setTut_anivel(nivel);
            tutor.setTut_mail(email);
            tutor.setTut_contrasena(pasword);

            clienteRest.doPost(URL, tutor, SOLICITUD_GUARDAR_ADMINISTRADOR, true);
        }catch(Exception e){
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        switch (idSolicitud){
            case SOLICITUD_GUARDAR_ADMINISTRADOR:
                if(result!=null){
                    try {
                        Respuesta res = ClienteRest.getResult(result, Respuesta.class);
                        Util.showMensaje(this, res.getMensaje());
                        if (res.getCodigo() == 1) {
                            finish();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Util.showMensaje(this,R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(this,R.string.msj_error_clienrest);
                break;
            default:
                break;
        }

    }
}
