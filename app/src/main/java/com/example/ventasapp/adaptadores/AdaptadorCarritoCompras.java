package com.example.ventasapp.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventasapp.R;
import com.example.ventasapp.entidades.DetalleVenta;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdaptadorCarritoCompras  extends RecyclerView.Adapter<AdaptadorCarritoCompras.ViewHolder> {

    List<DetalleVenta> detalleVentaList;

    Context context;
    int id_detalle;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView detalleNombreProdcuto, detalleCantidad, detalleTotal;
        public TextView detallePrecioProducto, detalleProducto, detalleFecha;
        public ImageView imagen, agregar;


        public ViewHolder(View v) {
            super(v);


            detalleNombreProdcuto = (TextView) v.findViewById(R.id.detalle_nombre_producto);
            detallePrecioProducto = (TextView) v.findViewById(R.id.texto_precio_detalle);
            detalleCantidad = (TextView) v.findViewById(R.id.texto_canridad_detalle);
            detalleTotal = (TextView) v.findViewById(R.id.texto_total);
            detalleProducto = (TextView) v.findViewById(R.id.texto_detalle_producto);
            detalleFecha = (TextView) v.findViewById(R.id.texto_fecha_detalle);
            imagen = (ImageView) v.findViewById(R.id.icono_detalle_producto);

        }
    }

    public AdaptadorCarritoCompras(Context context, List<DetalleVenta> detalleVentaList){
        this.context = context;
        this.detalleVentaList = detalleVentaList;

    }
    @NonNull
    @NotNull
    @Override
    public AdaptadorCarritoCompras.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle_venta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdaptadorCarritoCompras.ViewHolder holder, int position) {

        DetalleVenta detalleVenta = (DetalleVenta) detalleVentaList.get(position);
        id_detalle = detalleVentaList.get(position).getId_detalle();
        holder.detalleNombreProdcuto.setText(detalleVenta.getNombre_producto());
        holder.detallePrecioProducto.setText("$ "+detalleVenta.getPrecio());
        holder.detalleCantidad.setText(detalleVenta.getCantidad());
        holder.detalleTotal.setText("$ "+detalleVenta.getTotal());
      //  holder.detalleProducto.setText(detalleVenta.get);
      //  holder.detalleFecha.setText(detalleVenta.get);


    }

    @Override
    public int getItemCount() {
        return detalleVentaList.size();
    }
}
