package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.mathome.app.R;
import com.loopj.android.http.*;
import com.mathome.app.entity.Nivel;
import com.mathome.app.interfaces.LoginActivity;
import com.mathome.app.security.Token;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NivelActivity extends AppCompatActivity {

    private AsyncHttpClient cliente;
    private Spinner spinnerNivel;

    Token token = new Token();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel);
        cliente = new AsyncHttpClient();
        spinnerNivel = (Spinner) findViewById(R.id.spNivel);
        llenarSpinner();
        VerificarRed();
    }

    private boolean VerificarRed(){
        boolean estado = true;
        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        Boolean mobile = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(!wifi && !mobile){
            Toast.makeText(getApplicationContext(),R.string.conexion,Toast.LENGTH_LONG).show();
            estado = false;
        }

        return estado;
    }

    private void llenarSpinner(){
        String url = "http://mathome.me/api/service/get/level.php?";
        //String url = "http://192.168.1.52/api-mathome/service/get/level.php?";
        String requestToken = "token="+token.getToken();
        ArrayList<Nivel> lista = new ArrayList<Nivel>();
        Nivel n0 = new Nivel();
        n0.setNivel("SELECCIONAR");
        lista.add(n0);
        ArrayAdapter<Nivel> a = new ArrayAdapter<Nivel>(this,android.R.layout.simple_dropdown_item_1line,lista);
        spinnerNivel.setAdapter(a);

        cliente.post(url + requestToken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    cargarSpinner(new String(responseBody));
                }else{
                    Toast.makeText(NivelActivity.this, "Algo salió mal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private  void cargarSpinner(String respuesta){
        ArrayList<Nivel> lista = new ArrayList<Nivel>();
        //List<Nivel> lista = new ArrayList<Nivel>();
        Nivel n1 = new Nivel();
        n1.setNivel("SELECCIONAR");
        lista.add(n1);
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);

            for (int i=0; i < jsonArreglo.length();i++){
                Nivel n2 = new Nivel();
                n2.setNivel(jsonArreglo.getJSONObject(i).getString("nivel"));
                n2.setId(jsonArreglo.getJSONObject(i).getInt("idNivel"));
                lista.add(n2);
            }

            ArrayAdapter<Nivel> a = new ArrayAdapter<Nivel>(this,android.R.layout.simple_dropdown_item_1line,lista);

            spinnerNivel.setAdapter(a);
        }catch (Exception e){
            Toast.makeText(NivelActivity.this, "Error: "+e.toString(), Toast.LENGTH_LONG).show();
            // e.printStackTrace();
        }
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnSiguienteR1:
                if(validar()){
                    SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
                    SharedPreferences.Editor obj_editor = preferences.edit();
                    obj_editor.putString("nivel", spinnerNivel.getSelectedItem().toString());
                    obj_editor.commit();

                    Intent nombre_apellido = new Intent(this, NombreActivity.class);
                    startActivity(nombre_apellido);
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                }
                break;
            case R.id.lblEmpezarLoginR1:
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                finish();
                break;
        }
    }


    public boolean validar(){

        boolean retorno = true;

        String sn = spinnerNivel.getSelectedItem().toString();

        if(sn.isEmpty()){
            Toast.makeText(NivelActivity.this, R.string.error_nivel_estudio, Toast.LENGTH_LONG).show();
            retorno = false;
        }

        if(sn.equalsIgnoreCase("SELECCIONAR")){
            Toast.makeText(NivelActivity.this, R.string.error_nivel_estudio, Toast.LENGTH_LONG).show();
            retorno = false;
        }

        return retorno;
    }

}
