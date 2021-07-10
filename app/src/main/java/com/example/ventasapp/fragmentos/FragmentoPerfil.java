package com.example.ventasapp.fragmentos;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ventasapp.R;
import com.example.ventasapp.datos.BaseDatos;
import com.example.ventasapp.entidades.Usuarios;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

/**
 * Fragmento para la pestaña "PERFIL" De la sección "Mi Cuenta"
 */
public class FragmentoPerfil extends Fragment {

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen
    File fileImagen;
    Bitmap bitmap;
    ImageView imgFoto, imgEdit, imgEditdirec, imgEditContra;
    CircleImageView imgperfil;

    private final int MIS_PERMISOS = 100;
    private static final int COD_SELECCIONA = 10;
    private static final int COD_FOTO = 20;
    BaseDatos bdLocal;
    Usuarios usuarios;
    public FragmentoPerfil() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragmento_perfil, container, false);

        TextView nombre = v.findViewById(R.id.texto_nombre);
        TextView correo = v.findViewById(R.id.texto_email);
        TextView tvusuario = v.findViewById(R.id.texto_usuario);
        TextView direccion = v.findViewById(R.id.texto_direccion_usuario);
        TextView telefono = v.findViewById(R.id.texto_telefono);
        TextView direccionCalle = v.findViewById(R.id.tv_calle);
        TextView direccionColonia = v.findViewById(R.id.tv_colonia);
        imgFoto = v.findViewById(R.id.ic_cambiar_imagen);
        imgperfil = v.findViewById(R.id.img_perfil);
        imgEdit = v.findViewById(R.id.icono_edit_datos);
        imgEditContra = v.findViewById(R.id.icono_indicador_derecho);
        imgEditdirec = v.findViewById(R.id.icono_editar_derecho);
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        usuarios = new Usuarios();
        verdatosUsuario();

        if (usuarios != null) {

            nombre.setText(usuarios.getNombre());
            correo.setText(usuarios.getCorreo());
            tvusuario.setText(usuarios.getUser());
            direccion.setText(usuarios.getDireccion());
            telefono.setText(usuarios.getTelefono());
            direccionCalle.setText(usuarios.getCalle());
            direccionColonia.setText(usuarios.getColonia());
//            String urlImagen = usuarios.getUrlImagen().toLowerCase();

            Glide.with(requireContext())
                    .asBitmap()
                    .load(usuarios.getUrlImagen())
                    .error(R.drawable.ic_persona)
                    .centerCrop()
                    .into(imgperfil);
        }
        imgFoto.setOnClickListener(v14 -> tomarFoto());

        imgEdit.setOnClickListener(v1 -> editarDatosUsuario(usuarios));
        imgEditdirec.setOnClickListener(v12 -> editarDireccion(usuarios));

        imgEditContra.setOnClickListener(v13 -> editarContrasena(usuarios));

        return v;
    }

    private void editarContrasena(Usuarios usuarios) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View viewCon = inflater.inflate(R.layout.dialog_contrasena, null, false);
        TextInputEditText edtContra = viewCon.findViewById(R.id.edt_contras);
        TextInputLayout layout = viewCon.findViewById(R.id.label_password);
        TextInputEditText edtConfirCon = viewCon.findViewById(R.id.edt_confirmar);
        if (usuarios != null){
            edtContra.setText(usuarios.getPassword());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(viewCon);
        builder.create();

        builder.setPositiveButton("GUARDAR DATOS", (dialog, which) -> {
            final String contra = Objects.requireNonNull(edtContra.getText()).toString();
            final String confirmar = edtContra.getText().toString();
            if (TextUtils.isEmpty(confirmar)) {
                Toast.makeText(getContext(), "Algo salió mal. Verifique sus valores de entrada", Toast.LENGTH_LONG).show();
            } else {
                bdLocal = new BaseDatos(requireContext().getApplicationContext());
                assert usuarios != null;
                bdLocal.actualizarContrasena(new
                        Usuarios(usuarios.getId_usuario(), contra));
                ((Activity) getContext()).finish();
                getContext().startActivity(((Activity)
                        getContext()).getIntent());
            }
        });
        builder.setNegativeButton("CANCELAR", (dialog, which) -> Toast.makeText(getContext(), "Tarea Cancelada",Toast.LENGTH_LONG).show());

        builder.show();
    }

    private void editarDireccion(Usuarios usuarios) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View viewDir = inflater.inflate(R.layout.dialog_direccion, null, false);
        TextInputEditText edtDireccionColonia = viewDir.findViewById(R.id.edt_direcci_colonia);
        TextInputEditText edtDireccionCiudad = viewDir.findViewById(R.id.edt_direcci_ciudad);
        TextInputEditText edtDireccionCalle = viewDir.findViewById(R.id.edt_direcci_calle);

        if (usuarios != null){
            edtDireccionCiudad.setText(usuarios.getDireccion());
            edtDireccionCalle.setText(usuarios.getCalle());
            edtDireccionColonia.setText(usuarios.getColonia());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(viewDir);
        builder.create();

        builder.setPositiveButton("GUARDAR DATOS", (dialog, which) -> {
            final String ciudad = Objects.requireNonNull(edtDireccionCiudad.getText()).toString().trim();
            final String newcalle = Objects.requireNonNull(edtDireccionCalle.getText()).toString().trim();
            final String newcolonia = Objects.requireNonNull(edtDireccionColonia.getText()).toString().trim();

            if (TextUtils.isEmpty(ciudad)) {
                Toast.makeText(getContext(), "Algo salió mal. Verifique sus valores de entrada", Toast.LENGTH_LONG).show();
            } else {
                bdLocal = new BaseDatos(requireContext().getApplicationContext());
                assert usuarios != null;
                bdLocal.actualizarDireccion(new
                        Usuarios(usuarios.getId_usuario(), ciudad, newcalle, newcolonia));

                ((Activity) requireContext()).finish();
                requireContext().startActivity(((Activity)
                        requireContext()).getIntent());
            }
        });
        builder.setNegativeButton("CANCELAR", (dialog, which) -> Toast.makeText(getContext(), "Tarea Cancelada",Toast.LENGTH_LONG).show());

        builder.show();
    }

    private void editarDatosUsuario(Usuarios usuarios) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_registro_perfil, null, false);
        TextInputEditText edtNombreCliente = view.findViewById(R.id.edt_nombre_usuario);
        TextInputEditText edtEmailCliente = view.findViewById(R.id.edt_correo);
        TextInputEditText edtUsername = view.findViewById(R.id.edt_username);
        TextInputEditText edtTelefono = view.findViewById(R.id.edt_telefono_user);
        if (usuarios != null){
            edtEmailCliente.setText(usuarios.getCorreo());
            edtNombreCliente.setText(usuarios.getNombre());
            edtUsername.setText(usuarios.getUser());
            edtTelefono.setText(usuarios.getTelefono());

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);
        builder.create();

        builder.setPositiveButton("GUARDAR DATOS", (dialog, which) -> {
            final String nombre = Objects.requireNonNull(edtNombreCliente.getText()).toString();
            final String telefono = Objects.requireNonNull(edtTelefono.getText()).toString();
            final String correo = Objects.requireNonNull(edtEmailCliente.getText()).toString();
            final String user = Objects.requireNonNull(edtUsername.getText()).toString();
            if (TextUtils.isEmpty(nombre)) {
                Toast.makeText(getContext(), "Algo salió mal. Verifique sus valores de entrada", Toast.LENGTH_LONG).show();
            } else {
                bdLocal = new BaseDatos(requireContext().getApplicationContext());
                assert usuarios != null;
                bdLocal.actualizarUsuario(new
                        Usuarios(usuarios.getId_usuario(), nombre, telefono, correo, user));
                ((Activity) requireContext()).finish();
                requireContext().startActivity(((Activity)
                        requireContext()).getIntent());
            }
        });
        builder.setNegativeButton("CANCELAR", (dialog, which) -> Toast.makeText(getContext(), "Tarea Cancelada",Toast.LENGTH_LONG).show());

        builder.show();
    }

    @SuppressLint("Recycle")
    private void verdatosUsuario() {
        bdLocal = new BaseDatos(getContext());
        SQLiteDatabase db = bdLocal.getWritableDatabase();

        Cursor cursor;
        cursor = db.rawQuery("select * from Usuario", null);
        if (cursor.moveToFirst()) {


            usuarios.setId_usuario(cursor.getInt(0));
            //usuarios.setId_R(cursor.getInt(1));
            usuarios.setNombre(cursor.getString(1));
            usuarios.setUrlImagen(cursor.getString(2));
            usuarios.setDireccion(cursor.getString(3));
            usuarios.setCorreo(cursor.getString(4));
            usuarios.setTelefono(cursor.getString(5));
            usuarios.setUser(cursor.getString(6));
            usuarios.setPassword(cursor.getString(7));
            usuarios.setColonia(cursor.getString(8));
            usuarios.setCalle(cursor.getString(9));


        }
    }

    public void tomarFoto() {
        mostrarDialogOpciones();
    }

    private void mostrarDialogOpciones() {

        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
        builder.setTitle("Elige una Opción");
        builder.setItems(opciones, (dialogInterface, i) -> {
            if (opciones[i].equals("Tomar Foto")){
                abriCamara();
            }else{
                if (opciones[i].equals("Elegir de Galeria")){
                    @SuppressLint("IntentReset") Intent intent=new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(Intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                }else{
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    private void abriCamara() {
        File miFile=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada=miFile.exists();

        if(!isCreada){
            isCreada=miFile.mkdirs();
        }

        if(isCreada){
            long consecutivo= System.currentTimeMillis()/1000;
            String nombre= consecutivo +".jpg";

            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;//indicamos la ruta de almacenamiento

            fileImagen=new File(path);

            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));

            ////
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            {
                String authorities= requireContext().getPackageName()+".provider";
                Uri imageUri= FileProvider.getUriForFile(requireContext(),authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            }
            startActivityForResult(intent,COD_FOTO);

            ////

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case COD_SELECCIONA:
                Uri miPath=data.getData();
                imgperfil.setImageURI(miPath);
                BaseDatos bdLocal = new BaseDatos(requireContext().getApplicationContext());
                bdLocal.obtenerRutaImagen(miPath);

                try {
                    bitmap=MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(),miPath);
                    imgperfil.setImageBitmap(bitmap);
                    if (imgperfil != null){

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Ruta "+imgperfil, Toast.LENGTH_LONG).show();


                break;
            case COD_FOTO:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        (path, uri) -> Log.i("Path",""+path));


                bitmap= BitmapFactory.decodeFile(path);
                imgperfil.setImageBitmap(bitmap);
                if (imgperfil != null){
                    bdLocal = new BaseDatos(requireContext().getApplicationContext());
                    bdLocal.guardarImagen(path);
                }

                break;
        }
        bitmap=redimensionarImagen(bitmap);

    }


    private Bitmap redimensionarImagen(Bitmap bitmap) {

        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho> (float) 600048 || alto> (float) 600048){
            float escalaAncho= (float) 600048 /ancho;
            float escalaAlto= (float) 600048 /alto;

            Matrix matrix=new Matrix();
            matrix.postScale(escalaAncho,escalaAlto);

            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

        }else{
            return bitmap;
        }


    }


    //permisos
    ////////////////

    private boolean solicitaPermisosVersionesSuperiores() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){//validamos si estamos en android menor a 6 para no buscar los permisos
            return true;
        }

        //validamos si los permisos ya fueron aceptados
        if((requireContext().checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)&& requireContext().checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED){
            return true;
        }


        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)||(shouldShowRequestPermissionRationale(CAMERA)))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MIS_PERMISOS);
        }

        return false;//implementamos el que procesa el evento dependiendo de lo que se defina aqui
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MIS_PERMISOS){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){//el dos representa los 2 permisos
                Toast.makeText(getContext(),"Permisos aceptados",Toast.LENGTH_SHORT).show();
                // btnFoto.setEnabled(true);
            }
        }else{
            solicitarPermisosManual();
        }
    }


    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(requireContext());//estamos en fragment
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            private String packageName;

            public String getPackageName() {
                return packageName;
            }

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",this.getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(requireContext());
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", (dialogInterface, i) -> requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100));
        dialogo.show();
    }

}
