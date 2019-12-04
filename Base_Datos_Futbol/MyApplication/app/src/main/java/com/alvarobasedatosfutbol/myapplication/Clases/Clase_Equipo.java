package com.alvarobasedatosfutbol.myapplication.Clases;

/**
 * Created by Álvaro on 08/08/2017.
 */

public class Clase_Equipo {

    private int id;
    private String nombre;
    private int temp_comp;
    private int tipo;

    public Clase_Equipo(int id, String nombre, int temp_comp, int tipo) {
        this.id = id;
        this.nombre = nombre;
        this.temp_comp = temp_comp;
        this.tipo = tipo;
    }

    public Clase_Equipo() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTemp_comp() {
        return temp_comp;
    }

    public void setTemp_comp(int temp_comp) {
        this.temp_comp = temp_comp;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
