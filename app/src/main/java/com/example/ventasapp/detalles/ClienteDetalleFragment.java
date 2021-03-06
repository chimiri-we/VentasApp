package com.example.ventasapp.detalles;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.ventasapp.R;
import com.example.ventasapp.adaptadores.AdaptadorCarritoCompras;
import com.example.ventasapp.adaptadores.AdapterDetalleImagenes;
import com.example.ventasapp.adaptadores.NotificacionesAdapter;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.DetalleVenta;
import com.example.ventasapp.entidades.Producto;
import com.example.ventasapp.entidades.VolleySingleton;
import com.example.ventasapp.fragmentos.FragmenNotificacion;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClienteDetalleFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    String id;
   private static ProgressDialog progreso;
    private static JsonObjectRequest jsonObjectRequest;
 private static String idProducto;
Producto producto;
Producto p;
    String IDPRODUCTO, NOMBRE, DESCRIPCION, PRECIO;
    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView precioProducto;
    private TextView descripcionProducto, txtNombre;

    ImageView imageView;
    RecyclerView reciclerImagenes;
AdapterDetalleImagenes adapterDetalleImagenes;
    ArrayList<Producto> ArraylistaPro;

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
    private NotificacionesAdapter studentAdapter;
    private List studentDataList = new ArrayList<>();
    @TargetApi(Build.VERSION_CODES.O)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            id = getArguments().getString(idProducto);

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clientes_detalles, container, false);
        mCollapsingView = (CollapsingToolbarLayout) requireActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) requireActivity().findViewById(R.id.iv_avatar);
        precioProducto = root.findViewById(R.id.tv_phone_number);
        descripcionProducto = root.findViewById(R.id.tv_specialty);
        imageView = root.findViewById(R.id.img_remplazo_recicler);
        txtNombre = root.findViewById(R.id.tv_bio);
      //  ArraylistaPro = new ArrayList<>();
        reciclerImagenes = root.findViewById(R.id.recicler_imagenes);
        studentAdapter = new NotificacionesAdapter(studentDataList,getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        reciclerImagenes.setLayoutManager(manager);
        reciclerImagenes.setHasFixedSize(true);
        reciclerImagenes.setAdapter(studentAdapter);
        consultarProducto();
        StudentDataPrepare();
       /* LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        reciclerImagenes.setLayoutManager(layoutManager);
        reciclerImagenes.setHasFixedSize(true);



        if (ArraylistaPro.size() > 0){
            reciclerImagenes.setVisibility(View.VISIBLE);
            adapterDetalleImagenes = new AdapterDetalleImagenes(getContext(), ArraylistaPro);
            reciclerImagenes.setAdapter(adapterDetalleImagenes);
            imageView.setVisibility(View.GONE);
        }else {

            reciclerImagenes.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);

        }

/*
        if (idProducto != null) {
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
*/
        return root;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void StudentDataPrepare() {
        FragmenNotificacion.studentData data = new FragmenNotificacion.studentData("sai", 25);
        studentDataList.add(data);
        data = new FragmenNotificacion.studentData("sai raj", 25);
        studentDataList.add(data);
        data = new FragmenNotificacion.studentData("raghu", 20);
        studentDataList.add(data);
        data = new FragmenNotificacion.studentData("raj", 28);
        studentDataList.add(data);
        data = new FragmenNotificacion.studentData("amar", 15);
        studentDataList.add(data);
        data = new FragmenNotificacion.studentData("bapu", 19);
        studentDataList.add(data);
        data = new FragmenNotificacion.studentData("chandra", 52);
        studentDataList.add(data);
        data = new FragmenNotificacion.studentData("deraj", 30);
        studentDataList.add(data);
        data = new FragmenNotificacion.studentData("eshanth", 28);
        studentDataList.add(data);
        Collections.sort(studentDataList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }


        });
    }


    private void consultarProducto() {
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Consultando...");
        progreso.show();



        String url="https://servicioparanegocio.es/ventasApp/consultas/consultar_ProductoPorId.php?id_producto="+idProducto;


        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();

        //    Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show();

        p = new Producto();

        JSONArray json=response.optJSONArray("producto");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            p.setId_producto(jsonObject.optInt("Id_producto"));
            p.setNombre_producto(jsonObject.optString("nombre"));
            p.setPrecio(jsonObject.optInt("Precio"));
            p.setDescripcion(jsonObject.optString("Descripcion"));
         //  ArraylistaPro.add(p);

            IDPRODUCTO = String.valueOf(p.getId_producto());
            DESCRIPCION = p.getDescripcion();
            NOMBRE = p.getNombre_producto();
            PRECIO = String.valueOf(p.getPrecio());

            txtNombre.setText(NOMBRE);
            descripcionProducto.setText(DESCRIPCION);
            precioProducto.setText(PRECIO);
           // Toast.makeText(getContext(),"Mensaje: "+NOMBRE,Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void showLawyer(Producto producto) {
        mCollapsingView.setTitle(producto.getNombre_producto());
        Glide.with(this)
                .load(producto.getUrlImagen())
                .centerCrop()
                .into(mAvatar);
        precioProducto.setText(producto.getNombre_producto());
        descripcionProducto.setText("$"+producto.getPrecio());

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
                "Error al cargar informaci??n", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar informacion", Toast.LENGTH_SHORT).show();
    }


}