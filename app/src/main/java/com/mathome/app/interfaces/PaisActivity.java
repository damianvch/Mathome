package com.mathome.app.interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mathome.app.R;
import com.mathome.app.adapter.PaisAdapter;
import com.mathome.app.entity.Pais;
import com.mathome.app.register.TelefonoActivity;
import com.mathome.app.security.Token;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class PaisActivity extends AppCompatActivity {

    @BindView(R.id.listViewPaises)
    public ListView listViewPaises;
    @BindView(R.id.txtBuscar)
    public EditText txtBuscar;
    private PaisAdapter paisAdapter;
    private AsyncHttpClient cliente;
    Token token = new Token();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);
        cliente = new AsyncHttpClient();
        ButterKnife.bind(this);
        llenarPaises();
        buscarOnTextListener();
    }

    private void buscarOnTextListener() {
        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                paisAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void llenarPaises(){
        String url = "http://192.168.1.52/api-mathome/service/get/country.php?";
        String requestToken = "token="+token.getToken();
        cliente.post(url + requestToken, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode == 200){
                    cargarPaises(new String(responseBody));
                }else{
                    Toast.makeText(getApplicationContext(), "Algo salió mal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void cargarPaises(String respuesta) {

        List<Pais> listaPlanetas = new ArrayList<>();

        try {

            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i=0; i < jsonArreglo.length();i++){

                Pais p = new Pais();
                p.setCodigo(jsonArreglo.getJSONObject(i).getString("codigoISO2"));
                p.setNombre(jsonArreglo.getJSONObject(i).getString("pais"));
                p.setPrefijo(jsonArreglo.getJSONObject(i).getString("prefijoTelef"));
                listaPlanetas.add(p);
            }

            paisAdapter = new PaisAdapter(this, listaPlanetas);
            listViewPaises.setAdapter(paisAdapter);


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
            // e.printStackTrace();
        }


    }

    public void Telefono(View view){
        Intent telefono = new Intent(this, TelefonoActivity.class);
        startActivity(telefono);
        finish();
    }

    public void onBackPressed(View view) {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

}
