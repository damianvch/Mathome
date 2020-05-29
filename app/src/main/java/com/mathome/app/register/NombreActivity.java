package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mathome.app.R;
import com.mathome.app.dto.RegistroUsuarioDTO;
import com.mathome.app.interfaces.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class NombreActivity extends AppCompatActivity {

    EditText nombre,apellido;

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

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnSiguienteR2:
                if(validar()){
                    SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
                    SharedPreferences.Editor obj_editor = preferences.edit();
                    String nom = nombre.getText().toString().replace(" ","%20");
                    String ape = apellido.getText().toString().replace(" ","%20");
                    obj_editor.putString("nombre", nom);
                    obj_editor.putString("apellido", ape);
                    obj_editor.commit();

                    Intent nacimiento = new Intent(this, NacimientoActivity.class);
                    startActivity(nacimiento);
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                }
                break;
            case R.id.lblEmpezarLoginR2:
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                finish();
                break;
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
