package com.example.ventasapp.detalles;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ventasapp.R;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.Producto;
import com.example.ventasapp.modelo.Comida;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

public class ClienteDetalleFragment extends Fragment {

    int id;
 private static String idProducto;
Producto producto;
BaseDatos bdLocal;
    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mPhoneNumber;
    private TextView mSpecialty, txtNombre;


    public ClienteDetalleFragment() {
        // Required empty public constructor
    }

    public static ClienteDetalleFragment newInstance(String id) {
ClienteDetalleFragment fragment = new ClienteDetalleFragment();

        Bundle extras = new Bundle();
        idProducto = extras.getString("id", id);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clientes_detalles, container, false);
        mCollapsingView = (CollapsingToolbarLayout) requireActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) requireActivity().findViewById(R.id.iv_avatar);
        mPhoneNumber = root.findViewById(R.id.tv_phone_number);
        mSpecialty = root.findViewById(R.id.tv_specialty);
        txtNombre = root.findViewById(R.id.tv_bio);


        if (idProducto != null){
        bdLocal = new BaseDatos(getContext());
       producto = bdLocal.verProducto(Integer.parseInt(idProducto));

       if (producto != null) {
           Toast.makeText(getContext(), "el precio"+producto, Toast.LENGTH_SHORT).show();
           txtNombre.setText(producto.getNombre_producto());
           mPhoneNumber.setText("$"+producto.getPrecio());
       }else {
           Toast.makeText(getContext(), "no esta pasando el id", Toast.LENGTH_SHORT).show();
       }
        }
/*           Glide.with(this)
                    .load(producto.getUrlImagen())
                    .centerCrop()
                    .into(mAvatar);
            txtNombre.setText(producto.getNombre_producto());
           mPhoneNumber.setText("$"+producto.getPrecio());
*/

     //   mClientesDbHelper = new Producto(getActivity());



        return root;
    }


    private void showLawyer(Producto producto) {
        mCollapsingView.setTitle(producto.getNombre_producto());
        Glide.with(this)
                .load(producto.getUrlImagen())
                .centerCrop()
                .into(mAvatar);
        mPhoneNumber.setText(producto.getNombre_producto());
        mSpecialty.setText("$"+producto.getPrecio());

    }


    private void showLawyersScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar información", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar informacion", Toast.LENGTH_SHORT).show();
    }


}