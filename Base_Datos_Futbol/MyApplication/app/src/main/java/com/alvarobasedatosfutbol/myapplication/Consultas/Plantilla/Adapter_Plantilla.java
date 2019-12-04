package com.alvarobasedatosfutbol.myapplication.Consultas.Plantilla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Jugador;
import com.alvarobasedatosfutbol.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Álvaro on 18/06/2017.
 */
public class Adapter_Plantilla extends BaseAdapter{
    private Context context;
    private ArrayList<Clase_Jugador> items;
    private int horientacion;

    public Adapter_Plantilla(Context context, ArrayList<Clase_Jugador> items, int horientacion) {
        this.context = context;
        this.items = items;
        this.horientacion = horientacion;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.fragment_consulta_plantilla_list_view, parent, false);
        }
        // Set data into the view.
        TextView list_dorsal = (TextView) rowView.findViewById(R.id.list_dorsal);
        TextView list_nombre = (TextView) rowView.findViewById(R.id.list_nombre);
        TextView list_partidos = (TextView) rowView.findViewById(R.id.list_partidos);
        TextView list_goles = (TextView) rowView.findViewById(R.id.list_goles);
        TextView list_asistencias = (TextView) rowView.findViewById(R.id.list_asistencias);
        TextView list_amarillas = (TextView) rowView.findViewById(R.id.list_amarillas);
        TextView list_rojas = (TextView) rowView.findViewById(R.id.list_rojas);

        Clase_Jugador item = this.items.get(position);
        list_dorsal.setText(Integer.toString(item.getDorsal()));
        list_nombre.setText(item.getNombre());
        if (item.getEquipo_tipo()==1){
            list_partidos.setText(Integer.toString(item.getPartidos())+"("+ Integer.toString(item.getPartidos_suplente()) +")");
        }else {
            list_partidos.setText(Integer.toString(item.getPartidos()));
        }
        list_goles.setText(Integer.toString(item.getGoles()));
        if (horientacion == 2) {
            list_asistencias.setText("0");
            list_amarillas.setText(Integer.toString(item.getAmarillas()));
            list_rojas.setText(Integer.toString(item.getRojas()));
        }
        return rowView;
    }
}