package com.example.ventasapp.entidades;

public class Producto {
    private int id_producto;
    private String nombre_producto;
    private float precio;
    private int urlImagen;

    public Producto(int id_producto, String nombre_producto, float precio, int urlImagen) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.precio = precio;
        this.urlImagen = urlImagen;
    }

    public Producto() {

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
