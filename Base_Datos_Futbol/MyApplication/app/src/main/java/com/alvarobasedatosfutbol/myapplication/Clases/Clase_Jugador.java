package com.alvarobasedatosfutbol.myapplication.Clases;

/**
 * Created by Álvaro on 18/06/2017.
 */

public class Clase_Jugador {

    private int id;
    private String nombre;
    private int partidos;
    private int goles;
    private int dorsal;
    private int amarillas;
    private int rojas;
    private int asistencias;
    private String posicion;
    private int partidos_suplente;
    private int id_equipo;
    private String nombre_equipo;
    private int equipo_tipo;

    public Clase_Jugador(int dorsal, String nombre, int goles, int partidos) {
        super();
        this.dorsal = dorsal;
        this.nombre = nombre;
        this.goles = goles;
        this.partidos = partidos;
    }

    public Clase_Jugador(int id, String nombre, int partidos, int goles, int dorsal, int amarillas, int rojas, String posicion, int partidos_suplente, int id_equipo, String nombre_equipo, int asistencias) {
        this.id = id;
        this.nombre = nombre;
        this.partidos = partidos;
        this.goles = goles;
        this.dorsal = dorsal;
        this.amarillas = amarillas;
        this.rojas = rojas;
        this.posicion = posicion;
        this.partidos_suplente = partidos_suplente;
        this.id_equipo = id_equipo;
        this.nombre_equipo = nombre_equipo;
        this.asistencias = asistencias;

    }

    public Clase_Jugador(){
        super();

    }

    public int getId() {
        return id;
    }

    public int getAmarillas() {
        return amarillas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmarillas(int amarillas) {
        this.amarillas = amarillas;
    }

    public void setRojas(int rojas) {
        this.rojas = rojas;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public void setPartidos_suplente(int partidos_suplente) {
        this.partidos_suplente = partidos_suplente;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public void setNombre_equipo(String nombre_equipo) {
        this.nombre_equipo = nombre_equipo;
    }

    public int getRojas() {
        return rojas;
    }

    public String getPosicion() {
        return posicion;
    }

    public int getPartidos_suplente() {
        return partidos_suplente;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public String getNombre_equipo() {
        return nombre_equipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public int getPartidos() {
        return partidos;
    }

    public void setPartidos(int partidos) {
        this.partidos = partidos;
    }

    public int getDorsal() {

        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }
    public int getEquipo_tipo() {
        return equipo_tipo;
    }

    public void setEquipo_tipo(int equipo_tipo) {
        this.equipo_tipo = equipo_tipo;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {this.asistencias = asistencias;}
    @Override
    public String toString() {
        return nombre;
    }
}

