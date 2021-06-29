package com.example.ventasapp.detalles;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.ventasapp.R;
import com.example.ventasapp.databinding.ActividadPrincipalBinding;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.Producto;
import com.example.ventasapp.modelo.Comida;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class ClienteDetalleActivity extends AppCompatActivity {

    ImageView imageView;

 String id;
 int id_producto;
 BaseDatos bdLocal;
 Producto producto;
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
        if (id != null)

        bdLocal = new BaseDatos(ClienteDetalleActivity.this);
        producto = bdLocal.verProducto(Integer.parseInt(id));
        if (producto != null) {

            collapser.setTitle(producto.getNombre_producto());
          //  Toast.makeText(this, "el precio"+producto.getPrecio(), Toast.LENGTH_SHORT).show();
            imageView = findViewById(R.id.iv_avatar);
            Glide.with(this)
                    .load(producto.getUrlImagen())
                    .centerCrop()
                    .into(imageView);
        }

        String extras = getIntent().getStringExtra("nombre");


         // Cambiar título





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

    private void loadImageParallax(int id) {
        ImageView image = (ImageView) findViewById(R.id.iv_avatar);
        // Usando Glide para la carga asíncrona
        Glide.with(this)
                .load(id)
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