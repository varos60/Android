package com.alvarobasedatosfutbol.myapplication.Modificar.Jugador;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Selects;
import com.alvarobasedatosfutbol.myapplication.Base_Datos.Updates;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Equipo;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Jugador;
import com.alvarobasedatosfutbol.myapplication.Otros.DividerItemDecoration;
import com.alvarobasedatosfutbol.myapplication.Otros.Otros;
import com.alvarobasedatosfutbol.myapplication.R;

import java.util.ArrayList;


/**
 Fragment para actualizar los datos de Jugadores
 */
public class Modificar_Jugador extends Fragment {

    private OnFragmentInteractionListener mListener;

    //Cargamos las variables y objetos que vamos a necesitar

    Toast exito;
    Toast error;
    Toast vacio;
    int registros_error = 0;
    Spinner plantilla;
    Button actualizar;
    private ArrayList<Clase_Equipo> array_equipos = new ArrayList();
    private ArrayList<Clase_Jugador>array_jugadores = new ArrayList();
    String [] equipo_nombre;
    int posicion;
    int tipo_equipo;
    TextView partidos_suplente;
    private RecyclerView recyclerView;
    private RecyclerView_Jugador_Adapter adaptador;
    //private List<Integer[]> valores_jugador_data;
    //Integer[] valores_jugador = new Integer[1];



    public static Modificar_Jugador newInstance(String param1, String param2) {
        Modificar_Jugador fragment = new Modificar_Jugador();
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
        View view = inflater.inflate(R.layout.fragment_actualizar_jugador, container, false);
        actualizar = (Button)view.findViewById(R.id.act_jugador);
        //partidos_suplente = (TextView)view.findViewById(R.id.Part_supl);
        //Definimos dos toast para mostrar mensajes
        error = Toast.makeText(getContext(),"Error al Actualizar algún registor", Toast.LENGTH_SHORT);
        vacio = Toast.makeText(getContext(),"No hay datos", Toast.LENGTH_SHORT);
        exito = Toast.makeText(getContext(),"Actualizados en Base de Datos", Toast.LENGTH_SHORT);
        String select_equipos = Selects.constructor_sentencia_select_all_equipos();
        array_equipos = Selects.select_clase_equipo(getContext(),select_equipos);
        equipo_nombre = Selects.select_string(getContext(),select_equipos, 1);
        plantilla = (Spinner)view.findViewById(R.id.spinner_plantilla_mod_jug);
        Otros.spinner_rellenar(plantilla, equipo_nombre, this.getActivity());
        recyclerView = (RecyclerView)view.findViewById(R.id.jugadores_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //recyclerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        //valores_jugador_data = new ArrayList<>(array_jugadores.size());

        plantilla.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                posicion = plantilla.getSelectedItemPosition();
                String select_jugadores = Selects.constructor_sentencia_jugadores_plantilla(array_equipos.get(posicion).getId());
                array_jugadores = Selects.select_clase_jugador(getContext(),select_jugadores);
                all_values_cero();
                adaptador = new RecyclerView_Jugador_Adapter(array_jugadores, array_equipos.get(posicion).getTipo(), new RecyclerView_Jugador_Adapter.OnEditTextChanged() {
                    @Override
                    public void onTextChanged(int position, String charSeq, int edit_text) {
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        layoutManager.scrollToPosition(position);
                        set_jugador_values(position, edit_text, charSeq);
                    }
                });
                recyclerView.setAdapter(adaptador);
                recyclerView.setItemViewCacheSize(array_jugadores.size());
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
        
        actualizar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                for (int i=0;i<array_jugadores.size();i++){
                        if (array_jugadores.get(i).getPartidos()!= 0 || array_jugadores.get(i).getPartidos_suplente()!= 0 || array_jugadores.get(i).getGoles()!= 0 || array_jugadores.get(i).getAsistencias()!= 0 || array_jugadores.get(i).getAmarillas()!= 0 || array_jugadores.get(i).getRojas()!= 0) {
                            actualizar_jugadores(array_jugadores, i);
                        }
                }
                all_values_cero();
                recyclerView.setAdapter(adaptador);
                switch (registros_error){
                    case 0 : vacio.show();
                        break;
                    case 1 : exito.show();
                        break;
                    case 2 : error.show();
                        break;
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void set_jugador_values(int position, int edit_text, String charSeq){
        if (!charSeq.equals("-") && !charSeq.equals(".") && !charSeq.equals(",")) {
            switch (edit_text) {
                case 0:
                    if(charSeq.equals("")){
                        array_jugadores.get(position).setPartidos(0);
                    }else{
                        array_jugadores.get(position).setPartidos(Integer.valueOf(charSeq));
                    }
                    break;
                case 1:
                    if(charSeq.equals("")){
                        array_jugadores.get(position).setPartidos_suplente(0);
                    }else{
                        array_jugadores.get(position).setPartidos_suplente(Integer.valueOf(charSeq));
                    }
                    break;
                case 2:
                    if(charSeq.equals("")){
                        array_jugadores.get(position).setGoles(0);
                    }else{
                        array_jugadores.get(position).setGoles(Integer.valueOf(charSeq));
                    }
                    break;
                case 3:
                    if(charSeq.equals("")){
                        array_jugadores.get(position).setAsistencias(0);
                    }else{
                        array_jugadores.get(position).setAsistencias(Integer.valueOf(charSeq));
                    }
                    break;
                case 4:
                    if(charSeq.equals("")){
                        array_jugadores.get(position).setAmarillas(0);
                    }else{
                        array_jugadores.get(position).setAmarillas(Integer.valueOf(charSeq));
                    }
                    break;
                case 5:
                    if(charSeq.equals("")){
                        array_jugadores.get(position).setRojas(0);
                    }else{
                        array_jugadores.get(position).setRojas(Integer.valueOf(charSeq));
                    }
                    break;
            }
        }
    }
    private void actualizar_jugadores(ArrayList<Clase_Jugador> array_jugadores, int posicion){
        int id = array_jugadores.get(posicion).getId();
        int partidos = array_jugadores.get(posicion).getPartidos();
        int partidos_suplente = array_jugadores.get(posicion).getPartidos_suplente();
        int goles = array_jugadores.get(posicion).getGoles();
        int asistencias = array_jugadores.get(posicion).getAsistencias();
        int amarillas = array_jugadores.get(posicion).getAmarillas();
        int rojas = array_jugadores.get(posicion).getRojas();
        String sentencia_update = Updates.update_jugadores_valores(id,partidos,partidos_suplente,goles,asistencias,amarillas,rojas);
        try{
        Updates.update(getContext(), sentencia_update);
            if (registros_error == 0){
                registros_error = 1;
            }
        }catch (Exception e){
            registros_error = 2;
        }
    }
    private void all_values_cero(){
        for (int i=0; i < array_jugadores.size();i++){
            array_jugadores.get(i).setPartidos(0);
            array_jugadores.get(i).setPartidos_suplente(0);
            array_jugadores.get(i).setAsistencias(0);
            array_jugadores.get(i).setGoles(0);
            array_jugadores.get(i).setAmarillas(0);
            array_jugadores.get(i).setRojas(0);
        }
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
