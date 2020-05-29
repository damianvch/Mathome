package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class ClaveActivity extends AppCompatActivity {

    private AsyncHttpClient cliente;
    Token token = new Token();

    EditText clave;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clave);
        cliente = new AsyncHttpClient();
        clave = findViewById(R.id.txtClave);
    }

    public void Finalizar(View view){
        if(validar()){
            SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
            SharedPreferences.Editor obj_editor = preferences.edit();
            obj_editor.putString("clave", clave.getText().toString());
            obj_editor.commit();

            Intent finalizar = new Intent(this, RegistrarActivity.class);
            startActivity(finalizar);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
        }
    }

    public void Login(View view){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    public boolean validar() {

        boolean retorno = true;
        String claveInput = clave.getText().toString().trim();

        if(claveInput.length() < 6){
            clave.setError(getString(R.string.error_clave));
            retorno = false;
        }else{
            retorno = true;
        }
        return retorno;
    }

}
