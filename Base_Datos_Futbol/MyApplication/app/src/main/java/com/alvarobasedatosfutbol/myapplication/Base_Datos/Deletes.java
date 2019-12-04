package com.alvarobasedatosfutbol.myapplication.Base_Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Álvaro on 08/09/2017.
 */

public class Deletes {

    //Metodo para hacer un delete en la Base de Datos
    public static void delete(Context context, String sentencia_delete){

        Base_Datos base_data = new Base_Datos(context, Base_Datos.bd_name, null, Base_Datos.bd_version);
        SQLiteDatabase bd =  base_data.getWritableDatabase();
        bd.execSQL(sentencia_delete.toString());
        bd.close();

    }
    //**********************************************************************************************
    //Metodo para construir la query del Delete
    public static String constructor_delete(String tabla, int id){
        String st_delete = "DELETE FROM '" + tabla + "' WHERE id = " + id;
        return st_delete;
    }
    //**********************************************************************************************
    //Metodo para construir la query del Delete
    public static String constructor_delete_jugadores_relacionados(int id){
        String st_delete = "DELETE FROM jugadores WHERE equipo_id = " + id;
        return st_delete;
    }
    //**********************************************************************************************
    //Metodo para construir la query del Delete
    public static String constructor_delete_clasificaciones(String query, String tabla, int id){
        if(query == null){
            query = "DELETE FROM " + tabla + " WHERE id IN(" + id + ")";
        }else{
            query = query.substring(0, query.length()-1);
            query = query + ", " + id + ")";
        }
        return query;
    }
}
