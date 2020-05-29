package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mathome.app.R;
import com.mathome.app.dto.RegistroUsuarioDTO;
import com.mathome.app.entity.Nivel;
import com.mathome.app.interfaces.LoginActivity;
import com.mathome.app.security.Token;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class EmpezarActivity extends AppCompatActivity {

    Token token = new Token();
    private AsyncHttpClient cliente;

    RegistroUsuarioDTO rudto = new RegistroUsuarioDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empezar);
        cliente = new AsyncHttpClient();
        ObtenerLocalizacion();
        SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = preferences.edit();
        obj_editor.putString("tipo", "correo");
        obj_editor.commit();
    }

    private boolean VerificarRed(){
        boolean estado = true;
        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        Boolean mobile = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(!wifi && !mobile){
            Toast.makeText(this, R.string.conexion, Toast.LENGTH_SHORT).show();
            estado = false;
        }
        return estado;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnEmpezar:
                if(VerificarRed()){
                    Intent nivel = new Intent(this, NivelActivity.class);
                    startActivity(nivel);
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

    public void ObtenerLocalizacion(){
        String url = "http://mathome.me/api/service/get/location.php?";
        String requestToken = "token="+token.getToken();
        cliente.post(url + requestToken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    try {
                        JSONArray jsonArreglo = new JSONArray(new String(responseBody));
                        for (int i=0; i < jsonArreglo.length();i++){
                            SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
                            SharedPreferences.Editor obj_editor = preferences.edit();
                            obj_editor.putString("idPais", jsonArreglo.getJSONObject(i).getString("idPais"));
                            obj_editor.putString("codigoISO2", jsonArreglo.getJSONObject(i).getString("codigoISO2"));
                            obj_editor.putString("prefijoTelef", jsonArreglo.getJSONObject(i).getString("prefijoTelef"));
                            obj_editor.commit();
                        }

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Algo salió mal", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
