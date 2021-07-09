package com.example.ventasapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ventasapp.R;
import com.example.ventasapp.databinding.FragmentoPerfilBinding;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.DetalleVenta;
import com.example.ventasapp.entidades.Usuarios;
import com.example.ventasapp.entidades.Venta;
import com.example.ventasapp.fragmentos.FirstFragment;
import com.example.ventasapp.fragmentos.FragmentoPerfil;
import com.example.ventasapp.fragmentos.SecondFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Date;

public class CarritoComprasActivity extends AppCompatActivity {

    int id_venta=0;
    int totalV;
    Usuarios usuarios;
    int id_producto=0;
    DetalleVenta detalleVenta;
    private Fragment firstFragment, secondFragment;
    FloatingActionButton floatingActionButton;
    TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTotal = findViewById(R.id.tv_total_venta);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                id_venta = extras.getInt("id_venta");
//             cod = extras.getString("cod");
            }
        } else {
            id_venta = (int) savedInstanceState.getSerializable("id_venta");

        }

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_content_carrito, firstFragment).commit();

        floatingActionButton = findViewById(R.id.btnPagar);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventaNueva();
            }
        });
        BaseDatos bdLocal = new BaseDatos(this.getApplicationContext());
        Venta idVen = bdLocal.ultimaVenta();

        int idventa = 1+(idVen.getId_venta());

        TotalVenta(idventa);


    }
    public void onClick (View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (view.getId())
        {
            case R.id.btnPagar: transaction.replace(R.id.contenedorFragment, secondFragment);
                transaction.addToBackStack(null);
                break;
            //  case R.id.recuperarPass: transaction.replace(R.id.contenedorFragment, fragmentP);
            //       transaction.addToBackStack(null);
            //        break;
        }
        transaction.commit();

    }

    private void TotalVenta(int idventa) {
       BaseDatos bdLocal = new BaseDatos(this.getApplicationContext());
        // SQLiteDatabase db = bdLocal.getWritableDatabase();
        DetalleVenta dtVenta = bdLocal.sumarItems(idventa);
        totalV = Integer.parseInt(dtVenta.getTotal());
        tvTotal.setText("$"+dtVenta.getTotal());
    }

    private void ventaNueva() {
        int status = 0;
        BaseDatos bdLocal = new BaseDatos(this.getApplicationContext());
        Date fechaActual= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(fechaActual);

        usuarios = new Usuarios();
        usuarios = bdLocal.verdatosUsuario();
       int id_usuario = usuarios.getId_usuario();

        Venta venta = new Venta(id_usuario, formattedDate, totalV, status);
        bdLocal.generarVenta(venta);
        startActivity(getIntent());

       // Toast.makeText(this, "el total es" + totalV + id_usuario, Toast.LENGTH_SHORT).show();
    }
}
