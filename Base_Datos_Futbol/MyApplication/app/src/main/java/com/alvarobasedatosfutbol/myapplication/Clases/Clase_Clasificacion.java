package com.alvarobasedatosfutbol.myapplication.Clases;

/**
 * Created by Álvaro on 08/08/2017.
 */

public class Clase_Clasificacion {

    private int id;
    private String nombre;
    private String url;
    private int tipo;
    private int equipo_id;
    private boolean selected;

    public Clase_Clasificacion(int id, String nombre, int tipo, String url, int equipo_id) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.url = url;
        this.equipo_id = equipo_id;
    }
    public Clase_Clasificacion(int id, String nombre, int equipo_id, boolean selected) {
        this.id = id;
        this.nombre = nombre;
        this.equipo_id = equipo_id;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean delete_mark) {
        this.selected = delete_mark;
    }

    public Clase_Clasificacion() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getEquipo_id() {
        return equipo_id;
    }

    public void setEquipo_id(int equipo_id) {
        this.equipo_id = equipo_id;
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
