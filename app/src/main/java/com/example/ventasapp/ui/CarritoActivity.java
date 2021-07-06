package com.example.ventasapp.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.ventasapp.databinding.ActivityCarritoBinding;
import com.example.ventasapp.detalles.ClienteDetalleActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.ventasapp.R;

public class CarritoActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCarritoBinding binding;

    int idProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCarritoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
       // idProducto = Integer.parseInt(getIntent().getStringExtra("id_producto"));
       // Toast.makeText(this, "el id es "+idProducto, Toast.LENGTH_SHORT).show();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_carrito);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent miIntent = new Intent(CarritoActivity.this, ClienteDetalleActivity.class);
                startActivity(miIntent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_carrito);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}