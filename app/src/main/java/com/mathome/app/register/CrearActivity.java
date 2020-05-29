package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mathome.app.R;
import com.mathome.app.interfaces.LoginActivity;
import com.mathome.app.security.Token;

import cz.msebera.android.httpclient.Header;

public class CrearActivity extends AppCompatActivity {
    private AsyncHttpClient cliente;
    Token token = new Token();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);
        cliente = new AsyncHttpClient();
        insertarDatos();
    }

    private void insertarDatos(){
        String url = "http://mathome.me/api/service/session/signup.php?";
        //String url = "http://192.168.1.52/api-mathome/service/session/signup.php?";
        String requestToken = "token="+token.getToken();

        SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
        String nivel = preferences.getString("nivel","");
        String nombre = preferences.getString("nombre","");
        String apellido = preferences.getString("apellido","");
        String nacimiento = preferences.getString("nacimiento","");
        String genero = preferences.getString("genero","");
        String correo = preferences.getString("correo","");
        String telefono = preferences.getString("telefono","");
        String idPais = preferences.getString("idPais","");
        String clave = preferences.getString("clave","");

        if(preferences.getString("tipo","").equalsIgnoreCase("correo")){
            requestToken = "token="+token.getToken()+"&tipoCuenta=correo"+"&nivel="+nivel+"&nombre="+nombre+"&apellido="+apellido+"&fechaNacimiento="+nacimiento+"&genero="+genero+"&correo="+correo+"&clave="+clave;
        }else if(preferences.getString("tipo","").equalsIgnoreCase("telefono")){
            requestToken = "token="+token.getToken()+"&tipoCuenta=telefono"+"&nivel="+nivel+"&nombre="+nombre+"&apellido="+apellido+"&fechaNacimiento="+nacimiento+"&genero="+genero+"&idPais="+idPais+"&telefono="+telefono+"&clave="+clave;
        }

        cliente.post(url + requestToken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String respuesta = new String(responseBody);

                    if(respuesta.equalsIgnoreCase("Email exists")){
                        Intent intent = new Intent(CrearActivity.this, CorreoActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    }else{

                        if(respuesta.equalsIgnoreCase("General account error")){
                            Toast.makeText(CrearActivity.this, "Error de cuenta general", Toast.LENGTH_LONG).show();
                            Intent login = new Intent(CrearActivity.this, LoginActivity.class);
                            startActivity(login);
                            overridePendingTransition(R.anim.left_in,R.anim.left_out);
                            finish();
                        }

                        if(respuesta.equalsIgnoreCase("Account error")){
                            Toast.makeText(CrearActivity.this, "Error de cuenta", Toast.LENGTH_LONG).show();
                            Intent login = new Intent(CrearActivity.this, LoginActivity.class);
                            startActivity(login);
                            overridePendingTransition(R.anim.left_in,R.anim.left_out);
                            finish();
                        }

                        if(respuesta.equalsIgnoreCase("Email account error")){
                            Toast.makeText(CrearActivity.this, "Error de cuenta: correo electrónico", Toast.LENGTH_LONG).show();
                            Intent login = new Intent(CrearActivity.this, LoginActivity.class);
                            startActivity(login);
                            overridePendingTransition(R.anim.left_in,R.anim.left_out);
                            finish();
                        }

                        if(respuesta.equalsIgnoreCase("Phone account error")){
                            Toast.makeText(CrearActivity.this, "Error de cuenta: teléfono", Toast.LENGTH_LONG).show();
                            Intent login = new Intent(CrearActivity.this, LoginActivity.class);
                            startActivity(login);
                            overridePendingTransition(R.anim.left_in,R.anim.left_out);
                            finish();
                        }

                        if(respuesta.equalsIgnoreCase("Error registering user")){
                            Toast.makeText(CrearActivity.this, "Error de registro: usuario", Toast.LENGTH_LONG).show();
                            Intent login = new Intent(CrearActivity.this, LoginActivity.class);
                            startActivity(login);
                            overridePendingTransition(R.anim.left_in,R.anim.left_out);
                            finish();
                        }

                        if(respuesta.equalsIgnoreCase("Error registering profile")){
                            Toast.makeText(CrearActivity.this, "Error de registro: perfil", Toast.LENGTH_LONG).show();
                            Intent login = new Intent(CrearActivity.this, LoginActivity.class);
                            startActivity(login);
                            overridePendingTransition(R.anim.left_in,R.anim.left_out);
                            finish();
                        }

                        if(respuesta.equalsIgnoreCase("Successful registration")){
                            Toast.makeText(CrearActivity.this, "Registro con éxito", Toast.LENGTH_LONG).show();
                            Intent login = new Intent(CrearActivity.this, LoginActivity.class);
                            startActivity(login);
                            overridePendingTransition(R.anim.left_in,R.anim.left_out);
                            finish();
                        }
                    }

                }else{
                    Toast.makeText(CrearActivity.this, "Algo salió mal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
