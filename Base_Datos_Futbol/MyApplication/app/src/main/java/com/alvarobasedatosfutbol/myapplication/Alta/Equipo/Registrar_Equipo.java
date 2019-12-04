package com.alvarobasedatosfutbol.myapplication.Alta.Equipo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Inserts;
import com.alvarobasedatosfutbol.myapplication.R;


/**
        Fragment para dar de Alta equipos
        */
public class Registrar_Equipo extends Fragment {

    private OnFragmentInteractionListener mListener;

    //Cargamos las variables y objetos que vamos a necesitar
    Button registrar;
    EditText nombre_eq;
    int tipo = 0;
    Toast exito;
    Toast error;
    Toast vacio;
    RadioGroup r_group;

    public static Registrar_Equipo newInstance(String param1, String param2) {
        Registrar_Equipo fragment = new Registrar_Equipo();
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
        View view = inflater.inflate(R.layout.fragment_alta_equipo_registrar, container, false);

        //Definimos dos toast para mostrar mensajes
        exito = Toast.makeText(getContext(),"Registrado en Base de Datos", Toast.LENGTH_SHORT);
        error = Toast.makeText(getContext(),"Error al Importar", Toast.LENGTH_SHORT);
        vacio = Toast.makeText(getContext(),"Error: Nombre Vacio", Toast.LENGTH_SHORT);
        //Definimos el campo de edicion de texto para operar con el
        nombre_eq = (EditText)view.findViewById(R.id.nombre_eq);
        //Definimos el boton para poder operar con el
        registrar = (Button)view.findViewById(R.id.registrar_eq);
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
        //Funcion del boton ONCLICK que hace que ocurra algo al pulsar
        registrar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                try {
                    //Carga la base de datos, la hacemos editable, ejecutamos la sentencia cogiendo el campo edittext y cerramos la base de datos. Por ultimo mostramos el mensaje de exito. Si no funciona mostrara el mensaje de que no se pudo.
                    if (!nombre_eq.getText().toString().equals("")){
                        Inserts.insert(getContext(),Inserts.constructor_sentencia_insertar_equipos(nombre_eq.getText().toString(),tipo));
                        nombre_eq.setText("");
                        exito.show()
                        ;}else  vacio.show();
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
