package com.alvarobasedatosfutbol.myapplication.Consultas.Plantilla;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Selects;
import com.alvarobasedatosfutbol.myapplication.Otros.Otros;
import com.alvarobasedatosfutbol.myapplication.R;


/**
 Fragment para consultar Plantillas
 */
public class Consulta_Plantilla extends Fragment {

    private OnFragmentInteractionListener mListener;

    //Cargamos las variables y objetos que vamos a necesitar
    Spinner plantillas;
    Integer [] equipo_id;
    String [] equipo_nombre;
    ListView lista_jugadores;
    Toast error;
    int id_equipo;
    int posicion;
    int horientacion;

    public static Consulta_Plantilla newInstance(String param1, String param2) {
        Consulta_Plantilla fragment = new Consulta_Plantilla();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    //Cuando se crea el fragment ejecuta lo que tiene dentro
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Definimos un view para poder usar los objetos
        View view = inflater.inflate(R.layout.fragment_consulta_plantilla, container, false);
        //Definimos toast para mostrar mensajes
        error = Toast.makeText(getContext(),"Error al Consultar Plantilla", Toast.LENGTH_SHORT);
        String select = Selects.constructor_sentencia_select_all_equipos();
        equipo_id = Selects.select_integer(getContext(),select, 0);
        equipo_nombre = Selects.select_string(getContext(),select, 1);
        plantillas = (Spinner)view.findViewById(R.id.spinner_plantilla);
        Otros.spinner_rellenar(plantillas, equipo_nombre, this.getActivity());
        lista_jugadores = (ListView)view.findViewById(R.id.lista_jugadores);
        horientacion = this.getResources().getConfiguration().orientation;
        plantillas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                posicion = plantillas.getSelectedItemPosition();
                id_equipo = equipo_id[posicion];
                String select_jugadores = Selects.constructor_sentencia_jugadores_plantilla(id_equipo);
                Otros.listview_rellenar_plantilla(lista_jugadores,getContext(),select_jugadores, horientacion);
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
