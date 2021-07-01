package com.example.ventasapp.adaptadores;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ventasapp.R;
import com.example.ventasapp.detalles.ClienteDetalleActivity;
import com.example.ventasapp.entidades.Producto;

import java.util.List;

public class AdapterCompras  extends RecyclerView.Adapter<AdapterCompras.ComprasHolder> {
    List<Producto> listaUsuarios;
    int id_producto;
    String nombre;
    public class ComprasHolder extends RecyclerView.ViewHolder{

        public TextView nombre;
        public TextView precioProducto;
        public TextView descripcionProducto;
        public ImageView imagen;

        public ComprasHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre_producto);
            precioProducto = (TextView) itemView.findViewById(R.id.precio_producto);
            descripcionProducto = (TextView) itemView.findViewById(R.id.descripcion_producto);
            imagen = (ImageView) itemView.findViewById(R.id.miniatura_producto);
        }
    }

    public AdapterCompras(List<Producto> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public ComprasHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_producto,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new ComprasHolder(vista);
    }

    @Override
    public void onBindViewHolder(ComprasHolder holder, int position) {
        holder.nombre.setText(listaUsuarios.get(position).getNombre_producto().toString());
        holder.precioProducto.setText("$"+listaUsuarios.get(position).getPrecio());
        holder.descripcionProducto.setText(listaUsuarios.get(position).getDescripcion().toString());
        id_producto = listaUsuarios.get(position).getId_producto();

        nombre = holder.nombre.getText().toString().trim();

        if (listaUsuarios.get(position).getImagen()!=null){
            holder.imagen.setImageBitmap(listaUsuarios.get(position).getImagen());
        }else{
            holder.imagen.setImageResource(R.drawable.ic_carrito);
        }
        holder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "el id es"+nombre, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), ClienteDetalleActivity.class);
                intent.putExtra("nombre", nombre);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }




}
