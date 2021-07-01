package com.example.ventasapp.adaptadores;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventasapp.R;
import com.example.ventasapp.detalles.ClienteDetalleActivity;
import com.example.ventasapp.modelo.Comida;
import com.example.ventasapp.ui.CarritoActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import static com.example.ventasapp.R.drawable.ic_carrito;

/**
 * Adaptador para comidas usadas en la sección "Categorías"
 */
public class AdaptadorCategorias
        extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder> {


    private final List<Comida> items;
Context context;
    String piezas;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView nombre;
        public TextView precio;
        public ImageView imagen, agregar;

        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.nombre_comida);
            precio = (TextView) v.findViewById(R.id.precio_comida);
            imagen = (ImageView) v.findViewById(R.id.miniatura_comida);

        }
    }


    public AdaptadorCategorias(Context context, List<Comida> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_categorias, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Comida item = items.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.precio.setText("$" + item.getPrecio());

        viewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = item.getNombre();
                nuevaacticyty(item);

            }
        });
    }

    private void nuevaacticyty(Comida item) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.dialogo, null);
        int cantPro = 1;
        ImageView menosUno = subView.findViewById(R.id.btn_menosuno);
        ImageView masUno = subView.findViewById(R.id.btn_masuno);
        TextView mensaje = subView.findViewById(R.id.titulo_producto);
        EditText cantidad = subView.findViewById(R.id.tv_cantidad_compra);

        mensaje.setText(item.getNombre());
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

        builder.setTitle("Agrega al Carrito");
        builder.setMessage("Proceso de compra");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("AGREGAR", (dialog, which) -> {
            piezas = cantidad.getText().toString();
            detalle(item);
            // finish();
            //  startActivity(getContext());

        });
        builder.show();
    }

    private void detalle(Comida item) {
        Intent miIntent = new Intent(context, CarritoActivity.class);
        miIntent.putExtra("nombre", item.getNombre());
        miIntent.putExtra("imagen", item.getIdDrawable());
        miIntent.putExtra("precio", item.getPrecio());
        miIntent.putExtra("cantidad", piezas);
        Toast.makeText(context, " "+piezas, Toast.LENGTH_SHORT).show();
        context.startActivity(miIntent);
    }


}
