package com.example.ventasapp.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.ventasapp.R;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.Usuarios;
import com.example.ventasapp.fragmentos.FragmentoCategorias;
import com.example.ventasapp.fragmentos.FragmentoCompras;
import com.example.ventasapp.fragmentos.FragmentoCuenta;
import com.example.ventasapp.fragmentos.FragmentoInicio;
import com.example.ventasapp.login.ContentLogin;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;


public class ActividadPrincipal extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    BaseDatos bdLocal;
    Usuarios usuario=null;
    String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bdLocal = new BaseDatos(this);
       usuario = bdLocal.validarUsuario();
        if (usuario != null) {
            Toast.makeText(this, "el usuario es  "+usuario.getNombre(), Toast.LENGTH_SHORT).show();

            nombreUsuario = usuario.getNombre();

        }else {
            startActivity(new Intent(this, ContentLogin.class));
               finish();
               return;
        }
        setContentView(R.layout.actividad_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_inicio_base);
            ab.setDisplayHomeAsUpEnabled(true);
        }



      //  TextView tvNombre = findViewById(R.id.tv_nombre_user);
       // String nombre = getIntent().getStringExtra("nombre");
       // tvNombre.setText(nombre);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }



    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new FragmentoInicio();
                break;
            case R.id.item_cuenta:
                fragmentoGenerico = new FragmentoCuenta();
                break;
            case R.id.item_lo_nuevo:
                fragmentoGenerico = new FragmentoCompras();
                break;
            case R.id.item_categorias:
                fragmentoGenerico = new FragmentoCategorias();
                break;
            case R.id.item_configuracion:
                startActivity(new Intent(this, ActividadConfiguracion.class));
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_carrito:
                Intent carritoIntent = new Intent(this, CarritoComprasActivity.class);
                startActivity(carritoIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

  /*  public void activyti(View view) {
        Intent intent = new Intent(this, ClienteDetalleActivity.class);
        startActivity(intent);
    }
*/


    }
