package com.cae.electrociclate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn1;
    EditText edt1,edt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt1=findViewById(R.id.edtUsuario);
        edt2=findViewById(R.id.edtClave);
        btn1=findViewById(R.id.btnLogueo);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String usuario = edt1.getText().toString();
        String clave = edt2.getText().toString();
        if(usuario.length()==0){
            int text;

            Toast.makeText(this, "Tiene que ingresar usaurios",Toast.LENGTH_LONG).show();
        }
        else if(clave.length()==0){
            Toast.makeText(this,"Debe ingresar clave",Toast.LENGTH_LONG).show();
        }else{
            //startActivity(new Intent(getApplicationContext(),gestion.class));
            validarUsuario("http://jvp.onlinewebshop.net/index.php/validarLogin");
        }
    }
    private void validarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                String res;
                res=response.trim().toString();
                if(res.equals("NO")){
                    Toast.makeText(MainActivity.this,"Usuario o clave son incorrectas",Toast.LENGTH_LONG).show();
                }else{
                    Log.i("======>", response.trim());
                    startActivity(new Intent(getApplicationContext(),gestion.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String,String>();
                parametros.put("usuario",edt1.getText().toString());
                parametros.put("clave",edt2.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
