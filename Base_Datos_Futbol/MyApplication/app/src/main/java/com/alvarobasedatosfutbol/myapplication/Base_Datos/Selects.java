package com.alvarobasedatosfutbol.myapplication.Base_Datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Spinner;

import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Clasificacion;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Equipo;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Jugador;

import java.util.ArrayList;

/**
 * Created by Álvaro on 03/10/2016.
 */
public class Selects {

    static int posicion;
    static Spinner eleccion;


    /***************************
     *********SELECTS***********
     ***************************/

    //Funcion para fabricar la select de equipos
    public static String constructor_sentencia_select_all_equipos(){
        String select_eq = "SELECT id, nombre_eq, temp_completa, tipo FROM equipos order by id";
        return select_eq;
    }
    //**********************************************************************************************
    //Funcion para fabricar la select de equipos con la temporada abierta
    public static String constructor_sentencia_select_equipos_temp_abierta(){
        String select_eq = "SELECT id, nombre_eq, temp_completa, tipo FROM equipos WHERE temp_completa = 0 order by id";
        return select_eq;
    }
    //**********************************************************************************************
    //Funcion para fabricar la select de equipos que tienen clasificaciones
    public static String constructor_sentencia_select_equipos_con_clasificaciones(){
        String select_eq = "SELECT DISTINCT equipos.id, equipos.nombre_eq, equipos.temp_completa, equipos.tipo FROM equipos LEFT JOIN clasificaciones ON clasificaciones.equipo_id = equipos.id WHERE clasificaciones.id IS NOT NULL ORDER BY equipos.id";
        return select_eq;
    }
    //**********************************************************************************************
    //Funcion para fabricar la select de jugadores
    public static String constructor_sentencia_jugadores_delete(){
        String select_ju = "SELECT jugadores.id, jugadores.nombre_ju, jugadores.dorsal_ju, jugadores.equipo_id, nombre_eq FROM jugadores Left join equipos on equipos.id = jugadores.equipo_id order by jugadores.equipo_id, jugadores.dorsal_ju";
        return select_ju;
    }
    //**********************************************************************************************
    //Funcion para fabricar la select de jugadores para la vista plantilla
    public static String constructor_sentencia_jugadores_plantilla(int id_equipo){
        String select_ju = "SELECT jugadores.id, jugadores.nombre_ju, jugadores.dorsal_ju, jugadores.partidos_ju, jugadores.goles_ju, jugadores.amarillas_ju, jugadores.rojas_ju, jugadores.asistencias_ju, jugadores.posicion_ju, jugadores.equipo_id, nombre_eq, jugadores.partidos_suplente_ju, equipos.tipo FROM jugadores Left join equipos on equipos.id = jugadores.equipo_id WHERE jugadores.equipo_id = "+id_equipo+" ORDER BY jugadores.dorsal_ju";
        return select_ju;
    }
    //**********************************************************************************************
    //Funcion para fabricar la select de clasificaciones
    public static String constructor_sentencia_select_clasificaciones(int equipo_id){
        String select_cl = "SELECT id, nombre, tipo, url, equipo_id FROM clasificaciones WHERE equipo_id = " + equipo_id + " ORDER BY id";
        return select_cl;
    }
    //**********************************************************************************************
    //Funcion para fabricar la select de clasificaciones
    public static String constructor_sentencia_select_clasificaciones_delete(int equipo_id){
        String select_cl = "SELECT id, nombre, equipo_id FROM clasificaciones WHERE equipo_id = " + equipo_id + " ORDER BY id";
        return select_cl;
    }
    //**********************************************************************************************
    //Funcion devuelve un array string con la columna de la select que le hemos pasado por parametro
    public static String[] select_string(Context context, String sentencia_select, int numero_columna) {
        String [] lista = {};
        Base_Datos base_data = new Base_Datos(context, Base_Datos.bd_name, null, Base_Datos.bd_version);
        SQLiteDatabase bd = base_data.getReadableDatabase();
        Cursor seleccion = null;
        try {
            seleccion = bd.rawQuery(sentencia_select, null);
            lista = Selects.cursor_string(seleccion, numero_columna);
        } catch (Exception e) {
        }
        bd.close();
        return lista;
    }
    //**********************************************************************************************
    //Funcion devuelve un array integer con la columna de la select que le hemos pasado por parametro
    public static Integer[] select_integer(Context context, String sentencia_select, int numero_columna) {
        Integer [] lista = {};
        Base_Datos base_data = new Base_Datos(context, Base_Datos.bd_name, null, Base_Datos.bd_version);
        SQLiteDatabase bd = base_data.getReadableDatabase();
        Cursor seleccion = null;
        try {
            seleccion = bd.rawQuery(sentencia_select, null);
            lista = Selects.cursor_integer(seleccion, numero_columna);
        } catch (Exception e) {
            System.out.print(e);
        }
        bd.close();
        return lista;
    }
    //**********************************************************************************************
    //Funcion devuelve un array string con la columna de la select que le hemos pasado por parametro
    public static String[] select_string_combinacion(Context context, String sentencia_select, int numero_columna1, int numero_columna2) {
        String [] lista = {};
        Base_Datos base_data = new Base_Datos(context, Base_Datos.bd_name, null, Base_Datos.bd_version);
        SQLiteDatabase bd = base_data.getReadableDatabase();
        Cursor seleccion = null;
        try {
            seleccion = bd.rawQuery(sentencia_select, null);
            lista = Selects.cursor_string_combinacion(seleccion, numero_columna1, numero_columna2);
        } catch (Exception e) {
        }
        bd.close();
        return lista;
    }
    //**********************************************************************************************
    //Funcion devuelve un array de objeto de la clase jugador con la columna de la select que le hemos pasado por parametro
    public static ArrayList<Clase_Jugador> select_clase_jugador(Context context, String sentencia_select) {
        ArrayList<Clase_Jugador> lista = new ArrayList();
        Base_Datos base_data = new Base_Datos(context, Base_Datos.bd_name, null, Base_Datos.bd_version);
        SQLiteDatabase bd = base_data.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = bd.rawQuery(sentencia_select, null);
            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    Clase_Jugador objeto = new Clase_Jugador();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        String nombre_columna = cursor.getColumnName(i);
                        if (nombre_columna.equals("id")){
                            objeto.setId(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("nombre_ju")){
                            objeto.setNombre(cursor.getString(i));
                        }
                        if (nombre_columna.equals("dorsal_ju")){
                            objeto.setDorsal(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("partidos_ju")){
                            objeto.setPartidos(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("goles_ju")){
                            objeto.setGoles(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("amarillas_ju")){
                            objeto.setAmarillas(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("rojas_ju")){
                            objeto.setRojas(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("posicion_ju")){
                            objeto.setPosicion(cursor.getString(i));
                        }
                        if (nombre_columna.equals("equipo_id")){
                            objeto.setId_equipo(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("nombre_eq")){
                            objeto.setNombre_equipo(cursor.getString(i));
                        }
                        if (nombre_columna.equals("partidos_suplente_ju")){
                            objeto.setPartidos_suplente(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("tipo")){
                            objeto.setEquipo_tipo(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("asistencias_ju")){
                            objeto.setAsistencias(cursor.getInt(i));
                        }
                    }
                    lista.add(objeto);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        bd.close();
        return lista;
    }

    //**********************************************************************************************
    //Funcion devuelve un array de objeto de la clase jugador con la columna de la select que le hemos pasado por parametro
    public static ArrayList<Clase_Equipo> select_clase_equipo(Context context, String sentencia_select) {
        ArrayList<Clase_Equipo> lista = new ArrayList();
        Base_Datos base_data = new Base_Datos(context, Base_Datos.bd_name, null, Base_Datos.bd_version);
        SQLiteDatabase bd = base_data.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = bd.rawQuery(sentencia_select, null);
            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    Clase_Equipo objeto = new Clase_Equipo();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        String nombre_columna = cursor.getColumnName(i);
                        if (nombre_columna.equals("id")){
                            objeto.setId(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("nombre_eq")){
                            objeto.setNombre(cursor.getString(i));
                        }
                        if (nombre_columna.equals("temp_completa")){
                            objeto.setTemp_comp(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("tipo")){
                            objeto.setTipo(cursor.getInt(i));
                        }
                    }
                    lista.add(objeto);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        bd.close();
        return lista;
    }
    //**********************************************************************************************
    //Funcion devuelve un array de objeto de la clase clasificacion con la columna de la select que le hemos pasado por parametro
    public static ArrayList<Clase_Clasificacion> select_clase_clasificacion(Context context, String sentencia_select) {
        ArrayList<Clase_Clasificacion> lista = new ArrayList();
        Base_Datos base_data = new Base_Datos(context, Base_Datos.bd_name, null, Base_Datos.bd_version);
        SQLiteDatabase bd = base_data.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = bd.rawQuery(sentencia_select, null);
            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    Clase_Clasificacion objeto = new Clase_Clasificacion();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        String nombre_columna = cursor.getColumnName(i);
                        if (nombre_columna.equals("id")){
                            objeto.setId(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("nombre")){
                            objeto.setNombre(cursor.getString(i));
                        }
                        if (nombre_columna.equals("tipo")){
                            objeto.setTipo(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("url")){
                            objeto.setUrl(cursor.getString(i));
                        }
                        if (nombre_columna.equals("equipo_id")){
                            objeto.setEquipo_id(cursor.getInt(i));
                        }
                    }
                    lista.add(objeto);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        bd.close();
        return lista;
    }
    //**********************************************************************************************
    //Funcion devuelve un array de objeto de la clase clasificacion para borrar con la columna de la select que le hemos pasado por parametro
    public static ArrayList<Clase_Clasificacion> select_clase_clasificacion_delete_mark(Context context, String sentencia_select) {
        ArrayList<Clase_Clasificacion> lista = new ArrayList();
        Base_Datos base_data = new Base_Datos(context, Base_Datos.bd_name, null, Base_Datos.bd_version);
        SQLiteDatabase bd = base_data.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = bd.rawQuery(sentencia_select, null);
            //Nos aseguramos de que existe al menos un registro
            if (cursor.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    Clase_Clasificacion objeto = new Clase_Clasificacion();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        String nombre_columna = cursor.getColumnName(i);
                        if (nombre_columna.equals("id")){
                            objeto.setId(cursor.getInt(i));
                        }
                        if (nombre_columna.equals("nombre")){
                            objeto.setNombre(cursor.getString(i));
                        }
                        if (nombre_columna.equals("equipo_id")){
                            objeto.setEquipo_id(cursor.getInt(i));
                        }
                    }
                    objeto.setSelected(false);
                    lista.add(objeto);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        bd.close();
        return lista;
    }

    /***************************
     *********CURSORES**********
     ***************************/

    //Funcion para convertir un cursor en un String array
    public static String [] cursor_string(Cursor cursor, int numero_columna){
        String [] lista = {};
        ArrayList<String> array_list = new ArrayList<>();
        //Nos aseguramos de que existe al menos un registro
        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String nombre = cursor.getString(numero_columna);
                    array_list.add(nombre);
                } while (cursor.moveToNext());
            }
        }
        lista = array_list.toArray(new String[array_list.size()]);
        return lista;
    }
    //**********************************************************************************************
    //Funcion para convertir un cursor en un Integer array
    public static Integer [] cursor_integer(Cursor cursor, int numero_columna){
        Integer [] lista = {};
        ArrayList<Integer> array_list = new ArrayList<>();
        //Nos aseguramos de que existe al menos un registro
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                int id= cursor.getInt(numero_columna);
                array_list.add(id);
            } while(cursor.moveToNext());
        }
        lista = array_list.toArray(new Integer[array_list.size()]);
        return lista;
    }
    //**********************************************************************************************
    //Funcion para convertir un cursor en un String array pero combinando campos de string
    public static String [] cursor_string_combinacion(Cursor cursor, int numero_columna1, int numero_columna2){
        String [] lista = {};
        ArrayList<String> array_list = new ArrayList<>();
        //Nos aseguramos de que existe al menos un registro
        if (cursor != null ) {
            if (cursor.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String nombre1 = cursor.getString(numero_columna1);
                    String nombre2 = cursor.getString(numero_columna2);
                    String nombre = nombre1 + " - " + nombre2;
                    array_list.add(nombre);
                } while (cursor.moveToNext());
            }
        }
        lista = array_list.toArray(new String[array_list.size()]);
        return lista;
    }
    //**********************************************************************************************
    //Funcion para obtener el ultimo id de una tabla
    public static int select_last_id(Context context, String tabla){
        int id = 1;
        Base_Datos base_data = new Base_Datos(context, Base_Datos.bd_name, null, Base_Datos.bd_version);
        SQLiteDatabase bd = base_data.getReadableDatabase();
        try {
            Cursor cursor = bd.rawQuery("SELECT * FROM " + tabla + "", null);
            if(cursor.moveToLast()){
                id = cursor.getInt(0);
            }
        }catch (Exception e){}

        return id;
    }
}
