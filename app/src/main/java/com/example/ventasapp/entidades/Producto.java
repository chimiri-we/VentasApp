package com.example.ventasapp.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Producto {
    private int id_producto;
    private String nombre_producto;
    private float precio;
    private String descripcion;
    private String caracteristicas;

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    private String categoria;
    private int urlImagen;
    private int stock;
    private String dato;
    private Bitmap imagen;

    public Producto(int id_producto, String nombre_producto, float precio, int urlImagen) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.precio = precio;
        this.urlImagen = urlImagen;
    }

    public Producto() {

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;

            try {
                byte[] byteCode= Base64.decode(dato,Base64.DEFAULT);
                //this.imagen= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);

                int alto=250;//alto en pixeles
                int ancho=300;//ancho en pixeles

                Bitmap foto= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
                this.imagen=Bitmap.createScaledBitmap(foto,alto,ancho,true);


            }catch (Exception e){
                e.printStackTrace();
            }
        }



    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(int urlImagen) {
        this.urlImagen = urlImagen;
    }
}
