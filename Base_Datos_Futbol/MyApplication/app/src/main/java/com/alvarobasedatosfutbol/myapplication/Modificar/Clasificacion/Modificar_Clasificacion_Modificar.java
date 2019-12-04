package com.alvarobasedatosfutbol.myapplication.Modificar.Clasificacion;

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
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Clasificacion;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Equipo;
import com.alvarobasedatosfutbol.myapplication.Otros.Otros;
import com.alvarobasedatosfutbol.myapplication.R;

import java.util.ArrayList;


/**
        Fragment para dar de Alta equipos
        */
public class Modificar_Clasificacion_Modificar extends Fragment {

    private OnFragmentInteractionListener mListener;

    //Cargamos las variables y objetos que vamos a necesitar
    Button modificar;
    EditText nombre;
    String nombre_clasificacion_act;
    int tipo;
    int tipo_act;
    EditText url;
    String url_act;
    private Spinner equipos;
    private Spinner clasificaciones;
    ArrayList<Clase_Equipo> array_equipos = new ArrayList();
    ArrayList<Clase_Clasificacion> array_clasificaciones = new ArrayList();
    RadioGroup r_group;
    int sp_pos_eq;
    int sp_pos_clasificacion;
    int id_eq;
    int id_clasificacion;

    public static Modificar_Clasificacion_Modificar newInstance(String param1, String param2) {
        Modificar_Clasificacion_Modificar fragment = new Modificar_Clasificacion_Modificar();
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
        View view = inflater.inflate(R.layout.fragment_actualizar_clasificacion_modificar, container, false);
        url = (EditText)view.findViewById(R.id.url_clasificacion);
        nombre = (EditText)view.findViewById(R.id.nombre_clasificacion);
        modificar = (Button)view.findViewById(R.id.modificar_clasificacion);
        r_group = (RadioGroup)view.findViewById(R.id.radio_group_tipo_clasificacion);
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

        String select_equipos = Selects.constructor_sentencia_select_equipos_con_clasificaciones();
        array_equipos = Selects.select_clase_equipo(getContext(),select_equipos);
        equipos = (Spinner) view.findViewById(R.id.spinner_actualizar_clasificacion_modificar_seleccion_equipo);
        clasificaciones = (Spinner) view.findViewById(R.id.spinner_actualizar_clasificacion_sel_clas);
        Otros.spinner_rellenar_equipos(equipos, array_equipos, this.getActivity());
        equipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_pos_eq = position;
                setSpinner_Clasificacion(array_equipos.get(sp_pos_eq).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        clasificaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_pos_clasificacion = position;
                setCl_Values(sp_pos_clasificacion);
                id_clasificacion = array_clasificaciones.get(sp_pos_clasificacion).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        modificar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                try {
                    if (!nombre_clasificacion_act.equals(nombre.getText().toString()) || !url_act.equals(url.getText().toString()) || tipo_act == tipo){
                        id_eq = array_equipos.get(sp_pos_eq).getId();
                        id_clasificacion = array_clasificaciones.get(sp_pos_clasificacion).getId();
                        String update_cl = Updates.update_clasificaciones(id_clasificacion, nombre.getText().toString(), tipo, url.getText().toString());
                        Updates.update(getContext(),update_cl);
                        refresh_Spinner(clasificaciones);
                        setCl_Values(sp_pos_clasificacion);
                        Toast exito = (Toast.makeText(getContext(),"Modificado en Base de Datos",Toast.LENGTH_SHORT));
                        exito.show();
                    }else  {
                        Toast nothing_change = (Toast.makeText(getContext(),"Los datos son los mismos",Toast.LENGTH_LONG));
                        nothing_change.show();
                    }
                }catch (Exception e){
                    Toast error = (Toast.makeText(getContext(),"Error al Modificar",Toast.LENGTH_SHORT));
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            refresh_Spinner(clasificaciones);
        }else{

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
    private void setSpinner_Clasificacion(int equipo_id){
        String select = Selects.constructor_sentencia_select_clasificaciones(equipo_id);
        array_clasificaciones = Selects.select_clase_clasificacion(getContext(),select);
        Otros.spinner_rellenar_clasififcaciones(clasificaciones, array_clasificaciones, this.getActivity());
    }

    private void refresh_Spinner(Spinner spinner){
        String select = Selects.constructor_sentencia_select_clasificaciones(array_equipos.get(sp_pos_eq).getId());
        array_clasificaciones = Selects.select_clase_clasificacion(getContext(),select);
        Otros.spinner_rellenar_clasififcaciones(clasificaciones, array_clasificaciones, this.getActivity());
        int pos_spinner = 0;
        for(int i = 0; i< array_clasificaciones.size(); i++){
            if (array_clasificaciones.get(i).getId()==id_clasificacion){
                pos_spinner = i;
            }
        }
        spinner.setSelection(pos_spinner);
    }
    private void setCl_Values(int position){
        nombre.setText(array_clasificaciones.get(position).getNombre());
        tipo = array_clasificaciones.get(position).getTipo();
        View radioButton;
        if(tipo == 0){
            radioButton = r_group.getChildAt(0);
        }else{
            radioButton = r_group.getChildAt(1);
        }
        r_group.check(radioButton.getId());
        url.setText(array_clasificaciones.get(position).getUrl());
        nombre_clasificacion_act = array_clasificaciones.get(position).getNombre();
        url_act = array_clasificaciones.get(position).getUrl();
        tipo_act = array_clasificaciones.get(position).getTipo();
    }

}
