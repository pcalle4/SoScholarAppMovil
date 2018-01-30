package com.example.toshiba.proyecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.toshiba.proyecto.modelo.Estudiante;
import com.example.toshiba.proyecto.modelo.Respuesta;
import com.example.toshiba.proyecto.utilidades.ClienteRest;
import com.example.toshiba.proyecto.utilidades.OnTaskCompleted;
import com.example.toshiba.proyecto.utilidades.Util;

public class regis_estActivity extends AppCompatActivity implements View.OnClickListener, OnTaskCompleted {

    private static final int SOLICITUD_GUARDAR_ESTUDIANTES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_est);

        // Locate the button in activity_main.xml
        Button button = (Button) findViewById(R.id.btn_canc_est);

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(regis_estActivity.this,
                        LoginActivity.class);
                startActivity(myIntent);
            }
        });

        ((Button) findViewById(R.id.btn_reg_est)).setOnClickListener(this);
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        switch (idSolicitud){
            case SOLICITUD_GUARDAR_ESTUDIANTES:
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
    /**
     * Procedimiento para validar y confirmar la transaccion,
     */
    private void confirmarGuardarUsr() {
        String pregunta = "Esto seguro de realizar el registro?";
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.msj_confirmacion))
                .setMessage(pregunta)
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(getResources().getString(R.string.lbl_aceptar),
                        new DialogInterface.OnClickListener() {//un listener que al pulsar, solicite el WS de Transsaccion
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                guardarUsr();
                            }
                        })
                .show();
    }
    private void guardarUsr(){
        String nombres = ((EditText) findViewById(R.id.usrNombres)).getText().toString();
        String apellidos = ((EditText) findViewById(R.id.usrApellidos)).getText().toString();
        String cedula = ((EditText) findViewById(R.id.usrCedula)).getText().toString();
        String telefono = ((EditText) findViewById(R.id.usrTelefono)).getText().toString();
        String email = ((EditText) findViewById(R.id.usrEmail)).getText().toString();
        String pasword = ((EditText) findViewById(R.id.usrPass)).getText().toString();

        try {
            String URL = Util.URL_SRV + "estudiante/guardar";
            ClienteRest clienteRest = new ClienteRest(this);
            Estudiante estudiante = new Estudiante();
            estudiante.setEst_nombre(nombres);
            estudiante.setEst_apellido(apellidos);
            estudiante.setEst_cedula(cedula);
            estudiante.setEst_telefono(telefono);
            estudiante.setEst_mail(email);
            estudiante.setEst_contrasena(pasword);
            clienteRest.doPost(URL, estudiante, SOLICITUD_GUARDAR_ESTUDIANTES, true);
        }catch(Exception e){
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_reg_est:
                confirmarGuardarUsr();
                break;
            default:
                break;
        }
    }
}
