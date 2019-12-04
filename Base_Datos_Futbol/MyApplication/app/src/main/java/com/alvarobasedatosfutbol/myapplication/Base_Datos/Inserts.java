package com.alvarobasedatosfutbol.myapplication.Base_Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Álvaro on 08/09/2017.
 */

public class Inserts {

    //Funcion para fabricar el Insert de Equipos, devuelve la String con toda la sentencia
    public static String constructor_sentencia_insertar_equipos(String nombre_eq, int tipo){
        String insert_eq = "INSERT INTO equipos VALUES (NULL, '"+nombre_eq+"', 0, " +tipo +")";
        return insert_eq;
    }
    //**********************************************************************************************
    //Funcion para fabricar insert de jugadores, devuelve string con la sentencia
    public static String constructor_sentencia_insertar_jugadores(String nombre_jugador, int dorsal, String posicion, int equipo_id){
        String insert_jugadores = "INSERT INTO jugadores VALUES (NULL, '" + nombre_jugador + "', " + dorsal + ", 0, 0, 0, 0, 0, 0, '" + posicion + "', " + equipo_id + ")";
        return insert_jugadores;
    }
    //**********************************************************************************************
    //Funcion para fabricar insert de clasificacion, devuelve string con la sentencia
    public static String constructor_sentencia_insertar_clasificaciones(String nombre, int tipo, String url, int equipo_id){
        String insert_clasificaciones = "INSERT INTO clasificaciones VALUES (NULL, '" + nombre + "', " + tipo + ", '" + url + "', " + equipo_id + ")";
        return insert_clasificaciones;
    }
    //**********************************************************************************************
    //Funcion para fabricar insert de clasificacion_equipos_liga principal, devuelve string con la sentencia
    public static String constructor_sentencia_insertar_clasificacion_equipos_liga_principal(String nombre, int equipo_principal_id, int clasificacion_id){
        String insert_clasificacion_equipos_liga = "INSERT INTO clasificacion_equipos_liga VALUES (NULL, '" + nombre + "', 0, 0, 0, 0, 0, 0, 0, " + equipo_principal_id + ", " + clasificacion_id + ")";
        return insert_clasificacion_equipos_liga;
    }
    //**********************************************************************************************
    //Funcion para fabricar insert de clasificacion_equipos_liga, devuelve string con la sentencia
    public static String constructor_sentencia_insertar_clasificacion_equipos_liga(String nombre, int clasificacion_id){
        String insert_clasificacion_equipos_liga = "INSERT INTO clasificacion_equipos_liga VALUES (NULL, '" + nombre + "', 0, 0, 0, 0, 0, 0, 0, NULL, " + clasificacion_id + ")";
        return insert_clasificacion_equipos_liga;
    }
    //**********************************************************************************************
    //Metodo insertar en base de datos
    public static void insert(Context context, String sentencia_insert){

        Base_Datos base_data = new Base_Datos(context, Base_Datos.bd_name, null, Base_Datos.bd_version);
        SQLiteDatabase bd =  base_data.getWritableDatabase();
        bd.execSQL(sentencia_insert.toString());
        bd.close();
    }
}
