package com.alvarobasedatosfutbol.myapplication.Alta.Equipo;

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
import com.alvarobasedatosfutbol.myapplication.Otros.Otros;
import com.alvarobasedatosfutbol.myapplication.R;

import java.util.ArrayList;


/**
        Fragment para dar de Alta equipos
        */
public class Modificar_Equipo extends Fragment {

    private OnFragmentInteractionListener mListener;

    //Cargamos las variables y objetos que vamos a necesitar
    Button modificar;
    EditText nombre_eq;
    int tipo = 0;
    Toast exito;
    Toast error;
    Toast nothing_change;
    RadioGroup r_group;
    String nombre_act;
    int tipo_act;
    Spinner equipos;
    ArrayList<Clase_Equipo> array_equipos = new ArrayList();
    int id_eq;
    int sp_pos;

    public static Modificar_Equipo newInstance(String param1, String param2) {
        Modificar_Equipo fragment = new Modificar_Equipo();
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
        View view = inflater.inflate(R.layout.fragment_alta_equipo_modificar, container, false);

        //Definimos dos toast para mostrar mensajes
        exito = Toast.makeText(getContext(),"Modificado en Base de Datos", Toast.LENGTH_SHORT);
        error = Toast.makeText(getContext(),"Error al Modificar", Toast.LENGTH_SHORT);
        nothing_change = Toast.makeText(getContext(),"Los datos son los mismos", Toast.LENGTH_SHORT);
        //Definimos el campo de edicion de texto para operar con el
        nombre_eq = (EditText)view.findViewById(R.id.nombre_eq);
        //Definimos el boton para poder operar con el
        modificar = (Button)view.findViewById(R.id.modificar_eq);
        r_group = (RadioGroup)view.findViewById(R.id.radio_group_tipo);
        r_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = r_group.findViewById(checkedId);
                int index = r_group.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        tipo = 0;
                        break;
                    case 1:
                        tipo = 1;
                        break;
                }
            }
        });

        String select_equipos = Selects.constructor_sentencia_select_all_equipos();
        array_equipos = Selects.select_clase_equipo(getContext(),select_equipos);
        equipos = (Spinner) view.findViewById(R.id.spinner_seleccion_equipo);
        Otros.spinner_rellenar_equipos(equipos, array_equipos, this.getActivity());
        equipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_pos = position;
                setEq_Values(sp_pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Funcion del boton ONCLICK que hace que ocurra algo al pulsar
        modificar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                try {
                    if (!nombre_act.equals(nombre_eq.getText().toString()) || tipo_act != tipo){
                        id_eq = array_equipos.get(sp_pos).getId();
                        String update_eq = Updates.update_equipos(id_eq, nombre_eq.getText().toString(),tipo);
                        Updates.update(getContext(),update_eq);
                        refresh_Spinner(equipos);
                        setEq_Values(sp_pos);
                        exito.show();
                    }else  nothing_change.show();
                }catch (Exception e){
                    error.show();
                }
            }
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            refresh_Spinner(equipos);
        }else{

        }
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

    private void refresh_Spinner(Spinner spinner){
        String select_equipos = Selects.constructor_sentencia_select_all_equipos();
        array_equipos = Selects.select_clase_equipo(getContext(),select_equipos);
        Otros.spinner_rellenar_equipos(spinner, array_equipos, this.getActivity());
        spinner.setSelection(sp_pos);
    }
    private void setEq_Values(int position){
        nombre_eq.setText(array_equipos.get(position).getNombre());
        View radioButton = r_group.getChildAt(array_equipos.get(position).getTipo());
        r_group.check(radioButton.getId());
        nombre_act = array_equipos.get(position).getNombre();
        tipo_act = array_equipos.get(position).getTipo();
    }
}
