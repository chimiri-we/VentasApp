package com.example.ventasapp.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ventasapp.R;
import com.example.ventasapp.entidades.Producto;
import com.example.ventasapp.entidades.Usuarios;

import java.util.ArrayList;

public class BaseDatos extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "Ventas.db";

    private static final String TABLE_USUARIO = "Usuario";
    private static final String TABLE_PRODUCTO = "Producto";


    public BaseDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String dbUsuario="create table Usuario("+
                "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombre TEXT,"+
                "uri_imagen TEXT,"+
                "direccion TEXT,"+
                "correo TEXT,"+
                "telefono TEXT,"+
                "user TEXT,"+
                "password TEXT)";
        db.execSQL(dbUsuario);

        String dbProducto="create table Producto("+
                "id_producto INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nombre_producto TEXT,"+
                "urlImagen TEXT,"+
                "precio FLOAT)";
        db.execSQL(dbProducto);

        loadDummyData(db);
    }

    private void loadDummyData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
       // values.put("id_producto", producto.getId_producto());
        values.put("nombre_producto", "Camarones Tismados");
        values.put("urlImagen", R.drawable.camarones);
        values.put("precio", 65);
      //  db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTO, null, values);

         values = new ContentValues();
        // values.put("id_producto", producto.getId_producto());
        values.put("nombre_producto", "Rosca Herbárea");
        values.put("urlImagen", R.drawable.rosca);
        values.put("precio", 95);
      //  db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTO, null, values);

        values = new ContentValues();
        // values.put("id_producto", producto.getId_producto());
        values.put("nombre_producto", "Sushi Extremo");
        values.put("urlImagen", R.drawable.sushi);
        values.put("precio", 125);
      //  db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTO, null, values);

        values = new ContentValues();
        values.put("nombre_producto", "Bascula");
        values.put("urlImagen", R.drawable.bascula3);
        values.put("precio", 250);
        //  db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTO, null, values);

        values = new ContentValues();
        // values.put("id_producto", producto.getId_producto());
        values.put("nombre_producto", "Cadena Circonia");
        values.put("urlImagen", R.drawable.cadena2);
        values.put("precio", 350);
        //  db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTO, null, values);

        values = new ContentValues();
        // values.put("id_producto", producto.getId_producto());
        values.put("nombre_producto", "Audifonos");
        values.put("urlImagen", R.drawable.cadena1);
        values.put("precio", 300);
        //  db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTO, null, values);

        values = new ContentValues();
        // values.put("id_producto", producto.getId_producto());
        values.put("nombre_producto", "mochila");
        values.put("urlImagen", R.drawable.mochila2);
        values.put("precio", 400);
        //  db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTO, null, values);
        values = new ContentValues();
        // values.put("id_producto", producto.getId_producto());
        values.put("nombre_producto", "Smart Watch");
        values.put("urlImagen", R.drawable.smartwatch1);
        values.put("precio", 300);
        //  db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTO, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTO);
        onCreate(db);
    }

    public void registrarUser(Usuarios usuario) {
        ContentValues values = new ContentValues();
        values.put("id_usuario", usuario.getId_usuario());
        values.put("nombre", usuario.getNombre());
        values.put("telefono", usuario.getTelefono());
        values.put("direccion", usuario.getDireccion());
        values.put("user", usuario.getUser());
        values.put("password", usuario.getPassword());
        values.put("url_imagen", usuario.getUrlImagen());
        values.put("correo", usuario.getCorreo());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USUARIO, null, values);
    }

    public void agregarProducto(Producto producto) {
        ContentValues values = new ContentValues();
        values.put("id_producto", producto.getId_producto());
        values.put("nombre_producto", producto.getNombre_producto());

        values.put("urlImagen", producto.getUrlImagen());
        values.put("precio", producto.getPrecio());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTO, null, values);
    }

    public ArrayList<Producto> listProducto () {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Producto> storeVenta = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select * from Producto", null);
        if (cursor.moveToFirst()) {
            do {

                int id_producto = Integer.parseInt(cursor.getString(0));
                String nombre_producto = cursor.getString(1);
                int urlImagen = Integer.parseInt(cursor.getString(2));
                int precio = Integer.parseInt(cursor.getString(3));


                storeVenta.add(new Producto(id_producto, nombre_producto, precio, urlImagen));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeVenta;
    }


    public Producto verProducto(int id_producto) {
        SQLiteDatabase db = this.getWritableDatabase();
        Producto producto = null;
        Cursor cursor;

        cursor = db.rawQuery("select * from Producto WHERE id_producto = " + id_producto + " LIMIT 1", null);
        if (cursor.moveToFirst()) {

            producto = new Producto();
      producto.setId_producto(cursor.getInt(0));
            producto.setNombre_producto(cursor.getString(1));
            producto.setUrlImagen(Integer.parseInt(cursor.getString(2)));
            producto.setPrecio(cursor.getInt(3));

        }
        cursor.close();
        return producto;
    }


    public Usuarios validarUsuario() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
Usuarios usuario=null;
        cursor = db.rawQuery("select * from Usuario", null);
        if (cursor.moveToFirst()) {

            usuario = new Usuarios();
            usuario.setNombre(cursor.getString(1));
            usuario.setCorreo(cursor.getString(4));


        }
        cursor.close();
        return usuario;

    }

    public Usuarios verdatosUsuario() {
        SQLiteDatabase db = this.getWritableDatabase();
Usuarios usuarios = null;
Cursor cursor;

        cursor = db.rawQuery("select * from Usuario", null);
        if (cursor.moveToFirst()) {

            usuarios = new Usuarios();
            usuarios.setId_usuario(cursor.getInt(0));
            //usuarios.setId_R(cursor.getInt(1));
            usuarios.setNombre(cursor.getString(1));
            usuarios.setDireccion(cursor.getString(3));
            usuarios.setCorreo(cursor.getString(4));
            usuarios.setTelefono(cursor.getString(5));
            usuarios.setUser(cursor.getString(6));
            usuarios.setPassword(cursor.getString(7));


        }
        cursor.close();
        return usuarios;
    }
}
