package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mathome.app.R;
import com.mathome.app.interfaces.LoginActivity;

public class GeneroActivity extends AppCompatActivity {

    private RadioButton rbMujer,rbHombre,rbNo;
    private RadioGroup rgGenero;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genero);
        rbMujer = findViewById(R.id.rbMujer);
        rbHombre = findViewById(R.id.rbHombre);
        rbNo = findViewById(R.id.rbNo);
        rgGenero = findViewById(R.id.rgGenero);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnSiguienteR4:
                if(validar()){
                    SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);

                    if(preferences.getString("tipo","").equalsIgnoreCase("correo")){
                        Intent correo = new Intent(this, CorreoActivity.class);
                        startActivity(correo);
                        overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    }else if(preferences.getString("tipo","").equalsIgnoreCase("telefono")){
                        Intent telefono = new Intent(this, TelefonoActivity.class);
                        startActivity(telefono);
                        overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    }
                }else{
                    Toast.makeText(this, "Debe seleccionar su género", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.lblEmpezarLoginR4:
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                finish();
                break;
        }
    }

    public boolean validar() {
        boolean retorno = true;

        if(!rbMujer.isChecked() && !rbHombre.isChecked() && !rbNo.isChecked()){
            retorno = false;
        }

        if(rbMujer.isChecked()){
            SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
            SharedPreferences.Editor obj_editor = preferences.edit();
            obj_editor.putString("genero", rbMujer.getText().toString());
            obj_editor.commit();
            retorno = true;
        }

        if(rbHombre.isChecked()){
            SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
            SharedPreferences.Editor obj_editor = preferences.edit();
            obj_editor.putString("genero", rbHombre.getText().toString());
            obj_editor.commit();
            retorno = true;
        }

        if(rbNo.isChecked()){
            SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
            SharedPreferences.Editor obj_editor = preferences.edit();
            obj_editor.putString("genero", rbNo.getText().toString());
            obj_editor.commit();
            retorno = true;
        }

        return retorno;
    }

}
