package com.mathome.app.interfaces;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.*;
import com.mathome.app.R;
import com.mathome.app.register.ClaveActivity;
import com.mathome.app.register.CorreoActivity;
import com.mathome.app.register.EmpezarActivity;
import com.mathome.app.security.Token;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    private AsyncHttpClient cliente;
    Token token = new Token();

    private EditText usuario,clave;
    private Button entrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cliente = new AsyncHttpClient();
        usuario = findViewById(R.id.txtUsername);
        clave = findViewById(R.id.txtPassword);
        entrar = findViewById(R.id.btnLogin);
        usuario.addTextChangedListener(validarCampos);
        clave.addTextChangedListener(validarCampos);
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

    private TextWatcher validarCampos = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usuarioInput = usuario.getText().toString().trim();
            String claveInput = clave.getText().toString().trim();

            entrar.setEnabled(!usuarioInput.isEmpty() && !claveInput.isEmpty());

            if(entrar.isEnabled() == true){
                entrar.setTextColor(getColor(R.color.white));
            }else{
                entrar.setTextColor(getColor(R.color.mathomeDeshabilitado));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    public void Empezar(View view){
        Intent empezar = new Intent(this, EmpezarActivity.class);
        startActivity(empezar);
    }


    public void Login(View view){
        String url = "http://mathome.me/api/service/session/login.php?";
        //String url = "http://192.168.1.52/api-mathome/service/session/login.php?";
        String user = usuario.getText().toString().replace(" ","%20");
        String pwd = clave.getText().toString().replace(" ","%20");
        String requestToken = "token="+token.getToken()+"&user="+user+"&pwd="+pwd;

        cliente.post(url + requestToken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String respuesta = new String(responseBody);

                    if(respuesta.equalsIgnoreCase("Not exist")){
                        usuario.setError(getString(R.string.no_existe_cuenta));
                    }else if(respuesta.equalsIgnoreCase("Incorrect password")){
                        clave.setError(getString(R.string.clave_incorrecta));
                    }else if(respuesta.equalsIgnoreCase("Successful")){
                        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor obj_editor = preferences.edit();
                        obj_editor.putString("user", usuario.getText().toString());
                        obj_editor.commit();
                        pwd();
                        Intent menu = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(menu);
                        overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Algo salió mal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void pwd(){
        String url = "http://mathome.me/api/service/security/password.php?";
        //String url = "http://192.168.1.52/api-mathome/service/security/password.php?";
        //String user = usuario.getText().toString().replace(" ","%20");
        String pwd = clave.getText().toString().replace(" ","%20");
        String requestToken = "token="+token.getToken()+"&pwd="+pwd;

        cliente.post(url + requestToken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    String respuesta = new String(responseBody);

                    if(respuesta.equalsIgnoreCase("No password")){
                        Toast.makeText(LoginActivity.this, "No hay una contraseña", Toast.LENGTH_SHORT).show();
                    }else{
                        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor obj_editor = preferences.edit();
                        obj_editor.putString("pwd", clave.getText().toString());
                        obj_editor.commit();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Algo salió mal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
