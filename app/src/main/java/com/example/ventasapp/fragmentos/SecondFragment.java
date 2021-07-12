package com.example.ventasapp.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventasapp.R;
import com.example.ventasapp.adaptadores.AdaptadorCarritoCompras;
import com.example.ventasapp.databinding.FragmentSecondBinding;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.DetalleVenta;
import com.example.ventasapp.entidades.Usuarios;
import com.example.ventasapp.entidades.Venta;

import java.util.ArrayList;


public class SecondFragment extends Fragment {
    RecyclerView recyclerView;
    private FragmentSecondBinding binding;
    AdaptadorCarritoCompras adaptadorCarritoCompras;
    BaseDatos bdLocal;
    Usuarios usuarios;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        recyclerView = binding.reciclerProducto;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        bdLocal = new BaseDatos(getContext());
        Venta idVen = bdLocal.ultimaVenta();

        int idventa = 1+(idVen.getId_venta());
        ArrayList<DetalleVenta> arrayList = bdLocal.listDetalleVenta(idventa);
        if (arrayList.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            adaptadorCarritoCompras = new AdaptadorCarritoCompras(getContext(), arrayList);
            recyclerView.setAdapter(adaptadorCarritoCompras);
            binding.buttonTerminar.setVisibility(View.VISIBLE);
        }else {

            recyclerView.setVisibility(View.GONE);
            Toast.makeText(getContext(), "No hay ningún articulo guardado para este cliente", Toast.LENGTH_LONG).show();

            binding.buttonTerminar.setVisibility(View.GONE);
        }
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}