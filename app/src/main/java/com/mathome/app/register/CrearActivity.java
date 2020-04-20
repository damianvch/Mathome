package com.mathome.app.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mathome.app.R;
import com.mathome.app.interfaces.LoginActivity;

public class CrearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CrearActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }
}
