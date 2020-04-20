package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mathome.app.R;
import com.mathome.app.interfaces.LoginActivity;

public class EmpezarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empezar);
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

    public void Login(View view){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }
    public void NombreApellido(View view){
        if(VerificarRed()){
            Intent nombre_apellido = new Intent(this, NombreActivity.class);
            startActivity(nombre_apellido);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
        }
    }

}
