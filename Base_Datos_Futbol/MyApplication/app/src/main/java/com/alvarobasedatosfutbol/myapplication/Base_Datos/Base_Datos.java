package com.alvarobasedatosfutbol.myapplication.Base_Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Álvaro on 03/10/2016.
 */
public class Base_Datos extends SQLiteOpenHelper {

    public static int bd_version = 8;//Version base de datos
    public static final String bd_name = "Estadisticas_Futbol.db";//Nombre Base de Datos
    //Creacion de sentencias para crear Tablas
    String tabla_equipo="CREATE TABLE equipos (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre_eq TEXT, temp_completa INTEGER, tipo INTEGER)";
    String tabla_jugador="CREATE TABLE jugadores (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre_ju TEXT, dorsal_ju INTEGER, partidos_ju INTEGER, partidos_suplente_ju INTEGER, goles_ju INTEGER, amarillas_ju INTEGER, rojas_ju INTEGER, asistencias_ju INTEGER, posicion_ju TEXT, equipo_id INTEGER, CONSTRAINT equipo_id_fk FOREIGN KEY(equipo_id) REFERENCES equipos(id) ON UPDATE CASCADE ON DELETE CASCADE)";
    String tabla_clasificaciones="CREATE TABLE clasificaciones (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre TEXT, tipo INTEGER, url TEXT, equipo_id INTEGER, CONSTRAINT equipo_id_fk FOREIGN KEY(equipo_id) REFERENCES equipos(id) ON UPDATE CASCADE ON DELETE CASCADE)";
    String tabla_clasificacion_equipos_liga="CREATE TABLE clasificacion_equipos_liga (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre TEXT, jugados INTEGER, ganados INTEGER, empatados INTEGER, perdidos INTEGER, goles_favor INTEGER, goles_contra INTEGER, puntos INTEGER, equipo_principal_id INTEGER, clasificacion_id INTEGER, CONSTRAINT equipo_principal_id_fk FOREIGN KEY(equipo_principal_id) REFERENCES equipos(id) ON UPDATE CASCADE ON DELETE CASCADE, CONSTRAINT clasificacion_id_fk FOREIGN KEY(clasificacion_id) REFERENCES clasificaciones(id) ON UPDATE CASCADE ON DELETE CASCADE)";
    //Constructor de la Base de Datos
    public Base_Datos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //Creacion de las bases de datos en una bd nueva
        db.execSQL(tabla_equipo);
        db.execSQL("INSERT INTO equipos VALUES " +
                "(null, 'Estoril 2 08-09', 0, 0), " +
                "(NULL, 'Castilla Antusana C 09-10', 0, 1)");
        try {
            db.execSQL(tabla_jugador);
            db.execSQL("INSERT INTO jugadores VALUES " +
                    "(NULL, 'Alvaro', 6, 0, 0, 0, 0, 0, 0, 'Jugador', 1), " +
                    "(NULL, 'Aitor', 2, 0, 0, 0, 0, 0, 0, 'Jugador', 1), " +
                    "(NULL, 'Pepe', 7, 0, 0, 0, 0, 0, 0, 'Jugador', 1), " +
                    "(NULL, 'Cristiano', 5, 0, 0, 0, 0, 0, 0, 'Jugador', 1), " +
                    "(NULL, 'Adrian', 3, 0, 0, 0, 0, 0, 0, 'Portero', 1), " +
                    "(NULL, 'Coutinho', 10, 0, 0, 0, 0, 0, 0, 'Jugador', 1), " +
                    "(NULL, 'Alvaro', 22, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Juan', 5, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Damian', 88, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Marron', 44, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Milner', 66, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Roberto Firmino', 87, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Yo', 1, 0, 0, 0, 0, 0, 0, 'Portero', 2), " +
                    "(NULL, 'El', 22, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Yoel Robles', 5, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Serj Tajan', 88, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Geralt de Rivia', 44, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Rey Foltest', 66, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Jordan Henderson', 66, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Mohamed Salah', 87, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'No se', 1, 0, 0, 0, 0, 0, 0, 'Portero', 2), " +
                    "(NULL, 'Muerte', 87, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Pichichi', 1, 0, 0, 0, 0, 0, 0, 'Jugador', 2), " +
                    "(NULL, 'Miguel Taranilla', 11, 0, 0, 0, 0, 0, 0, 'Jugador', 1)");
            db.execSQL(tabla_clasificaciones);
            db.execSQL(tabla_clasificacion_equipos_liga);
        }catch (Exception e){
            Log.e("",e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se ejecuta cuando se actualiza la base de datos
        try {
            db.execSQL(tabla_clasificaciones);
            db.execSQL(tabla_clasificacion_equipos_liga);
            String tabla = "CREATE TABLE jugadores2 (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre_ju TEXT, dorsal_ju INTEGER, partidos_ju INTEGER, partidos_suplente_ju INTEGER, goles_ju INTEGER, amarillas_ju INTEGER, rojas_ju INTEGER, asistencias_ju INTEGER, posicion_ju TEXT, equipo_id INTEGER, CONSTRAINT equipo_id_fk FOREIGN KEY(equipo_id) REFERENCES equipos(id) ON UPDATE CASCADE ON DELETE CASCADE)";
            db.execSQL(tabla);
            db.execSQL("INSERT INTO jugadores2 (id, nombre_ju, dorsal_ju, partidos_ju, partidos_suplente_ju, goles_ju, amarillas_ju, rojas_ju, asistencias_ju, posicion_ju, equipo_id) SELECT id, nombre_ju, dorsal_ju, partidos_ju, partidos_suplente_ju, goles_ju, amarillas_ju, rojas_ju, asistencias_ju, posicion_ju, equipo_id FROM jugadores;");
            db.execSQL("DROP TABLE jugadores;");
            db.execSQL("ALTER TABLE jugadores2 RENAME TO jugadores;");
        }catch (Exception e){}
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        String foreign_keys_on = "PRAGMA foreign_keys = ON";
        db.execSQL(foreign_keys_on);
    }
}
