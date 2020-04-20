package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mathome.app.R;
import com.mathome.app.entity.RegistrarUsuario;
import com.mathome.app.interfaces.LoginActivity;

public class NombreActivity extends AppCompatActivity {

    EditText nombre,apellido;
    RegistrarUsuario ru = new RegistrarUsuario();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre);
        nombre = (EditText)findViewById(R.id.txtNombre);
        apellido = (EditText)findViewById(R.id.txtApellido);
    }

    public void Login(View view){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }

    public void Nacimiento(View view){
        if(validar()){
            Intent nacimiento = new Intent(this, NacimientoActivity.class);
            startActivity(nacimiento);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
        }
    }

    public void Nivel(View view){
        if(validar()){
            Intent nivel = new Intent(this, NivelActivity.class);
            startActivity(nivel);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
        }
    }

    public boolean validar(){

        boolean retorno = true;

        String c1 = nombre.getText().toString().trim();
        String c2 = apellido.getText().toString().trim();

        if(c1.isEmpty()){
            nombre.setError(getString(R.string.error_nombre));
            retorno = false;
        }

        if(c2.isEmpty()){
            apellido.setError(getString(R.string.error_apellido));
            retorno = false;
        }

        return retorno;
    }

}
