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

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Inserts;
import com.alvarobasedatosfutbol.myapplication.Base_Datos.Selects;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Equipo;
import com.alvarobasedatosfutbol.myapplication.Otros.Otros;
import com.alvarobasedatosfutbol.myapplication.R;

import java.util.ArrayList;


/**
        Fragment para dar de Alta equipos
        */
public class Modificar_Clasificacion_Registrar extends Fragment {

    private OnFragmentInteractionListener mListener;

    //Cargamos las variables y objetos que vamos a necesitar
    Button registrar;
    String nombre = "";
    String url;
    RadioGroup tipo_Radio;
    EditText nombre_EditText;
    EditText url_EditText;
    Spinner equipos;
    int tipo = 0;
    ArrayList<Clase_Equipo> array_equipos = new ArrayList();
    int id_eq;
    int array_posicion;

    public static Modificar_Clasificacion_Registrar newInstance(String param1, String param2) {
        Modificar_Clasificacion_Registrar fragment = new Modificar_Clasificacion_Registrar();
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
        View view = inflater.inflate(R.layout.fragment_actualizar_clasificacion_registrar, container, false);
        nombre_EditText = (EditText)view.findViewById(R.id.nombre_clasificacion);
        url_EditText = (EditText)view.findViewById(R.id.url_clasificacion);

        tipo_Radio = (RadioGroup)view.findViewById(R.id.radio_group_tipo_clasificacion);
        tipo_Radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = tipo_Radio.findViewById(checkedId);
                int index = tipo_Radio.indexOfChild(radioButton);

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
        equipos = (Spinner) view.findViewById(R.id.spinner_actualizar_clasificacion_registrar_seleccion_equipo);
        Otros.spinner_rellenar_equipos(equipos, array_equipos, this.getActivity());
        equipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_eq = array_equipos.get(position).getId();
                array_posicion = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        registrar = (Button)view.findViewById(R.id.registrar_clasificacion);
        registrar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                try {
                    nombre = nombre_EditText.getText().toString();
                    url = url_EditText.getText().toString();
                    if (!nombre.equals("")){
                        nombre_EditText.setText("");
                        url_EditText.setText("");
                        String insert_clasificacion = Inserts.constructor_sentencia_insertar_clasificaciones(nombre,tipo,url,id_eq);
                        Inserts.insert(getContext(), insert_clasificacion);
                        int clasificacion_id = Selects.select_last_id(getContext(),"clasificaciones");
                        String insert_clasificacion_equipo_principal = Inserts.constructor_sentencia_insertar_clasificacion_equipos_liga_principal(array_equipos.get(array_posicion).getNombre(),id_eq, clasificacion_id);
                        Inserts.insert(getContext(), insert_clasificacion_equipo_principal);
                        Toast exito = (Toast.makeText(getContext(),"Registrado en Base de Datos",Toast.LENGTH_SHORT));
                        exito.show();
                    }else{
                        Toast vacio = (Toast.makeText(getContext(),"Es necesario rellenar el nombre",Toast.LENGTH_LONG));
                        vacio.show();
                    }

                }catch (Exception e){
                    Toast error = (Toast.makeText(getContext(),"Error al Registrar",Toast.LENGTH_SHORT));
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
