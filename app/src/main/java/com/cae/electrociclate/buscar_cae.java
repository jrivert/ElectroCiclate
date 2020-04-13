package com.cae.electrociclate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link buscar_cae#newInstance} factory method to
 * create an instance of this fragment.
 */
public class buscar_cae extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView lstCentros;
   View vista;
   EditText edtBuscar;
   Button btnBuscar;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment buscar_cae.
     */
    // TODO: Rename and change types and number of parameters
    public static buscar_cae newInstance(String param1, String param2) {
        buscar_cae fragment = new buscar_cae();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista =inflater.inflate(R.layout.fragment_buscar_cae, container, false);
        btnBuscar=vista.findViewById(R.id.btnBuscar);
        edtBuscar=vista.findViewById(R.id.edtConsulta);
        btnBuscar.setOnClickListener(this);
        return vista;
    }


    @Override
    public void onClick(View v) {
        String criterio =edtBuscar.getText().toString();
        String url = "http://jvp.onlinewebshop.net/index.php/buscarcaepornombre/"+criterio;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Log.i("======>", jsonArray.toString());

                    List<String> items = new ArrayList<>();
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        items.add(object.getString("nombre") + "\n" + object.getString("direccion")+ "\n" + object.getString("Distrito"));
                    }

                    //lstCentros =  vista.findViewById(R.id.lista);
                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                            getActivity().getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            items);
                    Log.i("======>", items.toString());
                    lstCentros =  vista.findViewById(R.id.lista);
                    lstCentros.setAdapter(adaptador);

                } catch (JSONException e) {
                    Log.i("======>", e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", error.toString());
                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
