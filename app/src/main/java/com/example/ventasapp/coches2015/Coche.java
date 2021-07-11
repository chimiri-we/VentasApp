package com.example.ventasapp.coches2015;

import com.example.ventasapp.R;

/**
 * Clase que representa la existencia de un Coche
 */
public class Coche {
    private String nombre;
    private int idDrawable;

    public Coche(String nombre, int idDrawable) {
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public int getId() {
        return nombre.hashCode();
    }

    public static Coche[] ITEMS = {
            new Coche("Jaguar F-Type 2015", R.drawable.bascula1),
            new Coche("Mercedes AMG-GT", R.drawable.bascula4),
            new Coche("Mazda MX-5", R.drawable.cadena1),
            new Coche("Porsche 911 GTS", R.drawable.cadena4),
            new Coche("BMW Serie 6", R.drawable.bascula2),
            new Coche("Ford Mondeo", R.drawable.cadena5),
            new Coche("Volvo V60 Cross Country", R.drawable.cupcakes_festival),
            new Coche("Jaguar XE", R.drawable.cadena6),
            new Coche("VW Golf R Variant", R.drawable. bascula1),
            new Coche("Seat León ST Cupra", R.drawable.cadena4),
    };

    /**
     * Obtiene item basado en su identificador
     *
     * @param id identificador
     * @return Coche
     */
    public static Coche getItem(int id) {
        for (Coche item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}
