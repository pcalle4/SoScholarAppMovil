package com.example.toshiba.proyecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.toshiba.proyecto.modelo.Estudiante;
import com.example.toshiba.proyecto.modelo.Tutor;
import com.example.toshiba.proyecto.utilidades.ClienteRest;
import com.example.toshiba.proyecto.utilidades.OnTaskCompleted;
import com.example.toshiba.proyecto.utilidades.Util;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnTaskCompleted {
    private static final int SOLICITUD_ESTUDIANTE = 1;
    private static final int SOLICITUD_TUTOR = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Locate the button in activity_main.xml
        Button button = (Button) findViewById(R.id.id_reg);

        // Locate the button in activity_main.xml
        //  Button button2 = (Button) findViewById(R.id.id_iniciar);
        ((Button) findViewById(R.id.id_iniciar)).setOnClickListener(this);

        // Locate the button in activity_main.xml
        Button button3 = (Button) findViewById(R.id.id_regUsr);

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(LoginActivity.this,
                        RegisTutorActivity.class);
                startActivity(myIntent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(LoginActivity.this,
                        regis_estActivity.class);
                startActivity(myIntent);
            }
        });
       /* button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(LoginActivity.this,
                        EmpresasActivity.class);
                startActivity(myIntent);
            }
        });*/
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_iniciar:
                consultaUsuario();
                break;
            default:
                break;
        }
    }
    /**
     * Comprueba credencuales de Tutor
     */
    protected void consultaTutor() {
        try {
            String usuario = ((EditText) findViewById(R.id.loginNu)).getText().toString();
            String pass = ((EditText) findViewById(R.id.loginPss)).getText().toString();
            String URL = Util.URL_SRV + "tutor/login";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "?correo=" + usuario + "&pass=" + pass, SOLICITUD_TUTOR, true);
        } catch (Exception e) {
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }
    /**
     * Comprueba credencuales de Estudiante
     */

    protected void consultaUsuario() {
        try {
            String usuario = ((EditText) findViewById(R.id.loginNu)).getText().toString();
            String pass = ((EditText) findViewById(R.id.loginPss)).getText().toString();
            String URL = Util.URL_SRV + "estudiante/login";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "?correo=" + usuario + "&pass=" + pass, SOLICITUD_ESTUDIANTE, true);
        } catch (Exception e) {
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        Log.i("MainActivity", "" + result);
        switch (idSolicitud) {
            case SOLICITUD_ESTUDIANTE:
                if (result != null) {
                    try {
                        Estudiante est = ClienteRest.getResult(result, Estudiante.class);
                        if (est.getEst_nombre() != null) {
                            Util.showMensaje(this, "Hola " + est.getEst_nombre());
                            //aqui va a guardarse con el sharedPreference los datos del usuario.
                            SharedPreferences miPreferencia = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = miPreferencia.edit();
                            editor.putString("idUsuario", est.getEst_id() + "");
                            editor.commit();
                            Intent myIntent = new Intent(LoginActivity.this, EstudianteActivity.class);
                            startActivity(myIntent);
                        } else {
                            consultaTutor();

                        }

                    } catch (Exception e) {
                        Log.i("MainActivity", "Error en carga de Usuarios", e);
                        Util.showMensaje(this, R.string.msj_error_clienrest_formato);
                    }
                } else
                    Util.showMensaje(this, R.string.msj_error_clienrest);
                break;
            case SOLICITUD_TUTOR:
                if (result != null) {
                    try {
                        Tutor tutor =  ClienteRest.getResult(result, Tutor.class);
                        if(tutor.getTut_nombre()!=null) {
                            Util.showMensaje(this, "Hola " + tutor.getTut_nombre());
                            //aqui va a guardarse con el sharedPreference los datos del usuario.
                            SharedPreferences miPreferencia = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = miPreferencia.edit();
                            editor.putString("idUsuario", tutor.getTut_id()+"");
                            editor.commit();
                            Intent myIntent = new Intent(LoginActivity.this,TutorActivity.class);
                            startActivity(myIntent);
                        }else{
                            Util.showMensaje(this, "Datos Incorrectos");
                        }

                    }catch (Exception e){
                        Log.i("MainActivity", "Error en carga de Usuarios", e);
                        Util.showMensaje(this, R.string.msj_error_clienrest_formato);
                    }
                } else
                    Util.showMensaje(this, R.string.msj_error_clienrest);
                break;
            default:
                break;
        }

    }
}