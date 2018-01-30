package com.example.toshiba.proyecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.toshiba.proyecto.modelo.SolicitudCita;
import com.example.toshiba.proyecto.utilidades.ClienteRest;
import com.example.toshiba.proyecto.utilidades.OnTaskCompleted;
import com.example.toshiba.proyecto.utilidades.Util;

import java.util.ArrayList;
import java.util.List;

public class MisSolicitudesFragment extends Fragment implements OnTaskCompleted {
    private static final int SOLICITUD_CITAS = 1;
    private static final int SOLICITUD_EMPRESA = 2;
   private ListAdapterSolicitudes listAdapterSolicitudes;
    private Context globalContext = null;



    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        getActivity().getApplicationContext();
        globalContext=this.getActivity();

    }



    @Override
    public void onResume() {
        super.onResume();
        consultaListaSol();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Realiza la llamada al WS para consultar el listado de Solicitudes de Estudiantes
     */
    protected void consultaListaSol() {
        try {
          //  http://localhost:8080/miSiremo/srv/login/administrador?usuario=Admin&pass=1234
            //http://localhost:8080/miSiremo/srv/empresa/listaempresas
            String URL = Util.URL_SRV + "estudiante/listasol";
            ClienteRest clienteRest = new ClienteRest(this);
            SharedPreferences miPreferencia = this.getActivity().getSharedPreferences("MisPreferencias", globalContext.MODE_PRIVATE);
            String idUsuario = miPreferencia.getString("idUsuario","No de pudo obtener");
            clienteRest.doGet(URL, "?id="+idUsuario, SOLICITUD_CITAS, true);
        }catch(Exception e){
            Util.showMensaje(globalContext, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }
    /**
     * Realiza la llamada al WS para consultar el listado de Categorias
     */
    protected void consultaEmpresa(int id) {
        try {
            String URL = Util.URL_SRV + "empresa/verempresa";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "?id="+id, SOLICITUD_EMPRESA, true);
        }catch(Exception e){
            Util.showMensaje(globalContext, R.string.msj_error_clienrest);
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mis_solicitudes, container, false);
    }

    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        Log.i("MainActivity", "" + result);
        switch (idSolicitud){
            case SOLICITUD_CITAS:
                if(result!=null){
                    try {

                        List<SolicitudCita> sol = ClienteRest.getResults(result, SolicitudCita.class);
                        mostrarCitas(sol);
//                        Util.showMensaje(globalContext, "Hola "+sol.get(0).getTutor().getTut_apellido() +"<<");
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

    /**
     * Muetra el Solicitud Cita
     */
    public void mostrarCitas(List<SolicitudCita> list){
        ListView lista = (ListView) getView().findViewById(R.id.list_solicitudes);
        listAdapterSolicitudes = new ListAdapterSolicitudes(globalContext, new ArrayList<SolicitudCita>(list));
        lista.setAdapter(listAdapterSolicitudes);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                mostrarEmpresa(position);
            }
        });
    }

    private void mostrarEmpresa(int position){
     //   SolicitudCita emp = listAdapterSolicitudes.getItem(position);
     //   consultaEmpresa(emp.getCodigo());
    }

    private void llamarCrearCategoria(){
       // Intent intent = new Intent(this, CategoriaActivity.class);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //cierra cola de actividades
       // startActivity(intent);
    }
}
