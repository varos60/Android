package com.alvarobasedatosfutbol.myapplication.Otros;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Selects;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Clasificacion;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Equipo;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Jugador;
import com.alvarobasedatosfutbol.myapplication.Consultas.Plantilla.Adapter_Plantilla;

import java.util.ArrayList;

/**
 * Created by Álvaro on 08/09/2017.
 */

public class Otros {

    //Metodo rellenar un Spinner
    public static void spinner_rellenar(Spinner spinner, String [] valores, Activity activity) {

        ArrayAdapter<String> adapter =new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, valores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    //**********************************************************************************************

    //Metodo rellenar un Spinner equipos
    public static void spinner_rellenar_equipos(Spinner spinner, ArrayList<Clase_Equipo> valores, Activity activity) {

        ArrayAdapter<Clase_Equipo> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, valores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    //**********************************************************************************************

    //Metodo rellenar un Spinner jugadores
    public static void spinner_rellenar_jugadores(Spinner spinner, ArrayList<Clase_Jugador> valores, Activity activity) {

        ArrayAdapter<Clase_Jugador> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, valores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    //**********************************************************************************************
    //Metodo rellenar un Spinner clasificaciones
    public static void spinner_rellenar_clasififcaciones(Spinner spinner, ArrayList<Clase_Clasificacion> valores, Activity activity) {

        ArrayAdapter<Clase_Clasificacion> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, valores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    //**********************************************************************************************

    //Metodo rellenar un listview
    public static void listview_rellenar_plantilla(ListView lista, Context contexto, String sentencia, int horientacion){

        Adapter_Plantilla adaptador = new Adapter_Plantilla(contexto, Selects.select_clase_jugador(contexto,sentencia), horientacion);
        lista.setAdapter(adaptador);

    }
    //**********************************************************************************************

}
