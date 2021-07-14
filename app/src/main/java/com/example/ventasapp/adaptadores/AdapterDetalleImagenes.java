package com.example.ventasapp.adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventasapp.R;
import com.example.ventasapp.entidades.Producto;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class AdapterDetalleImagenes extends RecyclerView.Adapter<AdapterDetalleImagenes.ImagenesViewHolder>{

    List<Producto> lisImagenesProducto;
    Context context;

    public class ImagenesViewHolder extends RecyclerView.ViewHolder{

        public TextView name,age;
        LinearLayout parent;
        ImageView img;

        public ImagenesViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView6);
            parent = itemView.findViewById(R.id.parent);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
        }
    }

    public AdapterDetalleImagenes(Context context, List<Producto> lisImagenesProducto) {
        this.context = context;
        this.lisImagenesProducto = lisImagenesProducto;
    }
    @NonNull
    @NotNull
    @Override
    public AdapterDetalleImagenes.ImagenesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recicler_imagenes,parent,false);

        return new AdapterDetalleImagenes.ImagenesViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterDetalleImagenes.ImagenesViewHolder holder, int position) {

        holder.img.setImageBitmap(lisImagenesProducto.get(position).getImagen());
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.parent.setBackgroundColor(currentColor);
        holder.name.setText(lisImagenesProducto.get(position).getNombre_producto());
        holder.age.setText((int) lisImagenesProducto.get(position).getPrecio());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
