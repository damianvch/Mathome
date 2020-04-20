package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mathome.app.R;
import com.mathome.app.interfaces.LoginActivity;

public class GeneroActivity extends AppCompatActivity {

    private EditText txtPersonalizado;
    private RadioButton rbPersonalizado;
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
        txtPersonalizado = (EditText)findViewById(R.id.txtPersonalizado);
        rbPersonalizado = (RadioButton) findViewById(R.id.rbPersonalizado);
        rgGenero = (RadioGroup) findViewById(R.id.rgGenero);
        txtPersonalizado.setVisibility(View.GONE);
    }

    public void MostrarOcultarGenero(View view){
        if(rbPersonalizado.isChecked() == true){
            txtPersonalizado.setVisibility(View.VISIBLE);
        }else{
            txtPersonalizado.setText("");
            txtPersonalizado.setVisibility(View.GONE);
        }
    }
    public void Correo(View view){
        Intent correo = new Intent(this, CorreoActivity.class);
        startActivity(correo);
        overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }
    public void Login(View view){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }

}
