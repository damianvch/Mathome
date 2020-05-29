package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mathome.app.R;
import com.mathome.app.interfaces.LoginActivity;
import com.mathome.app.security.Token;

import cz.msebera.android.httpclient.Header;

public class CorreoActivity extends AppCompatActivity {

    private AsyncHttpClient cliente;
    Token token = new Token();

    EditText correo;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correo);
        cliente = new AsyncHttpClient();
        correo = findViewById(R.id.txtCorreo);
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

    private void verificarCorreo(){
        String url = "http://mathome.me/api/service/verify/email.php?";
        //String url = "http://192.168.1.52/api-mathome/service/verify/email.php?";
        String email = correo.getText().toString().replace(" ","%20");
        String requestToken = "token="+token.getToken()+"&email="+email;

        cliente.post(url + requestToken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String respuesta = new String(responseBody);

                    if(respuesta.equalsIgnoreCase("Exist")){
                        correo.setError(getString(R.string.error_correo_exist));
                    }else if(respuesta.equalsIgnoreCase("Successful")){
                        SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
                        SharedPreferences.Editor obj_editor = preferences.edit();
                        obj_editor.putString("correo", correo.getText().toString());
                        obj_editor.putString("tipo", "correo");
                        obj_editor.commit();

                        Intent clave = new Intent(CorreoActivity.this, ClaveActivity.class);
                        startActivity(clave);
                        overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    }
                }else{
                    Toast.makeText(CorreoActivity.this, "Algo salió mal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void Clave(View view){
        if(validar()){
            if(VerificarRed()){
                verificarCorreo();
            }

        }
    }

    public void Telefono(View view){
        SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
        SharedPreferences.Editor obj_editor = preferences.edit();
        obj_editor.putString("tipo", "telefono");
        obj_editor.commit();
        Intent telefono = new Intent(this, TelefonoActivity.class);
        startActivity(telefono);
        finish();
    }

    public void Login(View view){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    public boolean validar() {

        boolean retorno = true;

        String correoInput = correo.getText().toString().trim();
        if(!correoInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(correoInput).matches()){
            retorno = true;
        }else{
            correo.setError(getString(R.string.error_correo));
            retorno = false;
        }

        return retorno;
    }

}
