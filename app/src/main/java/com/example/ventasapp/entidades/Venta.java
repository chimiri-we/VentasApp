package com.example.ventasapp.entidades;

public class Venta {
    private int id_venta;
    private int id_usuario;
    private String fecha;
    private int total_venta;
    private int status;

    public Venta(int id_venta, int id_usuario, String fecha, int total_venta, int status) {
        this.id_venta = id_venta;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.total_venta = total_venta;
        this.status = status;
    }
    public Venta(){

    }

    public Venta(int id_usuario, String formattedDate, int status) {
        this.id_usuario = id_usuario;
        this.fecha = formattedDate;
        this.status = status;
    }

    public Venta(int id_usuario, String formattedDate, int totalV, int status) {
        this.id_usuario = id_usuario;
        this.fecha = formattedDate;
        this.total_venta = totalV;
        this.status = status;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(int total_venta) {
        this.total_venta = total_venta;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
