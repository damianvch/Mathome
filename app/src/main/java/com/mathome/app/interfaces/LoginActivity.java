package com.mathome.app.interfaces;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.*;
import com.mathome.app.R;
import com.mathome.app.register.EmpezarActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usuario,clave;
    private Button entrar;
    private AsyncHttpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    public void Menu(View view){
        Toast.makeText(this,"Contraseña incorrecta",Toast.LENGTH_SHORT).show();
    }
}
