package com.example.ventasapp.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ventasapp.entidades.DetalleVenta;
import com.example.ventasapp.entidades.Venta;

public class Consultas  extends BaseDatos {

    private static final String TABLE_DETALLE_VENTA = "DetalleVenta";
    Context context;
    BaseDatos bdLocal;

    public Consultas(Context context) {
        super(context);
        this.context = context;

    }


    public void agregardetalleVenta(DetalleVenta detalleVenta) {
        bdLocal = new BaseDatos(context.getApplicationContext());
        ContentValues values = new ContentValues();
        values.put("nombre_producto", detalleVenta.getNombre_producto());
        values.put("precio_producto", detalleVenta.getPrecio());
        values.put("cantidad", detalleVenta.getCantidad());
        values.put("id_venta", detalleVenta.getId_venta());
        values.put("id_producto", detalleVenta.getId_producto());

        values.put("total", detalleVenta.getTotal());
        SQLiteDatabase db = bdLocal.getWritableDatabase();
        db.insert(TABLE_DETALLE_VENTA, null, values);
    }
}
