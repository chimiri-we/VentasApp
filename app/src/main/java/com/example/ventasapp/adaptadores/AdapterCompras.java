package com.example.ventasapp.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ventasapp.R;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.detalles.ClienteDetalleActivity;
import com.example.ventasapp.entidades.DetalleVenta;
import com.example.ventasapp.entidades.Producto;
import com.example.ventasapp.entidades.Venta;
import com.example.ventasapp.ui.CarritoComprasActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterCompras  extends RecyclerView.Adapter<AdapterCompras.ComprasHolder> {
    List<Producto> productoList;


    int id_producto;
    int precio;
    String id;
    String nombre;
    Context context;
    String piezas;
    String formattedDate;
    int status = 0;
    int id_venta;
    Bitmap img;
    public class ComprasHolder extends RecyclerView.ViewHolder{

        public TextView nombreProducto;
        public TextView precioProducto;
        public TextView descripcionProducto;
        public ImageView imagen;
        public CardView btCard;




        public ComprasHolder(View itemView) {
            super(itemView);
            btCard = itemView.findViewById(R.id.agregar_carrito);
            nombreProducto = (TextView) itemView.findViewById(R.id.nombre_producto);
            precioProducto = (TextView) itemView.findViewById(R.id.precio_producto);
            descripcionProducto = (TextView) itemView.findViewById(R.id.descripcion_producto);
            imagen = (ImageView) itemView.findViewById(R.id.miniatura_producto);
        }
    }

    public AdapterCompras(Context context, List<Producto> productoList) {
        this.context = context;
        this.productoList = productoList;
    }

    @Override
    public ComprasHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_producto,parent,false);

        return new ComprasHolder(vista);
    }

    @Override
    public void onBindViewHolder(ComprasHolder holder, int position) {
        Producto producto = (Producto) productoList.get(position);

        holder.nombreProducto.setText(producto.getNombre_producto().toString());
        holder.precioProducto.setText("$"+ producto.getPrecio());
        holder.descripcionProducto.setText(producto.getCaracteristicas().toString());
        id_producto = productoList.get(position).getId_producto();

        precio = (int) productoList.get(position).getPrecio();
        nombre = productoList.get(position).getNombre_producto();
        id = String.valueOf(productoList.get(position).getId_producto());

        if (productoList.get(position).getImagen()!=null){
            holder.imagen.setImageBitmap(productoList.get(position).getImagen());
        }else{
            holder.imagen.setImageResource(R.drawable.ic_carrito);
        }
        holder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  Toast.makeText(v.getContext(), "el id es que estoy pasando es "+id, Toast.LENGTH_SHORT).show();

              img = productoList.get(position).getImagen();
                Intent intent = new Intent(v.getContext(), ClienteDetalleActivity.class);
                intent.putExtra("image", img);
                intent.putExtra("nombre", nombre);
                intent.putExtra("id", id);
                v.getContext().startActivity(intent);
            }
        });

        holder.btCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflarOpciones(nombre, id_producto, precio, img);
            }
        });
    }

    private void inflarOpciones(String nombre, int id_producto, int precio, Bitmap img) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.dialogo, null);
        int cantPro = 1;



        ImageView imgMiniaturaPro = subView.findViewById(R.id.img_producto);
        Glide.with(context)
                .load(img)
                .centerCrop()
                .into(imgMiniaturaPro);
        CircleImageView menosUno = subView.findViewById(R.id.btn_menosuno);
        CircleImageView masUno = subView.findViewById(R.id.btn_masuno);
        TextView mensaje = subView.findViewById(R.id.titulo_producto);
        TextView tvprecio = subView.findViewById(R.id.precio);
        EditText cantidad = subView.findViewById(R.id.tv_cantidad_compra);

        mensaje.setText(nombre);
        tvprecio.setText("$"+(String.valueOf(precio)));
        cantidad.setText(String.valueOf(cantPro));
        masUno.setOnClickListener(v -> {

            int suma = Integer.parseInt(cantidad.getText().toString());
            int resultado = suma+1;
            cantidad.setText(String.valueOf(resultado));
        });
        menosUno.setOnClickListener(v -> {

            int resta = Integer.parseInt(cantidad.getText().toString());
            int residuo =  resta-1;
            cantidad.setText(String.valueOf(residuo));

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("AGREGAR",  (dialog, which) -> {


           piezas = cantidad.getText().toString();
            String costo = String.valueOf(precio);

            int dato1 = Integer.parseInt(String.valueOf(precio));
            int dato2 = Integer.parseInt(cantidad.getText().toString());
            int suma = dato1 * dato2;
            String resultado = String.valueOf(suma);


            BaseDatos bdLocal = new BaseDatos(context.getApplicationContext());
            Venta idVen = bdLocal.ultimaVenta();

            int idventa = 1+(idVen.getId_venta());
            DetalleVenta detalleVenta = new DetalleVenta(idventa, id_producto, nombre, costo, piezas, resultado);
           bdLocal.agregardetalleVenta(detalleVenta);

            //Toast.makeText(context, "el id de la venta es el id "+idventa, Toast.LENGTH_LONG).show();

            Intent mintent = new Intent(context, CarritoComprasActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id_producto", id_producto);

            mintent.putExtras(bundle);
           // Toast.makeText(context, "envio el id "+id_producto, Toast.LENGTH_SHORT).show();
            context.startActivity(mintent);


        });
        builder.setNegativeButton("CANCELAR", (dialog, which) -> Toast.makeText(context, "Tarea Cancelada",Toast.LENGTH_LONG).show());

        builder.show();

    }

 /*   private void generarDetalleVenta(EditText cantidad) {
        piezas = cantidad.getText().toString();
        String costo = String.valueOf(precio);

        int dato1 = Integer.parseInt(String.valueOf(precio));
        int dato2 = Integer.parseInt(cantidad.getText().toString());
        int suma = dato1 * dato2;
        String resultado = String.valueOf(suma);

        BaseDatos bdLocal = new BaseDatos(context.getApplicationContext());
        DetalleVenta detalleVenta = new DetalleVenta(id_venta, id_producto, nombre, costo, piezas, resultado);
        bdLocal.agregardetalleVenta(detalleVenta);

        // Toast.makeText(context, "envio el id "+id_detalle, Toast.LENGTH_LONG).show();

        Intent mintent = new Intent(context, CarritoComprasActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id_venta", id_venta);

        mintent.putExtras(bundle);
        Toast.makeText(context, "envio el id de la venta "+id_venta, Toast.LENGTH_SHORT).show();
        context.startActivity(mintent);

    }

*/
    @Override
    public int getItemCount() {
        return productoList.size();
    }




}
