package com.example.ventasapp.modelo;


import android.content.Context;

import com.example.ventasapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Comida {
    private float precio;
    private String nombre;
    private int idDrawable;

    public Comida(float precio, String nombre, int idDrawable) {
        this.precio = precio;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }
    public Comida(){

    }

    public static final List<Comida> COMIDAS_POPULARES = new ArrayList<Comida>();
   public static final List<Comida> comidaBEBIDAS = new ArrayList<>();
    public static final List<Comida> comidaPOSTRES = new ArrayList<>();
    public static final List<Comida> comidaPLATILLOS = new ArrayList<>();



    static {
        COMIDAS_POPULARES.add(new Comida(5, "Camarones Tismados", R.drawable.camarones));
        COMIDAS_POPULARES.add(new Comida(3.2f, "Rosca Herbárea", R.drawable.rosca));
        COMIDAS_POPULARES.add(new Comida(12f, "Sushi Extremo", R.drawable.sushi));
        COMIDAS_POPULARES.add(new Comida(9, "Sandwich Deli", R.drawable.sandwich));
        COMIDAS_POPULARES.add(new Comida(34f, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo));



        comidaPLATILLOS.add(new Comida(5, "Camarones Tismados", R.drawable.camarones));
        comidaPLATILLOS.add(new Comida(3.2f, "Rosca Herbárea", R.drawable.rosca));
        comidaPLATILLOS.add(new Comida(12f, "Sushi Extremo", R.drawable.sushi));
        comidaPLATILLOS.add(new Comida(9, "Sandwich Deli", R.drawable.sandwich));
        comidaPLATILLOS.add(new Comida(34f, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo));

        comidaBEBIDAS.add(new Comida(3, "Taza de Café", R.drawable.cafe));
        comidaBEBIDAS.add(new Comida(12, "Coctel Tronchatoro", R.drawable.coctel));
        comidaBEBIDAS.add(new Comida(5, "Jugo Natural", R.drawable.jugo_natural));
        comidaBEBIDAS.add(new Comida(24, "Coctel Jordano", R.drawable.coctel_jordano));
        comidaBEBIDAS.add(new Comida(30, "Botella Vino Tinto Darius", R.drawable.vino_tinto));

        comidaPOSTRES.add(new Comida(2, "Postre De Vainilla", R.drawable.postre_vainilla));
        comidaPOSTRES.add(new Comida(3, "Flan Celestial", R.drawable.flan_celestial));
        comidaPOSTRES.add(new Comida(2.5f, "Cupcake Festival", R.drawable.cupcakes_festival));
        comidaPOSTRES.add(new Comida(4, "Pastel De Fresa", R.drawable.pastel_fresa));
        comidaPOSTRES.add(new Comida(5, "Muffin Amoroso", R.drawable.muffin_amoroso));
    }


    public Comida(Context context) {

    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }
}
