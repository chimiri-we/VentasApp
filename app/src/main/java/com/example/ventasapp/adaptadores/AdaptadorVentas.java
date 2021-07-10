package com.example.ventasapp.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventasapp.R;
import com.example.ventasapp.entidades.Venta;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdaptadorVentas extends RecyclerView.Adapter<AdaptadorVentas.ViewHolder> {

    List<Venta> ventaList;

    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView idUsuario;
        public TextView totalVenta;
        public TextView fechaVenta;
        public TextView statusVenta;
        public ImageView imageView;


        public ViewHolder(View v) {
            super(v);
            idUsuario = (TextView) v.findViewById(R.id.dtIdusuario);
            totalVenta = (TextView) v.findViewById(R.id.dtTotalVenta);
            fechaVenta = (TextView) v.findViewById(R.id.dtTelefono);
            statusVenta = (TextView) v.findViewById(R.id.dtDireccion);
            imageView = v.findViewById(R.id.icono_indicador_derecho);

        }
    }
    public AdaptadorVentas(Context context, List<Venta> ventaList){
        this.context = context;
        this.ventaList = ventaList;

    }
    @NonNull
    @NotNull
    @Override
    public AdaptadorVentas.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_compras, parent, false);
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull @NotNull AdaptadorVentas.ViewHolder holder, int position) {

        Venta venta = (Venta) ventaList.get(position);
        String idusuario = String.valueOf(venta.getId_usuario());
        String total = String.valueOf(venta.getTotal_venta());
        String status = String.valueOf(venta.getStatus());


        holder.idUsuario.setText(idusuario);
        holder.totalVenta.setText(total);
       // holder.statusVenta.setText(status);
        holder.fechaVenta.setText(venta.getFecha());

     //   holder.departamento.setText(ventaList.get(position).getStatus());

       int estatus = ventaList.get(position).getStatus();
        if (estatus == 0){
            holder.statusVenta.setText("Pendiente de entrega");
        }else {
            holder.statusVenta.setText("Paquete entregado");
        }


    }

    @Override
    public int getItemCount() {
        return ventaList.size();
    }
}
