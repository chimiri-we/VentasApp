package com.example.ventasapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventasapp.R;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.DetalleVenta;
import com.example.ventasapp.entidades.Usuarios;
import com.example.ventasapp.entidades.Venta;
import com.example.ventasapp.fragmentos.FirstFragment;
import com.example.ventasapp.fragmentos.SecondFragment;
import com.example.ventasapp.login.ContentLogin;
import com.example.ventasapp.login.JSONParser;
import com.example.ventasapp.login.LoginViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CarritoComprasActivity extends AppCompatActivity {
    public static final String REGISTER_URL = "https://servicioparanegocio.es/ventasApp/regist_venta.php";
    public static final String REGISTRO_DETALLE = "https://servicioparanegocio.es/ventasApp/registro_venta.php";
    public static final String KEY_ID_USUARIO = "Id_usuario";
    public static final String KEY_TOTAL_VENTA = "Total_venta";
    public static final String KEY_FECHA_VENTA = "Fecha_venta";
    public static final String KEY_STATUS = "Status";
    JSONParser jsonParser = new JSONParser();
    JSONArray detalleJson;
    public static final String DArrayList = "detalle";


    // La respuesta del JSON es
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";



    private ProgressDialog progressDialog;
    int id_venta=0;
    int totalV;
    Usuarios usuarios;
    int id_producto=0;
    DetalleVenta detalleVenta;
    private Fragment firstFragment, secondFragment;
    FloatingActionButton floatingActionButton;
    TextView tvTotal;
    Button btnTerminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_content_carrito, secondFragment).commit();
/*
        floatingActionButton = findViewById(R.id.btnPagar);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventaNueva();
            }
        });*/
        BaseDatos bdLocal = new BaseDatos(this.getApplicationContext());
        Venta idVen = bdLocal.ultimaVenta();

        int idventa = 1+(idVen.getId_venta());

        TotalVenta(idventa);


    }
    public void onClick (View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (view.getId())
        {
            case R.id.button_terminar: transaction.replace(R.id.nav_host_fragment_content_carrito, firstFragment);
                transaction.addToBackStack(null);
                break;
            case R.id.btn_pagar: transaction.replace(R.id.nav_host_fragment_content_carrito, secondFragment);
                ventaNueva();
                ventaDetalle();
                   transaction.addToBackStack(null);
                   break;
        }
        transaction.commit();

    }




    private void TotalVenta(int idventa) {
       BaseDatos bdLocal = new BaseDatos(this.getApplicationContext());
        // SQLiteDatabase db = bdLocal.getWritableDatabase();
        DetalleVenta dtVenta = bdLocal.sumarItems(idventa);
        totalV = Integer.parseInt(dtVenta.getTotal());
    //    tvTotal.setText("$"+dtVenta.getTotal());
    }

    private void ventaNueva() {

        int success;
        int status = 0;
        BaseDatos bdLocal = new BaseDatos(this.getApplicationContext());
        Date fechaActual= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(fechaActual);

        usuarios = new Usuarios();
        usuarios = bdLocal.verdatosUsuario();
       int id_usuario = usuarios.getId_usuario();
       String idUsuario = String.valueOf(id_usuario);
        String estatus = String.valueOf(status);
        String TOTALVENTA = String.valueOf(totalV);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                response -> {
                    progressDialog.hide();
                     Venta venta = new Venta(id_usuario, formattedDate, totalV, status);
                           bdLocal.generarVenta(venta);

                },
                error -> {
                    progressDialog.hide();
                    Toast.makeText(this, "Nose puede Registrar"+error.toString(), Toast.LENGTH_LONG).show();
                    Log.i("ERROR", error.toString());
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put(KEY_ID_USUARIO, idUsuario);
                params.put(KEY_TOTAL_VENTA, TOTALVENTA);
                params.put(KEY_FECHA_VENTA, formattedDate);
                params.put(KEY_STATUS, estatus);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void ventaDetalle() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando..");
        progressDialog.show();
        BaseDatos bdLocal = new BaseDatos(this.getApplicationContext());
        Venta idVen = bdLocal.ultimaVenta();
        int ultimaV = idVen.getId_venta();
        ArrayList<DetalleVenta> arrayList = bdLocal.listDetalleVenta(ultimaV);
        detalleJson=new JSONArray();
        for (int i=0; i<arrayList.size();i++){
            JSONObject dtObj=new JSONObject();
            try {
                dtObj.put("Id_producto", arrayList.get(i).getId_producto());

                dtObj.put("Id_venta", arrayList.get(i).getId_venta());
                //   dtObj.put("precio", arrayList.get(i).getPrecio());
                dtObj.put("Cantidad", arrayList.get(i).getCantidad());
                dtObj.put("Total", arrayList.get(i).getTotal());
                // dtObj.put("detalle", "dtventa" );
            }catch (JSONException e) {
                e.printStackTrace();
            }
            detalleJson.put(dtObj);

        }
        StringRequest detalleRecuest = new StringRequest(Request.Method.POST, REGISTRO_DETALLE,
                response -> {
                    // progressDialog.hide();
                    //  Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show();

                    Log.i("detalle", String.valueOf(detalleJson));

                }, error -> {
            // progressDialog.hide();
            // Toast.makeText(context, "Nose puede Registrar"+error.toString(), Toast.LENGTH_LONG).show();
            Log.i("ERROR", error.toString());

        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put(DArrayList, String.valueOf(detalleJson));

                return params;

            }
        };
        RequestQueue dtrequestQueue = Volley.newRequestQueue(this);
        dtrequestQueue.add(detalleRecuest);
        startActivity(new Intent(this, ActividadPrincipal.class));
        finish();
       // Intent i = new Intent(this, ActividadPrincipal.class);
      //  startActivity(i);
    }
       // Toast.makeText(this, "el total es" + totalV + id_usuario, Toast.LENGTH_SHORT).show();

}
