package com.example.ventasapp.detalles;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.ventasapp.R;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.Producto;
import com.example.ventasapp.entidades.VolleySingleton;
import com.example.ventasapp.modelo.Comida;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ClienteDetalleFragment extends Fragment {

    String id;
   private static ProgressDialog progreso;
    private static JsonObjectRequest jsonObjectRequest;
 private static String idProducto;
Producto producto;
BaseDatos bdLocal;
    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mPhoneNumber;
    private TextView mSpecialty, txtNombre;
    String descripcion;


    public ClienteDetalleFragment() {
        // Required empty public constructor
    }

    public static ClienteDetalleFragment newInstance(String id) {
ClienteDetalleFragment fragment = new ClienteDetalleFragment();

        Bundle extras = new Bundle();
        idProducto = extras.getString("id", id);

        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            id = getArguments().getString(idProducto);

        }
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

            Toast.makeText(getContext(), "fragment "+idProducto, Toast.LENGTH_SHORT).show();
        bdLocal = new BaseDatos(getContext());
       producto = bdLocal.verProducto(Integer.parseInt(idProducto));

       if (producto != null) {

           txtNombre.setText(producto.getNombre_producto());
           mPhoneNumber.setText("$"+producto.getPrecio());
       }
        }else {
            Toast.makeText(getContext(), "no esta pasando el id", Toast.LENGTH_SHORT).show();

        }

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