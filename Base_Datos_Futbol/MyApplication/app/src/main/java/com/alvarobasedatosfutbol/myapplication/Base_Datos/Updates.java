package com.alvarobasedatosfutbol.myapplication.Base_Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Álvaro on 09/09/2017.
 */

public class Updates {

    //**********************************************************************************************
    //Metodo Actualizar en base de datos
    public static void update(Context context, String sentencia_update){
        Base_Datos base_data = new Base_Datos(context, Base_Datos.bd_name, null, Base_Datos.bd_version);
        SQLiteDatabase bd =  base_data.getWritableDatabase();
        bd.execSQL(sentencia_update.toString());
        bd.close();
    }

    //Update Jugadores Estadisticas
    public static String update_jugadores_valores(int id, int partidos, int partidos_suplente, int goles, int asistencias, int amarillas, int rojas){
        String update_jugadores_valores = "UPDATE jugadores SET partidos_ju = partidos_ju + " + partidos + ", partidos_suplente_ju = partidos_suplente_ju + " + partidos_suplente + "," +
                " goles_ju = goles_ju + " + goles + ", asistencias_ju = asistencias_ju + " + asistencias + ", " +
                "amarillas_ju = amarillas_ju + " + amarillas + ", rojas_ju = rojas_ju + " + rojas + " WHERE id = "+id;
        return update_jugadores_valores;
    }

    //Update Equipos Modificar
    public static String update_equipos(int id, String nombre, int tipo){
        String update_equipo = "UPDATE equipos SET " +
                "nombre_eq = '" + nombre + "', tipo = " + tipo + " WHERE id = "+id;
        return update_equipo;
    }

    //Update Jugadores Modificar
    public static String update_jugadores(int id, String nombre, String posicion, int dorsal){
        String update_jugadores = "UPDATE jugadores SET " +
                "nombre_ju = '" + nombre + "', dorsal_ju = " + dorsal + ", posicion_ju = '" + posicion + "' WHERE id = "+id;
        return update_jugadores;
    }
    //Update Clasificaciones Modificar
    public static String update_clasificaciones(int id, String nombre, int tipo, String url){
        String update_clasififcaiones = "UPDATE clasificaciones SET " +
                "nombre = '" + nombre + "', tipo = " + tipo + ", url = '" + url + "' WHERE id = " + id;
        return update_clasififcaiones;
    }
}
