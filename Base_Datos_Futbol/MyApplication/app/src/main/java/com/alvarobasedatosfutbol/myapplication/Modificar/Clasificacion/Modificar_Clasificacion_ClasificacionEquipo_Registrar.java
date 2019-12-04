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
import android.widget.Spinner;
import android.widget.Toast;

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Inserts;
import com.alvarobasedatosfutbol.myapplication.Base_Datos.Selects;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Clasificacion;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Equipo;
import com.alvarobasedatosfutbol.myapplication.Otros.Otros;
import com.alvarobasedatosfutbol.myapplication.R;

import java.util.ArrayList;


/**
        Fragment para dar de Alta equipos
        */
public class Modificar_Clasificacion_ClasificacionEquipo_Registrar extends Fragment {

    private OnFragmentInteractionListener mListener;

    //Cargamos las variables y objetos que vamos a necesitar
    Button registrar;
    String nombre = "";
    EditText nombre_EditText;
    Spinner clasificaciones;
    ArrayList<Clase_Clasificacion> array_clasificaciones = new ArrayList();
    int id_clasificacion;
    Spinner equipos;
    ArrayList<Clase_Equipo> array_equipos = new ArrayList();
    int id_equipo;

    public static Modificar_Clasificacion_ClasificacionEquipo_Registrar newInstance(String param1, String param2) {
        Modificar_Clasificacion_ClasificacionEquipo_Registrar fragment = new Modificar_Clasificacion_ClasificacionEquipo_Registrar();
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
        View view = inflater.inflate(R.layout.fragment_actualizar_clasificacion_clasificacionequipo_registrar, container, false);
        nombre_EditText = (EditText)view.findViewById(R.id.nombre_clasificacionEquipo);

        String select_equipos = Selects.constructor_sentencia_select_equipos_con_clasificaciones();
        array_equipos = Selects.select_clase_equipo(getContext(),select_equipos);
        equipos = (Spinner) view.findViewById(R.id.spinner_actualizar_clasificacion_clasificacionEquipo_registrar_seleccion_equipo);
        clasificaciones = (Spinner) view.findViewById(R.id.spinner_actualizar_clasificacion_clasificacionEquipo_registrar_seleccion_clasificacion);
        Otros.spinner_rellenar_equipos(equipos, array_equipos, this.getActivity());
        equipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_equipo = array_equipos.get(position).getId();
                setSpinner_Clasificacion(id_equipo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        clasificaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_clasificacion = array_clasificaciones.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        registrar = (Button)view.findViewById(R.id.registrar_clasificacionEquipo);
        registrar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                try {
                    nombre = nombre_EditText.getText().toString();
                    if (!nombre.equals("")){
                        nombre_EditText.setText("");
                        String insert_clasificacionEquipo = Inserts.constructor_sentencia_insertar_clasificacion_equipos_liga(nombre, id_clasificacion);
                        Inserts.insert(getContext(), insert_clasificacionEquipo);
                        Toast.makeText(getContext(),"Registrado en Base de Datos",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(),"El Nombre no puede estar vacío",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getContext(),"Error al Registrar",Toast.LENGTH_SHORT).show();
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

    private void setSpinner_Clasificacion(int equipo_id){
        String select = Selects.constructor_sentencia_select_clasificaciones(equipo_id);
        array_clasificaciones = Selects.select_clase_clasificacion(getContext(),select);
        Otros.spinner_rellenar_clasififcaciones(clasificaciones, array_clasificaciones, this.getActivity());
    }
}
