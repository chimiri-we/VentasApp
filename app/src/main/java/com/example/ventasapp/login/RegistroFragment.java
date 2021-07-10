package com.example.ventasapp.login;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ventasapp.R;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.Usuarios;
import com.example.ventasapp.ui.ActividadPrincipal;

import java.util.HashMap;
import java.util.Map;

public class RegistroFragment extends Fragment {

    public static final String REGISTER_URL = "https://servicioparanegocio.es/ventasApp/registro.php";

    public static final String KEY_NOMBRE = "Nombre";
    public static final String KEY_USUARIO = "Usuario";
    public static final String KEY_PASSWORD = "Password";
    public static final String KEY_NUMEROTELEFONO = "Telefono";

    private EditText edtNombreCliente, edtNumeroCliente, edtPasswordCliente, edtUsername;

    private ProgressDialog progressDialog;


    private static final String TABLE_USUARIO = "Usuario";

    BaseDatos bdLocal;
    Usuarios usuarios;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registro, container, false);
        edtNombreCliente = v.findViewById(R.id.edt_nombre_usuario);
        edtNumeroCliente = v.findViewById(R.id.edt_numero);
        edtPasswordCliente = v.findViewById(R.id.edt_password);
        edtUsername = v.findViewById(R.id.edt_username);

        Button btnRegistroCliente = v.findViewById(R.id.btn_registrarCliente);
        btnRegistroCliente.setOnClickListener(v1 -> registroCliente());


        return v;
    }


    private void registroCliente()  {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando..");
        progressDialog.show();

        final String nombre = edtNombreCliente.getText().toString().trim();
        final String password = edtPasswordCliente.getText().toString().trim();
        final String telefono = edtNumeroCliente.getText().toString().trim();
        final String username = edtUsername.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                response -> {
                  //  Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();

        usuarios = new Usuarios();
        usuarios.setNombre(nombre);
        usuarios.setUser(username);
        usuarios.setPassword(password);
        usuarios.setTelefono(telefono);

        ContentValues values = new ContentValues();
        values.put("nombre", usuarios.getNombre());
        values.put("telefono", usuarios.getTelefono());
        values.put("user", usuarios.getUser());
        values.put("password", usuarios.getPassword());
        bdLocal = new BaseDatos(requireContext().getApplicationContext());
        SQLiteDatabase db = bdLocal.getReadableDatabase();
        if(db!= null) {
            Toast.makeText(getContext(), "Datos guardados"+usuarios.getId_usuario(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "Tienes un problema" , Toast.LENGTH_SHORT).show();
        }
        db.insert(TABLE_USUARIO, null, values);

        progressDialog.hide();
        edtPasswordCliente.setText("");
        edtNumeroCliente.setText("");
        edtNombreCliente.setText("");
        edtUsername.setText("");
                    startActivity(new Intent(getContext(), ActividadPrincipal.class));
                },
                error -> {
                    progressDialog.hide();
                    Toast.makeText(getContext(), "Nose puede Registrar"+error.toString(), Toast.LENGTH_LONG).show();
                    Log.i("ERROR", error.toString());
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put(KEY_NOMBRE, nombre);
                params.put(KEY_USUARIO, username);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_NUMEROTELEFONO, telefono);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}




      /*  StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                response -> {
                    Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    edtPasswordCliente.setText("");
                    edtNumeroCliente.setText("");
                    edtNombreCliente.setText("");
                    edtEmailCliente.setText("");
                },
                error -> {
                    progressDialog.hide();
                    Toast.makeText(getContext(), "Nose puede Registrar"+error.toString(), Toast.LENGTH_LONG).show();
                    Log.i("ERROR", error.toString());
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put(KEY_NOMBRE, nombre);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_EMAIL, email);
                params.put(KEY_NUMEROTELEFONO, telefono);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }*/

