package com.example.toshiba.proyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.toshiba.proyecto.modelo.Calificacion;
import com.example.toshiba.proyecto.utilidades.ClienteRest;
import com.example.toshiba.proyecto.utilidades.OnTaskCompleted;
import com.example.toshiba.proyecto.utilidades.Util;

import java.util.ArrayList;
import java.util.List;

public class ComentariosActivity extends AppCompatActivity implements OnTaskCompleted {

    private static final int SOLICITUD_CALIFICACION = 1;
    private ListAdapterComentarios listAdapterComentarios;
    //private Context globalContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
    }


    @Override
    public void onResume() {
        super.onResume();
        consultaCalificacion();
    }

    protected void consultaCalificacion() {
        try {
            String URL = Util.URL_SRV + "tutor/comentarios";
            ClienteRest clienteRest = new ClienteRest(this);

            SharedPreferences miPreferencia = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            String idUsuario = miPreferencia.getString("idUsuario","No de pudo obtener");

            clienteRest.doGet(URL, "?id="+idUsuario, SOLICITUD_CALIFICACION, true);


        }catch(Exception e){
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        Log.i("MainActivity", "" + result);
        switch (idSolicitud){
            case SOLICITUD_CALIFICACION:
                if(result!=null){
                    try {

                       List<Calificacion> cal = ClienteRest.getResults(result, Calificacion.class);
                        mostrarCalificacion(cal);
                    }catch (Exception e){
                        Log.i("MainActivity", "Error en carga de categorias", e);
                        Util.showMensaje(this, R.string.msj_error_clienrest_formato);
                    }
                }else
                    Util.showMensaje(this, R.string.msj_error_clienrest);
                break;
            default:
                break;
        }

    }


    public void mostrarCalificacion(List<Calificacion> list){
        ListView lista = (ListView) findViewById(R.id.list_comentarios);
        listAdapterComentarios = new ListAdapterComentarios(this, new ArrayList<Calificacion>(list));
        lista.setAdapter(listAdapterComentarios);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // mostrarTutor(position);
            }
        });
    }


}
