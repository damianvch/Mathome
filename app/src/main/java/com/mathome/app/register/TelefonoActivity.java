package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mathome.app.R;
import com.mathome.app.interfaces.LoginActivity;
import com.mathome.app.interfaces.PaisActivity;

public class TelefonoActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefono);
    }

    public void Clave(View view){
        Intent clave = new Intent(this, ClaveActivity.class);
        startActivity(clave);
        overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }

    public void Paises(View view){
        Intent paises = new Intent(this, PaisActivity.class);
        startActivity(paises);
        overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }

    public void Correo(View view){
        Intent correo = new Intent(this, CorreoActivity.class);
        startActivity(correo);
        finish();
    }

    public void Login(View view){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }

}
