package com.mathome.app.interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pais);
        cliente = new AsyncHttpClient();
        ButterKnife.bind(this);
        llenarPaises();
        buscarOnTextListener();
    }

    private boolean VerificarRed(){
        boolean estado = true;
        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        Boolean mobile = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(!wifi && !mobile){
            Toast.makeText(getApplicationContext(),R.string.conexion,Toast.LENGTH_LONG).show();
            estado = false;
        }

        return estado;
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
        if(VerificarRed()){
            //String url = "http://192.168.1.52/api-mathome/service/get/country.php?";
            String url = "http://mathome.me/api/service/get/country.php?";
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
    }

    private void cargarPaises(String respuesta) {

        final List<Pais> listaPaises = new ArrayList<>();

        try {

            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i=0; i < jsonArreglo.length();i++){
                Pais p = new Pais();
                p.setId(jsonArreglo.getJSONObject(i).getInt("idPais"));
                p.setCodigo(jsonArreglo.getJSONObject(i).getString("codigoISO2"));
                p.setNombre(jsonArreglo.getJSONObject(i).getString("pais"));
                p.setPrefijo(jsonArreglo.getJSONObject(i).getString("prefijoTelef"));
                listaPaises.add(p);
            }

            paisAdapter = new PaisAdapter(this, listaPaises);
            listViewPaises.setAdapter(paisAdapter);

            listViewPaises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Pais pe = paisAdapter.getItem(position);
                    SharedPreferences preferences = getSharedPreferences("registro", Context.MODE_PRIVATE);
                    SharedPreferences.Editor obj_editor = preferences.edit();
                    obj_editor.putString("idPais", String.valueOf(pe.getId()));
                    obj_editor.putString("codigoISO2", pe.getCodigo());
                    obj_editor.putString("prefijoTelef", pe.getPrefijo());
                    obj_editor.commit();
                    Intent telefono = new Intent(PaisActivity.this, TelefonoActivity.class);
                    startActivity(telefono);
                    //Toast.makeText(getApplicationContext(), pe.getCodigo()+" ("+pe.getPrefijo()+")", Toast.LENGTH_SHORT).show();
                }
            });


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void Telefono(View view){
        Intent telefono = new Intent(this, TelefonoActivity.class);
        startActivity(telefono);
        finish();
    }

    public void BackPressed(View view) {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

}
