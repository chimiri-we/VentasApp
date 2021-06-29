package com.example.ventasapp.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ventasapp.R;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.detalles.ClienteDetalleActivity;
import com.example.ventasapp.entidades.Producto;
import com.example.ventasapp.modelo.Productosmodel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<Productosmodel> {
   


    int id_producto;
    private final Context context;
    private ArrayList listProductos;
    private final ArrayList<Producto> mArrayList;
    private final BaseDatos mDatabase;
    public ProductoAdapter(Context context, ArrayList<Producto> listProductos) {
        this.context = context;
        this.listProductos = listProductos;
        this.mArrayList = listProductos;
        mDatabase = new BaseDatos(context);
    }

    @NonNull
    @NotNull
    @Override
    public Productosmodel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_inicio, parent, false);
        return new Productosmodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Productosmodel holder, int position) {

         Producto producto = (Producto) listProductos.get(position);
        id_producto = ((Producto) listProductos.get(position)).getId_producto();
        holder.precio.setText("$"+producto.getPrecio());

        holder.nombre.setText(producto.getNombre_producto());
        Glide.with(holder.itemView.getContext())
                .load(producto.getUrlImagen())
                .centerCrop()
                .into(holder.imagen);
        holder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClienteDetalleActivity.class);
                intent.putExtra("id", String.valueOf(producto.getId_producto()));
                Toast.makeText(context, "el id es "+producto.getId_producto(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listProductos.size();
    }


}

