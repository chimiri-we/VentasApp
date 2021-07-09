package com.example.ventasapp.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.ventasapp.R;
import com.example.ventasapp.entidades.DetalleVenta;
import com.example.ventasapp.entidades.Producto;
import com.example.ventasapp.entidades.Usuarios;
import com.example.ventasapp.entidades.Venta;

import java.util.ArrayList;

public class BaseDatos extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 19;
    private static final String DATABASE_NAME = "Ventas.db";

    private static final String TABLE_USUARIO = "Usuario";
    private static final String TABLE_PRODUCTO = "Producto";
    private static final String TABLE_DETALLE_VENTA = "DetalleVenta";
    private static final String TABLE_VENTA = "Venta";
    private static final String ID_USUARIO = "id_usuario";


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

        String dbVenta="create table Venta("+
                "id_venta INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "id_usuario INTEGER,"+
                "fecha TEXT,"+
                "total_venta integer,"+
                "status INTEGER,"+
                "FOREIGN KEY(id_usuario) REFERENCES Usuario(id_usuario))";

        db.execSQL(dbVenta);

        String dbDetalleVenta="create table DetalleVenta("+
                "id_detalle INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "id_venta INTEGER,"+
                "id_producto integer,"+
                "nombre_producto TEXT,"+
                "precio_producto TEXT,"+
                "cantidad integer,"+
                "total integer,"+

                "FOREIGN KEY(id_producto) REFERENCES Producto(id_producto),"+
                "FOREIGN KEY(id_venta) REFERENCES Venta(id_venta))";
        db.execSQL(dbDetalleVenta);

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETALLE_VENTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENTA);
        onCreate(db);
    }
    public void agregardetalleVenta(DetalleVenta detalleVenta) {
        ContentValues values = new ContentValues();
        values.put("nombre_producto", detalleVenta.getNombre_producto());
        values.put("precio_producto", detalleVenta.getPrecio());
        values.put("cantidad", detalleVenta.getCantidad());
        values.put("id_venta", detalleVenta.getId_venta());
        values.put("id_producto", detalleVenta.getId_producto());
        values.put("total", detalleVenta.getTotal());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_DETALLE_VENTA, null, values);
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



    public DetalleVenta consultarDetalleVenta(int id_venta) {
        SQLiteDatabase db = this.getWritableDatabase();
        DetalleVenta dtventa=null;
        Cursor cursor;

        cursor = db.rawQuery("select * from  DetalleVenta WHERE id_venta = " + id_venta + "", null);
        if (cursor.moveToFirst()) {
            dtventa = new DetalleVenta();
            dtventa.setId_detalle(cursor.getInt(0));
            dtventa.setId_producto(cursor.getInt(2));
            dtventa.setNombre_producto(cursor.getString(3));
            dtventa.setPrecio(cursor.getString(4));
            dtventa.setCantidad(cursor.getString(5));
            dtventa.setTotal(cursor.getString(6));
        }
        cursor.close();
        return dtventa;
    }

    public void generarVenta(Venta nuevaventa) {
        ContentValues values = new ContentValues();
        values.put("id_usuario", nuevaventa.getId_usuario());
        values.put("fecha", nuevaventa.getFecha());
        values.put("total_venta", nuevaventa.getTotal_venta());
        values.put("status", nuevaventa.getStatus());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VENTA, null, values);
    }

    public Venta ultimaVenta() {
        SQLiteDatabase db = this.getWritableDatabase();
        Venta venta = null;
        Cursor cursor;

        cursor = db.rawQuery("select MAX(id_venta) AS id_venta from Venta", null);
        if (cursor.moveToFirst()) {

            venta = new Venta();
            venta.setId_venta(cursor.getInt(0));
        }
        cursor.close();
        return venta;
    }

    public ArrayList<DetalleVenta> listDetalleVenta(int idventa) {

        String sql = "select * from  DetalleVenta WHERE id_venta = " + idventa + "";
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<DetalleVenta> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id_detalle = Integer.parseInt(cursor.getString(0));
                int id_venta = Integer.parseInt(cursor.getString(1));
                int id_producto = Integer.parseInt(cursor.getString(2));

                String nombre_producto = cursor.getString(3);
                String precio = cursor.getString(4);
                String cantidad = cursor.getString(5);
                String total = cursor.getString(6);

                storeContacts.add(new DetalleVenta(id_producto, id_venta, nombre_producto, precio, cantidad, total));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    public DetalleVenta sumarItems(int id_venta) {
              SQLiteDatabase db = this.getWritableDatabase();
        DetalleVenta dtVenta = null;
        Cursor cursor;
        cursor=db.rawQuery( "select SUM(total) from DetalleVenta WHERE id_venta = " + id_venta +"", null);
        if (cursor.moveToNext()) {
            dtVenta = new DetalleVenta();
            dtVenta.setTotal(String.valueOf(cursor.getInt(0)));

        }
        cursor.close();
        return  dtVenta;

    }

    public ArrayList<Venta> listaVentas() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Venta> venta = new ArrayList<>();
        Cursor cursor;
        cursor = db.rawQuery("select * from Venta", null);
        if (cursor.moveToFirst()) {
            do {
                int id_venta = Integer.parseInt(cursor.getString(0));
                int id_usuario = Integer.parseInt(cursor.getString(1));
                String fecha = cursor.getString(2);
                int total_venta = cursor.getInt(3);
                int status = Integer.parseInt(cursor.getString(4));


                venta.add(new Venta(id_venta, id_usuario, fecha, total_venta, status));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return venta;
    }

    public void guardarImagen(String path) {
        Usuarios usuario = new Usuarios();
        ContentValues values = new ContentValues();
        values.put("uri_imagen", path);
        SQLiteDatabase db = this.getWritableDatabase();
      //  db.insert(TABLE_USUARIO, null, values);
        db.update(TABLE_USUARIO, values, ID_USUARIO+ " = ?", new String[]{String.valueOf(usuario.getId_usuario())});
    }

    public void obtenerRutaImagen(Uri miPath) {
        Usuarios usuario = new Usuarios();
        ContentValues values = new ContentValues();
        values.put("uri_imagen", String.valueOf(miPath));
        SQLiteDatabase db = this.getWritableDatabase();
        //  db.insert(TABLE_USUARIO, null, values);
        db.update(TABLE_USUARIO, values, ID_USUARIO+ " = ?", new String[]{String.valueOf(usuario.getId_usuario())});
    }
    public void actualizarUsuario(Usuarios usuarios) {
        ContentValues values = new ContentValues();
        values.put("nombre", usuarios.getNombre());
        values.put("telefono", usuarios.getTelefono());
        values.put("user", usuarios.getUser());
        values.put("correo", usuarios.getCorreo());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USUARIO, values, ID_USUARIO + " = ?", new String[]{String.valueOf(usuarios.getId_usuario())});
    }

    public void actualizarContrasena(Usuarios usuarios) {
        ContentValues values = new ContentValues();

        values.put("password", usuarios.getPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USUARIO, values, ID_USUARIO + " = ?", new String[]{String.valueOf(usuarios.getId_usuario())});
    }

    public void actualizarDireccion(Usuarios usuarios) {
        ContentValues values = new ContentValues();
        values.put("direccion", usuarios.getDireccion());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USUARIO, values, ID_USUARIO + " = ?", new String[]{String.valueOf(usuarios.getId_usuario())});
    }
}
