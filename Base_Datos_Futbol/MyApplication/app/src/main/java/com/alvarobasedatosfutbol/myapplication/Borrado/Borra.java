package com.alvarobasedatosfutbol.myapplication.Borrado;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Deletes;
import com.alvarobasedatosfutbol.myapplication.Base_Datos.Selects;
import com.alvarobasedatosfutbol.myapplication.Otros.Otros;
import com.alvarobasedatosfutbol.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Borra.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Borra#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Borra extends Fragment {
    //Definimos los Spinner
    String [] valores;
    private OnFragmentInteractionListener mListener;
    RadioGroup r_group;
    Integer [] ids;
    String tabla;
    int identificacion;
    int posicion;
    Spinner eleccion;
    Button boton_borrar;
    Toast exito;
    Toast error;

    public Borra() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Borra.
     */
    // TODO: Rename and change types and number of parameters
    public static Borra newInstance(String param1, String param2) {
        Borra fragment = new Borra();
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
        //Definimos un view para poder usar los objetos
        View view = inflater.inflate(R.layout.fragment_alta_borrar, container, false);
        r_group = (RadioGroup) view.findViewById(R.id.radio_group);
        //Cargamos los Spinner
        tabla = "equipos";
        String select = Selects.constructor_sentencia_select_all_equipos();
        ids = Selects.select_integer(getContext(),select, 0);
        valores = Selects.select_string(getContext(),select, 1);

        //Cogemos la posicion del spinner y asignamos a identificacion el id que queremos borrar. posicion=posicion de array
        eleccion = (Spinner)view.findViewById(R.id.spinner_opcion_borrar);
        Otros.spinner_rellenar(eleccion, valores, this.getActivity());
        eleccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                posicion = eleccion.getSelectedItemPosition();
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
                    case 0: // equipos
                        radio_equipo();
                        break;
                    case 1: // jugadores
                        radio_jugador();
                        break;
                }

            }
        });

        //Definimos dos toast para mostrar mensajes
        exito = Toast.makeText(getContext(),"Borrado Completado", Toast.LENGTH_SHORT);
        error = Toast.makeText(getContext(),"Error al Borrar", Toast.LENGTH_SHORT);
        //Asociamos a nuestra variable la identificacion del boton
        boton_borrar = (Button)view.findViewById(R.id.borrar) ;
        //Funcion del boton ONCLICK que hace que ocurra algo al pulsar
        boton_borrar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                try {
                    //Borrado en Base de datos, le pasamos la tabla y el id a borrar
                    String st_delete = Deletes.constructor_delete(tabla,identificacion);
                    //String st_delete_jugadores_relacionados = Deletes.constructor_delete_jugadores_relacionados(identificacion);
                    Deletes.delete(getContext(),st_delete);
                    //Deletes.delete(getContext(),st_delete_jugadores_relacionados);
                    exito.show();
                    if (tabla == "equipos"){radio_equipo();}else radio_jugador();
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

    private void radio_equipo(){
        tabla = "equipos";
        String select = Selects.constructor_sentencia_select_all_equipos();
        ids = Selects.select_integer(getContext(),select, 0);
        valores = Selects.select_string(getContext(), select, 1);
        Otros.spinner_rellenar(eleccion, valores, this.getActivity());

    }

    private void radio_jugador(){
        tabla = "jugadores";
        //ids = new Integer[]{-1,-1};
        //valores = new String[]{"Equipo", "Jugador"};
        String select = Selects.constructor_sentencia_jugadores_delete();
        ids = Selects.select_integer(getContext(),select, 0);
        valores = Selects.select_string_combinacion(getContext(), select, 1, 4);
        Otros.spinner_rellenar(eleccion, valores, this.getActivity());

    }

}
