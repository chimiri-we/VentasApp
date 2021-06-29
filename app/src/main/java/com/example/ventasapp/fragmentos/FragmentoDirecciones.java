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
import com.example.ventasapp.adaptadores.AdaptadorDirecciones;
import com.example.ventasapp.detalles.ClienteDetalleActivity;
import com.example.ventasapp.ui.DecoracionLineaDivisoria;


/**
 * Fragmento para la pestaña "DIRECCIONES" De la sección "Mi Cuenta"
 */
public class FragmentoDirecciones extends Fragment {

    private LinearLayoutManager linearLayout;

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
        linearLayout = new LinearLayoutManager(getActivity());
        reciclador.setLayoutManager(linearLayout);

        AdaptadorDirecciones adaptador = new AdaptadorDirecciones();
        reciclador.setAdapter(adaptador);
        reciclador.addItemDecoration(new DecoracionLineaDivisoria(getActivity()));

        reciclador.setHasFixedSize(true);

        return view;
    }


}
