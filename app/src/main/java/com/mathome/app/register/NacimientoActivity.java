package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.mathome.app.R;
import com.mathome.app.interfaces.LoginActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class NacimientoActivity extends AppCompatActivity {

    Button fechaNacimiento;
    DatePickerDialog.OnDateSetListener setListener;
    Calendar calendar = Calendar.getInstance();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nacimiento);

        fechaNacimiento = findViewById(R.id.btnFechaNacimiento);

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        int month2 = month+1;
        String objMonth = String.valueOf(month2);

        if(objMonth.length() < 2){
            objMonth = "0"+objMonth;
        }
        fechaNacimiento.setText(String.valueOf(year)+"-"+objMonth+"-"+String.valueOf(day));

        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NacimientoActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String objMonth = String.valueOf(month);
                if(objMonth.length() < 2){
                    objMonth = "0"+objMonth;
                }
                String date = year+"-"+objMonth+"-"+dayOfMonth;
                fechaNacimiento.setText(date);
            }
        };

    }

    public void Genero(View view) {
            if(validar()){
                SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
                SharedPreferences.Editor obj_editor = preferences.edit();
                obj_editor.putString("nacimiento", fechaNacimiento.getText().toString());
                obj_editor.commit();

                Intent genero = new Intent(this, GeneroActivity.class);
                startActivity(genero);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
            }else{
                Toast.makeText(this, "Debe ser mayor de 10 años.", Toast.LENGTH_SHORT).show();
            }
    }

    public boolean validar() {
        boolean retorno = true;

        String objNac = fechaNacimiento.getText().toString();
        String[] parts = objNac.split("-");
        String strNac = parts[0];
        int anoNac = Integer.parseInt(strNac);
        int anoHoy = calendar.get(calendar.YEAR);
        int edad = anoHoy - anoNac;

        if(edad < 10){
            retorno = false;
        }
        return retorno;
    }


    public void Login(View view){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }

}
