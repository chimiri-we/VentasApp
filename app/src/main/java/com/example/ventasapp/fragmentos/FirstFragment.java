package com.example.ventasapp.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ventasapp.R;
import com.example.ventasapp.adaptadores.AdaptadorCarritoCompras;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.DetalleVenta;
import com.example.ventasapp.entidades.Usuarios;
import com.example.ventasapp.entidades.Venta;

import java.util.ArrayList;


public class FirstFragment extends Fragment {

    DetalleVenta detalleVenta;
    ArrayList<DetalleVenta> arrayList;
    BaseDatos bdLocal;
    Usuarios usuarios;
     int id_producto=0;

     AdaptadorCarritoCompras adaptadorCarritoCompras;
     RecyclerView recyclerViewdetalle;
    int id;
    TextView nombreUsuario, direcionUsuario, totalProducto;
    public FirstFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first, container, false);
        ImageView imgPerfil = v.findViewById(R.id.icono_perfil);

        totalProducto = v.findViewById(R.id.producto_total);
        nombreUsuario = v.findViewById(R.id.detalle_nombre_usuario);
        direcionUsuario = v.findViewById(R.id.direccion_detallle);
        recyclerViewdetalle = v.findViewById(R.id.recicler_detalle_venta);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewdetalle.setLayoutManager(layoutManager);
        recyclerViewdetalle.setHasFixedSize(true);

        bdLocal = new BaseDatos(getContext());
        Venta idVen = bdLocal.ultimaVenta();

        int idventa = 1+(idVen.getId_venta());
        ArrayList<DetalleVenta> arrayList = bdLocal.listDetalleVenta(idventa);
        if (arrayList.size() > 0){
            recyclerViewdetalle.setVisibility(View.VISIBLE);
            adaptadorCarritoCompras = new AdaptadorCarritoCompras(getContext(), arrayList);
            recyclerViewdetalle.setAdapter(adaptadorCarritoCompras);
        }else {

               recyclerViewdetalle.setVisibility(View.GONE);
                Toast.makeText(getContext(), "No hay ning??n articulo guardado para este cliente", Toast.LENGTH_LONG).show();

        }
        usuarios = new Usuarios();
        usuarios = bdLocal.verdatosUsuario();
       nombreUsuario.setText(usuarios.getNombre());
       direcionUsuario.setText(usuarios.getDireccion());
        Glide.with(requireContext())
                .asBitmap()
                .load(usuarios.getUrlImagen())
                .error(R.drawable.ic_persona)
                .centerCrop()
                .into(imgPerfil);
        totalVenta(idventa);


        return v;
}

    private void totalVenta(int idventa) {
        BaseDatos bdLocal = new BaseDatos(getContext().getApplicationContext());
        // SQLiteDatabase db = bdLocal.getWritableDatabase();
        DetalleVenta dtVenta = bdLocal.sumarItems(idventa);

        totalProducto.setText("$"+dtVenta.getTotal());
    }


}