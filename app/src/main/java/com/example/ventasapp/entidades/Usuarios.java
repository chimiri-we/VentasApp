package com.example.ventasapp.entidades;

public class Usuarios {
    private int id_usuario;
    private String nombre;
    private String correo;
    private String direccion;
    private String telefono;
    private String urlImagen;
    private String user;
    private String password;
    private String colonia;
    private String calle;

    public Usuarios(int id_usuario, String nombre, String correo, String direccion, String telefono, String urlImagen, String user, String password, String colonia, String calle) {

        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.urlImagen = urlImagen;
        this.user = user;
        this.password = password;
        this.colonia = colonia;
        this.calle = calle;
    }

    public Usuarios() {

    }

    public Usuarios(String usuarioNombre, String usuarioEmail, String usuarioUser, String imagen) {
        this.nombre = usuarioNombre;
        this.correo = usuarioEmail;
        this.user = usuarioUser;
        this.urlImagen = imagen;
    }

    public Usuarios(int id_usuario, String contra) {
        this.id_usuario = id_usuario;

        this.password = contra;
    }

    public Usuarios(int id_usuario, String nombre, String telefono, String correo, String user) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.correo = correo;

        this.telefono = telefono;

        this.user = user;

    }

    public Usuarios(int id_usuario, String ciudad, String newcalle, String newcolonia) {
this.id_usuario = id_usuario;
    this.direccion = ciudad;
    this.colonia = newcolonia;
    this.calle = newcalle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
