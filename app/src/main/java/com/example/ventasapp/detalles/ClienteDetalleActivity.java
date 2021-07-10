package com.example.ventasapp.detalles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.ventasapp.R;
import com.example.ventasapp.databinding.ActividadPrincipalBinding;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.Producto;
import com.example.ventasapp.entidades.VolleySingleton;
import com.example.ventasapp.modelo.Comida;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ClienteDetalleActivity extends AppCompatActivity {

    ImageView imageView;
    ProgressDialog progreso;


    //RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    String id;
    String descripcion;




    BaseDatos bdLocal;
    Producto producto;
   // Producto p;
    CollapsingToolbarLayout collapser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      collapser = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        id = getIntent().getStringExtra("id");
        if (id != null){
            bdLocal = new BaseDatos(ClienteDetalleActivity.this);
            producto = bdLocal.verProducto(Integer.parseInt(id));

            if (producto != null) {
            // Cambiar título
            collapser.setTitle(producto.getNombre_producto());
          //  Toast.makeText(this, "el precio"+producto.getPrecio(), Toast.LENGTH_SHORT).show();
            imageView = findViewById(R.id.iv_avatar);
            Glide.with(this)
                    .load(producto.getUrlImagen())
                    .centerCrop()
                    .into(imageView);
            }
        }
        Intent intent = getIntent();
        Bitmap b = (Bitmap) intent.getParcelableExtra("image");
        ponerEnImagen(b);
       // String IDPRODUCTO = getIntent().getStringExtra("id");
       // Toast.makeText(this, "el precio "+IDPRODUCTO, Toast.LENGTH_SHORT).show();
        String extras = getIntent().getStringExtra("nombre");
        collapser.setTitle(extras);

    //    consultarProducto();



        ClienteDetalleFragment fragment = (ClienteDetalleFragment)
                getSupportFragmentManager().findFragmentById(R.id.lawyer_detail_container);
        if (fragment == null) {
            fragment = ClienteDetalleFragment.newInstance(id);
            fragment = new ClienteDetalleFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.lawyer_detail_container, fragment)
                    .commit();
        }



    }
/*
    private void consultarProducto() {
        progreso=new ProgressDialog(this);
        progreso.setMessage("Consultando...");
        progreso.show();



        String url="https://servicioparanegocio.es/ventasApp/consultar_ProductoPorId.php?id_producto="+id;


        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(jsonObjectRequest);

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(this,"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();

        //    Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show();

        p = new Producto();

        JSONArray json=response.optJSONArray("producto");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            p.setId_producto(jsonObject.optInt("Id_producto"));
            p.setNombre_producto(jsonObject.optString("nombre"));
            p.setPrecio(jsonObject.optInt("Precio"));
            p.setDescripcion(jsonObject.optString("Descripcion"));

            IDPRODUCTO = String.valueOf(p.getId_producto());
            DESCRIPCION = p.getDescripcion();
            NOMBRE = p.getNombre_producto();
            PRECIO = String.valueOf(p.getPrecio());
            Toast.makeText(this,"Mensaje: "+NOMBRE,Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

       nombre = p.getNombre_producto();
        descripcion = p.getDescripcion();

    }
*/
    private void ponerEnImagen(Bitmap b) {
        ImageView image = (ImageView) findViewById(R.id.iv_avatar);
        // Usando Glide para la carga asíncrona
        Glide.with(this)
                .load(b)
                .centerCrop()
                .into(image);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_categorias, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}