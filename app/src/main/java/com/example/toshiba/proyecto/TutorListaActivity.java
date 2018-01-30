package com.example.toshiba.proyecto;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.toshiba.proyecto.modelo.Tutor;
import com.example.toshiba.proyecto.utilidades.ClienteRest;
import com.example.toshiba.proyecto.utilidades.OnTaskCompleted;
import com.example.toshiba.proyecto.utilidades.Util;

import java.util.ArrayList;
import java.util.List;

public class TutorListaActivity extends AppCompatActivity implements OnTaskCompleted {

    private static final int SOLICITUD_TUTOR = 1;
    private ListAdapterTutorList listAdapterTutorList;
    //private Context globalContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_lista);

    }

    @Override
    public void onResume() {
        super.onResume();
        consultaListaSol();
    }





    protected void consultaListaSol() {
        try {
            //  http://localhost:8080/miSiremo/srv/login/administrador?usuario=Admin&pass=1234
            //http://localhost:8080/miSiremo/srv/empresa/listaempresas
            String URL = Util.URL_SRV + "tutor/listadotut";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "", SOLICITUD_TUTOR, true);
        }catch(Exception e){
            Util.showMensaje(this, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        Log.i("MainActivity", "" + result);
        switch (idSolicitud){
            case SOLICITUD_TUTOR:
                if(result!=null){
                    try {

                        List<Tutor> tut = ClienteRest.getResults(result, Tutor.class);
                        mostrarTutores(tut);
//                        Util.showMensaje(globalContext, "Hola "+sol.get(0).getTutor().getTut_apellido() +"<<");
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

    public void mostrarTutores(List<Tutor> list){
        ListView lista = (ListView) findViewById(R.id.list_tut_list_ds);
        listAdapterTutorList = new ListAdapterTutorList(this, new ArrayList <Tutor>(list));
        lista.setAdapter(listAdapterTutorList);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // mostrarTutor(position);
            }
        });
    }
}
