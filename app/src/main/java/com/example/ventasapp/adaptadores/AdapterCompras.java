package com.example.ventasapp.adaptadores;

import android.content.Context;
import android.content.Intent;
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
    List<Producto> listaUsuarios;

    private static final String TABLE_DETALLE_VENTA = "DetalleVenta";
    int id_producto;
    int precio;
    String nombre;
    Context context;
    String piezas;
    String formattedDate;
    int status = 0;
    int id_venta;
    public class ComprasHolder extends RecyclerView.ViewHolder{

        public TextView nombre;
        public TextView precioProducto;
        public TextView descripcionProducto;
        public ImageView imagen;
        public CardView btCard;




        public ComprasHolder(View itemView) {
            super(itemView);
            btCard = itemView.findViewById(R.id.agregar_carrito);
            nombre = (TextView) itemView.findViewById(R.id.nombre_producto);
            precioProducto = (TextView) itemView.findViewById(R.id.precio_producto);
            descripcionProducto = (TextView) itemView.findViewById(R.id.descripcion_producto);
            imagen = (ImageView) itemView.findViewById(R.id.miniatura_producto);
        }
    }

    public AdapterCompras(Context context, List<Producto> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public ComprasHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_producto,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
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

        precio = (int) listaUsuarios.get(position).getPrecio();
        nombre = holder.nombre.getText().toString().trim();

        if (listaUsuarios.get(position).getImagen()!=null){
            holder.imagen.setImageBitmap(listaUsuarios.get(position).getImagen());
        }else{
            holder.imagen.setImageResource(R.drawable.ic_carrito);
        }
        holder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(v.getContext(), "el id es"+nombre, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), ClienteDetalleActivity.class);
                intent.putExtra("nombre", nombre);
                v.getContext().startActivity(intent);
            }
        });

        holder.btCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inflarOpciones(nombre, id_producto, precio);
            }
        });
    }

    private void inflarOpciones(String nombre, int id_producto, int precio) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.dialogo, null);
        int cantPro = 1;



        CircleImageView menosUno = subView.findViewById(R.id.btn_menosuno);
        CircleImageView masUno = subView.findViewById(R.id.btn_masuno);
        TextView mensaje = subView.findViewById(R.id.titulo_producto);
        TextView tvprecio = subView.findViewById(R.id.precio);
        EditText cantidad = subView.findViewById(R.id.tv_cantidad_compra);
        mensaje.setText(nombre);
        tvprecio.setText("$"+(String.valueOf(precio)));
        cantidad.setText(String.valueOf(cantPro));
        masUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int suma = Integer.parseInt(cantidad.getText().toString());
                int resultado = suma+1;
                cantidad.setText(String.valueOf(resultado));
            }
        });
        menosUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int resta = Integer.parseInt(cantidad.getText().toString());
                int residuo =  resta-1;
                cantidad.setText(String.valueOf(residuo));

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("AGREGAR",  (dialog, which) -> {
           /* Date fechaActual= Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            formattedDate = df.format(fechaActual);
            Usuarios usuarios;
            BaseDatos bdLocal = new BaseDatos(context.getApplicationContext());
            usuarios = bdLocal.verdatosUsuario();
            if (usuarios != null){
                int id_usuario = usuarios.getId_usuario();
                Venta nuevaventa = new Venta(id_usuario, formattedDate, status);
                bdLocal.generarVenta(nuevaventa);

                id_venta = nuevaventa.getId_venta();

                generarDetalleVenta(cantidad);
            }

*/

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

            Toast.makeText(context, "el id de la venta es el id "+idventa, Toast.LENGTH_LONG).show();

            Intent mintent = new Intent(context, CarritoComprasActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id_producto", id_producto);

            mintent.putExtras(bundle);
           // Toast.makeText(context, "envio el id "+id_producto, Toast.LENGTH_SHORT).show();
            context.startActivity(mintent);


        });
        builder.show();

    }

    private void generarDetalleVenta(EditText cantidad) {
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


    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }




}
