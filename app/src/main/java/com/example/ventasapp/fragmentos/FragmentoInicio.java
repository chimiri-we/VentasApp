package com.example.ventasapp.fragmentos;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventasapp.R;
import com.example.ventasapp.adaptadores.AdaptadorInicio;
import com.example.ventasapp.adaptadores.ProductoAdapter;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.detalles.ClienteDetalleActivity;
import com.example.ventasapp.entidades.Producto;

import java.util.ArrayList;

/**
 * Fragmento para la sección de "Inicio"
 */
public class FragmentoInicio extends Fragment {
    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    private AdaptadorInicio adaptador;
BaseDatos bdLocal;
Producto producto;
    public FragmentoInicio() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_inicio, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(layoutManager);


        bdLocal = new BaseDatos(getContext());
        ArrayList<Producto> listProducto = bdLocal.listProducto();
       // adaptador = new AdaptadorInicio();
        ProductoAdapter productoAdapter = new ProductoAdapter(getContext(), listProducto);
        reciclador.setAdapter(productoAdapter);
         reciclador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClienteDetalleActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
