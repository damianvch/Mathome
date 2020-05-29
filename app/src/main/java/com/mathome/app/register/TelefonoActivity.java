package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mathome.app.R;
import com.mathome.app.interfaces.LoginActivity;
import com.mathome.app.interfaces.PaisActivity;
import com.mathome.app.security.Token;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class TelefonoActivity extends AppCompatActivity {

    private AsyncHttpClient cliente;
    Token token = new Token();

    EditText txtTelefono;
    Button btnTelefono;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefono);
        cliente = new AsyncHttpClient();
        txtTelefono = findViewById(R.id.txtTelefono);
        btnTelefono = findViewById(R.id.btnTelefono);
        ObtenerPais();
    }

    public void Clave(View view){
        if(validar()){
            verificarTelefono();
        }
    }

    public void Paises(View view){
        Intent paises = new Intent(this, PaisActivity.class);
        startActivity(paises);
        overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }

    public void Correo(View view){
        SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = preferences.edit();
        obj_editor.putString("tipo", "correo");
        obj_editor.commit();
        Intent correo = new Intent(this, CorreoActivity.class);
        startActivity(correo);
        finish();
    }

    public void Login(View view){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    public void ObtenerPais(){
        SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
        btnTelefono.setText(preferences.getString("codigoISO2","")+" ("+preferences.getString("prefijoTelef","")+")");
    }

    public boolean validar(){
        SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);

        boolean retorno = true;

        String c1 = txtTelefono.getText().toString().trim();
        String idPais = preferences.getString("idPais","").trim();

        if(c1.isEmpty()){
            txtTelefono.setError(getString(R.string.error_telefono));
            retorno = false;
        }

        if(idPais.isEmpty()){
            Toast.makeText(this, "Debe seleccionar su País", Toast.LENGTH_SHORT).show();
            retorno = false;
        }

        return retorno;
    }

    private void verificarTelefono(){
        String url = "http://mathome.me/api/service/verify/phone.php?";
        //String url = "http://192.168.1.52/api-mathome/service/verify/phone.php?";
        String phone = txtTelefono.getText().toString().replace(" ","%20");
        String requestToken = "token="+token.getToken()+"&phone="+phone;

        cliente.post(url + requestToken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String respuesta = new String(responseBody);

                    if(respuesta.equalsIgnoreCase("Exist")){
                        txtTelefono.setError(getString(R.string.error_telefono_exist));
                    }else if(respuesta.equalsIgnoreCase("Successful")){
                        SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
                        SharedPreferences.Editor obj_editor = preferences.edit();
                        obj_editor.putString("telefono", txtTelefono.getText().toString().replace(" ","%20"));
                        obj_editor.putString("tipo", "telefono");
                        obj_editor.commit();

                        Intent clave = new Intent(TelefonoActivity.this, ClaveActivity.class);
                        startActivity(clave);
                        overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    }
                }else{
                    Toast.makeText(TelefonoActivity.this, "Algo salió mal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
