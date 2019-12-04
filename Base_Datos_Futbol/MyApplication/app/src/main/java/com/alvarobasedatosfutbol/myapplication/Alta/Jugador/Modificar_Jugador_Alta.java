package com.alvarobasedatosfutbol.myapplication.Alta.Jugador;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Selects;
import com.alvarobasedatosfutbol.myapplication.Base_Datos.Updates;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Equipo;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Jugador;
import com.alvarobasedatosfutbol.myapplication.Otros.Otros;
import com.alvarobasedatosfutbol.myapplication.R;

import java.util.ArrayList;

public class Modificar_Jugador_Alta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    Button modificar;
    EditText nombre_jugador;
    String nombre_jugador_act;
    Toast exito;
    Toast error;
    Toast nothing_change;
    String posicion_jugador;
    String posicion_jugador_act;
    EditText dorsal;
    int dorsal_act;
    private Spinner equipos;
    private Spinner jugadores;
    ArrayList<Clase_Equipo> array_equipos = new ArrayList();
    ArrayList<Clase_Jugador> array_jugadores = new ArrayList();
    RadioGroup r_group;
    int sp_pos_eq;
    int sp_pos_ju;
    int id_eq;
    int id_ju;


    private OnFragmentInteractionListener mListener;

    public Modificar_Jugador_Alta() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Modificar_Jugador_Alta newInstance(String param1, String param2) {
        Modificar_Jugador_Alta fragment = new Modificar_Jugador_Alta();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alta_jugador_modificar, container, false);

        //Definimos dos toast para mostrar mensajes
        exito = Toast.makeText(getContext(),"Modificado en Base de Datos", Toast.LENGTH_SHORT);
        error = Toast.makeText(getContext(),"Error al Modificar", Toast.LENGTH_SHORT);
        nothing_change = Toast.makeText(getContext(),"Los datos son los mismos", Toast.LENGTH_SHORT);
        //Definimos el campo de edicion de texto para operar con el
        dorsal = (EditText)view.findViewById(R.id.dorsal);
        nombre_jugador = (EditText)view.findViewById(R.id.nombre_jugador);
        modificar = (Button)view.findViewById(R.id.modificar_jugador);
        r_group = (RadioGroup)view.findViewById(R.id.radio_group_posicion);
        r_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = r_group.findViewById(checkedId);
                int index = r_group.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        posicion_jugador = "Jugador";
                        break;
                    case 1:
                        posicion_jugador = "Portero";
                        break;
                }

            }
        });

        String select_equipos = Selects.constructor_sentencia_select_all_equipos();
        array_equipos = Selects.select_clase_equipo(getContext(),select_equipos);
        equipos = (Spinner) view.findViewById(R.id.spinner_seleccion_equipo);
        jugadores = (Spinner) view.findViewById(R.id.spinner_seleccion_jugador);
        Otros.spinner_rellenar_equipos(equipos, array_equipos, this.getActivity());
        equipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_pos_eq = position;
                setSpinner_Jugadores(array_equipos.get(sp_pos_eq).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        jugadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_pos_ju = position;
                setJu_Values(sp_pos_ju);
                id_ju = array_jugadores.get(sp_pos_ju).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Funcion del boton ONCLICK que hace que ocurra algo al pulsar
        modificar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                try {
                    if (!nombre_jugador_act.equals(nombre_jugador.getText().toString()) || dorsal_act != Integer.parseInt(dorsal.getText().toString()) || !posicion_jugador_act.equals(posicion_jugador)){
                        id_eq = array_equipos.get(sp_pos_eq).getId();
                        id_ju = array_jugadores.get(sp_pos_ju).getId();
                        String update_ju = Updates.update_jugadores(id_ju, nombre_jugador.getText().toString(),posicion_jugador, Integer.parseInt(dorsal.getText().toString()));
                        Updates.update(getContext(),update_ju);
                        refresh_Spinner(jugadores);
                        setJu_Values(sp_pos_ju);
                        exito.show();
                    }else  nothing_change.show();
                }catch (Exception e){
                    error.show();
                }
            }
        });

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            refresh_Spinner(jugadores);
        }else{

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setSpinner_Jugadores(int equipo_id){
        String select = Selects.constructor_sentencia_jugadores_plantilla(equipo_id);
        array_jugadores = Selects.select_clase_jugador(getContext(),select);
        Otros.spinner_rellenar_jugadores(jugadores, array_jugadores, this.getActivity());
    }

    private void refresh_Spinner(Spinner spinner){
        String select = Selects.constructor_sentencia_jugadores_plantilla(array_equipos.get(sp_pos_eq).getId());
        array_jugadores = Selects.select_clase_jugador(getContext(),select);
        Otros.spinner_rellenar_jugadores(jugadores, array_jugadores, this.getActivity());
        int pos_spinner = 0;
        for(int i = 0; i< array_jugadores.size(); i++){
            if (array_jugadores.get(i).getId()==id_ju){
                pos_spinner = i;
            }
        }
        spinner.setSelection(pos_spinner);
    }
    private void setJu_Values(int position){
        nombre_jugador.setText(array_jugadores.get(position).getNombre());
        posicion_jugador = array_jugadores.get(position).getPosicion();
        View radioButton;
        if(posicion_jugador.equals("Jugador")){
            radioButton = r_group.getChildAt(0);
        }else{
            radioButton = r_group.getChildAt(1);
        }
        r_group.check(radioButton.getId());
        dorsal.setText(Integer.toString(array_jugadores.get(position).getDorsal()));
        nombre_jugador_act = array_jugadores.get(position).getNombre();
        dorsal_act = array_jugadores.get(position).getDorsal();
        posicion_jugador_act = array_jugadores.get(position).getPosicion();
    }
}
