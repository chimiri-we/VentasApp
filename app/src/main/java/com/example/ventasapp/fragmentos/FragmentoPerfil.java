package com.example.ventasapp.fragmentos;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventasapp.R;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.Usuarios;

/**
 * Fragmento para la pestaña "PERFIL" De la sección "Mi Cuenta"
 */
public class FragmentoPerfil extends Fragment {

    BaseDatos bdLocal;
    Usuarios usuarios;
    public FragmentoPerfil() {
    }

    private TextView nombre, correo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragmento_perfil, container, false);

        nombre = v.findViewById(R.id.texto_nombre);
        correo = v.findViewById(R.id.texto_email);
        usuarios = new Usuarios();
        verdatosUsuario();

        if (usuarios != null) {

            nombre.setText(usuarios.getNombre().toString());
            correo.setText(usuarios.getCorreo().toString().toLowerCase());
        }


        return v;
    }

    private void verdatosUsuario() {
        bdLocal = new BaseDatos(getContext());
        SQLiteDatabase db = bdLocal.getWritableDatabase();

        Cursor cursor;
        cursor = db.rawQuery("select * from Usuario", null);
        if (cursor.moveToFirst()) {

            usuarios = new Usuarios();
            usuarios.setId_usuario(cursor.getInt(0));
            //usuarios.setId_R(cursor.getInt(1));
            usuarios.setNombre(cursor.getString(1));
            usuarios.setDireccion(cursor.getString(3));
            usuarios.setCorreo(cursor.getString(4));
            usuarios.setTelefono(cursor.getString(5));
            usuarios.setUser(cursor.getString(6));
            usuarios.setPassword(cursor.getString(7));


        }
    }
}
