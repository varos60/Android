package com.alvarobasedatosfutbol.myapplication.Modificar.Clasificacion;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Deletes;
import com.alvarobasedatosfutbol.myapplication.Base_Datos.Selects;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Clasificacion;
import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Equipo;
import com.alvarobasedatosfutbol.myapplication.Otros.Otros;
import com.alvarobasedatosfutbol.myapplication.Otros.Toolbar_ActionMode_Callback;
import com.alvarobasedatosfutbol.myapplication.R;

import java.util.ArrayList;


/**
 Fragment para dar de Alta Clasificaciones
 */
public class Modificar_Clasificacion_Borrar_Clasificacion extends Fragment {

    private OnFragmentInteractionListener mListener;
    ArrayList<Clase_Equipo> array_equipos = new ArrayList();
    ArrayList<Clase_Clasificacion> array_clasificaciones = new ArrayList();
    private Spinner equipos;
    int id_eq;
    int sp_pos_eq;
    private RecyclerView recyclerView;
    private Modificar_Clasificacion_Borrar_Clasificacion_RecyclerView_Adapter adaptador;
    boolean selected = false;
    private ActionMode mActionMode;
    private FragmentManager fragmentManager;

    public static Modificar_Clasificacion_Borrar_Clasificacion newInstance(String param1, String param2) {
        Modificar_Clasificacion_Borrar_Clasificacion fragment = new Modificar_Clasificacion_Borrar_Clasificacion();
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
        View view = inflater.inflate(R.layout.fragment_actualizar_clasificacion_borrar_clasificacion, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.actualizar_clasificacion_borrar_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        String select_equipos = Selects.constructor_sentencia_select_equipos_con_clasificaciones();
        array_equipos = Selects.select_clase_equipo(getContext(),select_equipos);
        equipos = (Spinner) view.findViewById(R.id.spinner_actualizar_clasificacion_borrar_seleccion_equipo);
        Otros.spinner_rellenar_equipos(equipos, array_equipos, this.getActivity());
        equipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    mActionMode.finish();
                }
                sp_pos_eq = position;
                id_eq = array_equipos.get(sp_pos_eq).getId();
                final String select = Selects.constructor_sentencia_select_clasificaciones_delete(id_eq);
                array_clasificaciones = Selects.select_clase_clasificacion_delete_mark(getContext(),select);
                adaptador = new Modificar_Clasificacion_Borrar_Clasificacion_RecyclerView_Adapter(getContext(), array_clasificaciones, new Modificar_Clasificacion_Borrar_Clasificacion_RecyclerView_Adapter.onCheckChange() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onCheckedChange(boolean check) {
                        selected = false;
                        for(Clase_Clasificacion clasificacion : array_clasificaciones){
                            if(clasificacion.isSelected()){
                                selected = true;
                                break;
                            }
                        }
                        if (selected){
                            if(mActionMode == null) {
                                fragmentManager = getFragmentManager();
                                mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new Toolbar_ActionMode_Callback(getContext(), array_clasificaciones, adaptador, fragmentManager));
                                mActionMode.setTitle("Borrado");
                                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                            }
                        }else{
                            if (mActionMode != null) {
                                mActionMode.finish();
                            }
                        }
                    }
                });
                recyclerView.setAdapter(adaptador);
                recyclerView.setItemViewCacheSize(array_clasificaciones.size());
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
    public void setNullToActionMode() {
        if (mActionMode != null){
            mActionMode = null;
        }
    }

    public void borrar_clasificaciones_seleccionadas(){
        String delete = null;
        for(Clase_Clasificacion clasificacion : array_clasificaciones){
            if(clasificacion.isSelected()){
                delete = Deletes.constructor_delete_clasificaciones(delete, "clasificaciones", clasificacion.getId());
            }
        }
        try {
            Log.i("Delete", delete);
            Deletes.delete(getContext(), delete);
            Toast exito = (Toast.makeText(getContext(),"Borrado en Base de Datos", Toast.LENGTH_SHORT));
            exito.show();
            String select = Selects.constructor_sentencia_select_clasificaciones_delete(id_eq);
            array_clasificaciones = Selects.select_clase_clasificacion_delete_mark(getContext(),select);
            adaptador.array_clasificaciones = array_clasificaciones;
            adaptador.notifyDataSetChanged();
            mActionMode.finish();
        }catch (Exception e){
            Toast.makeText(getContext(),"Error al Borrar",Toast.LENGTH_SHORT).show();
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
