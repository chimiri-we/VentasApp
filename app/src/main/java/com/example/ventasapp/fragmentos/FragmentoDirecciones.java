package com.example.ventasapp.fragmentos;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventasapp.R;
import com.example.ventasapp.adaptadores.AdaptadorDirecciones;
import com.example.ventasapp.adaptadores.AdaptadorVentas;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.detalles.ClienteDetalleActivity;
import com.example.ventasapp.entidades.Venta;
import com.example.ventasapp.ui.DecoracionLineaDivisoria;

import java.util.ArrayList;


/**
 * Fragmento para la pestaña "DIRECCIONES" De la sección "Mi Cuenta"
 */
public class FragmentoDirecciones extends Fragment {

    private LinearLayoutManager linearLayout;
    ArrayList<Venta> ventaArrayList;
    AdaptadorVentas adaptador;

    public FragmentoDirecciones() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        RecyclerView reciclador = (RecyclerView)view.findViewById(R.id.reciclador);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        reciclador.setLayoutManager(layoutManager);
        reciclador.setHasFixedSize(true);
        BaseDatos bdLocal = new BaseDatos(getContext());
        ArrayList<Venta> ventaArrayList = bdLocal.listaVentas();
        if (ventaArrayList.size() > 0){
            reciclador.setVisibility(View.VISIBLE);
            adaptador = new AdaptadorVentas(getContext(), ventaArrayList);
            reciclador.setAdapter(adaptador);
          //  reciclador.addItemDecoration(new DecoracionLineaDivisoria(getActivity()));


        }
        else {
            reciclador.setVisibility(View.GONE);
         //   Toast.makeText(getContext(), "No hay ningún articulo guardado para este cliente", Toast.LENGTH_LONG).show();

        }


        return view;
    }


}
