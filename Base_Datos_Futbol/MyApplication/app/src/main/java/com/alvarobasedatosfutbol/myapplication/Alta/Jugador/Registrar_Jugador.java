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

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Inserts;
import com.alvarobasedatosfutbol.myapplication.Base_Datos.Selects;
import com.alvarobasedatosfutbol.myapplication.Otros.Otros;
import com.alvarobasedatosfutbol.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Registrar_Jugador.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Registrar_Jugador#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registrar_Jugador extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    Button registrar;
    EditText nombre_jugador;
    Toast exito;
    Toast error;
    Toast vacio;
    int identificacion;
    String posicion_jugador = "Jugador";
    EditText dorsal;
    private Spinner equipos;
    String [] valores;
    RadioGroup r_group;
    Integer [] ids;
    int posicion;


    private OnFragmentInteractionListener mListener;

    public Registrar_Jugador() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Alta_Jugador.
     */
    // TODO: Rename and change types and number of parameters
    public static Registrar_Jugador newInstance(String param1, String param2) {
        Registrar_Jugador fragment = new Registrar_Jugador();
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

        View view = inflater.inflate(R.layout.fragment_alta_jugador_registrar, container, false);

        exito = Toast.makeText(getContext(),"Registrado en Base de Datos", Toast.LENGTH_SHORT);
        error = Toast.makeText(getContext(),"Error al Importar", Toast.LENGTH_SHORT);
        vacio = Toast.makeText(getContext(),"Error: Nombre Vacio", Toast.LENGTH_SHORT);

        dorsal = (EditText)view.findViewById(R.id.dorsal);
        nombre_jugador = (EditText)view.findViewById(R.id.nombre_jugador);
        registrar = (Button)view.findViewById(R.id.registrar_jugador);
        r_group = (RadioGroup)view.findViewById(R.id.radio_group_posicion);

        String select = Selects.constructor_sentencia_select_equipos_temp_abierta();
        ids = Selects.select_integer(getContext(),select, 0);
        valores = Selects.select_string(getContext(),select, 1);



        //Cogemos la posicion del spinner y asignamos a identificacion el id que queremos borrar. posicion=posicion de array
        equipos = (Spinner)view.findViewById(R.id.spinner_seleccion_equipo);
        Otros.spinner_rellenar(equipos, valores, this.getActivity());
        equipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                posicion = equipos.getSelectedItemPosition();
                identificacion = ids[posicion];
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });

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

        registrar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                try {
                    if (!nombre_jugador.getText().toString().equals("") && !dorsal.getText().toString().equals("")){
                        String sentencia = Inserts.constructor_sentencia_insertar_jugadores(nombre_jugador.getText().toString(), Integer.parseInt(dorsal.getText().toString()), posicion_jugador, identificacion);
                        Inserts.insert(getContext(),sentencia);
                        nombre_jugador.setText("");
                        dorsal.setText("");
                        exito.show();
                    }else vacio.show();

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
}
