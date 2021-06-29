package com.example.ventasapp.modelo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ventasapp.R;

public class Productosmodel  extends RecyclerView.ViewHolder {

    public TextView nombre;
    public TextView precio;

    public ImageView imagen;
    public Productosmodel(View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.nombre_comida);
        precio = itemView.findViewById(R.id.precio_comida);
        imagen = itemView.findViewById(R.id.miniatura_comida);
    }
}