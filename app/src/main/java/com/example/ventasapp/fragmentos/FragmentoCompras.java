package com.example.ventasapp.fragmentos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ventasapp.R;
import com.example.ventasapp.adaptadores.AdapterCompras;
import com.example.ventasapp.detalles.ClienteDetalleActivity;
import com.example.ventasapp.entidades.Producto;
import com.example.ventasapp.entidades.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentoCompras extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    RecyclerView recyclerUsuarios;
    ArrayList<Producto> listaUsuarios;

    ProgressDialog dialog;
    String ID_PRODUCTO;
    private ImageView img;
    private Button btnRecargar;

    // RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    public void FragmentoCategoria() {
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmento_compras, container, false);
        listaUsuarios=new ArrayList<>();
        recyclerUsuarios = (RecyclerView) v.findViewById(R.id.reciclerProductos);
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerUsuarios.setHasFixedSize(true);
        cargarWebService();
       img = v.findViewById(R.id.img_sinInternet);
       img.setVisibility(View.GONE);

        // request= Volley.newRequestQueue(getContext());


        return v;
    }

    private void cargarWebService() {
        dialog=new ProgressDialog(getContext());
        dialog.setMessage("Cargando Productos");
        dialog.show();



        String url="https://servicioparanegocio.es/ventasApp/consultarProducto.php";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        Producto usuario;
        JSONArray json=response.optJSONArray("producto");


        try {

            for (int i = 0; i< Objects.requireNonNull(json).length(); i++){
                usuario=new Producto();
            JSONObject jsonObject=null;
            jsonObject=json.getJSONObject(i);

            usuario.setId_producto(jsonObject.optInt("Id_producto"));
            usuario.setNombre_producto(jsonObject.optString("Nombre_producto"));
            usuario.setPrecio(jsonObject.optInt("Precio"));
            usuario.setDescripcion(jsonObject.optString("Descripcion"));
                usuario.setCaracteristicas(jsonObject.optString("Caracteristicas"));
                usuario.setStock(jsonObject.optInt("Stock"));
                usuario.setCategoria(jsonObject.optString("Categoria"));
            usuario.setDato(jsonObject.optString("Imagen_producto"));
            listaUsuarios.add(usuario);
          //  ID_PRODUCTO = String.valueOf(usuario.getId_producto());
             }
            dialog.hide();
            AdapterCompras adapter=new AdapterCompras(getContext(), listaUsuarios);
            recyclerUsuarios.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
            dialog.hide();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        dialog.hide();
        Log.d("ERROR: ", error.toString());
        mostrarImagenSinInternet();
    }

    private void mostrarImagenSinInternet() {
        img.setVisibility(View.VISIBLE);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentoCompras fragmentoCompras = new FragmentoCompras();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.detach(fragmentoCompras).attach(fragmentoCompras).commit();
            }
        });
    }

    public void verdetalle(View view) {
        Intent intent = new Intent(getContext(), ClienteDetalleActivity.class);
       // intent.putExtra("id", ID_PRODUCTO);
        startActivity(intent);

    }
}