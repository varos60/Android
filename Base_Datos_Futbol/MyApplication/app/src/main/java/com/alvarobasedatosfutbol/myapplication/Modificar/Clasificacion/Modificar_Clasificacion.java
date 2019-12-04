package com.alvarobasedatosfutbol.myapplication.Modificar.Clasificacion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alvarobasedatosfutbol.myapplication.MainActivity;
import com.alvarobasedatosfutbol.myapplication.R;


/**
 Fragment para dar de Alta Clasificaciones
 */
public class Modificar_Clasificacion extends Fragment {

    private OnFragmentInteractionListener mListener;

    //Cargamos las variables y objetos que vamos a necesitar

    public static Modificar_Clasificacion newInstance(String param1, String param2) {
        Modificar_Clasificacion fragment = new Modificar_Clasificacion();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    //Cuando se crea el fragment ejecuta lo que tiene dentro
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Definimos un view para poder usar los objetos
        View view = inflater.inflate(R.layout.fragment_actualizar_clasificacion_page_viewer, container, false);
        //Definimos dos toast para mostrar mensajes
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager_modificar_clasificacion);
        viewPager.setAdapter(new Modificar_Clasificacion_Page_Adapter(this.getChildFragmentManager()));
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.appbartabs);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(tabLayout.GRAVITY_CENTER);
        tabLayout.setupWithViewPager(viewPager);
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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_actualizar_clasificacion, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.actualizar_clasificacion_alta_equipo){
            TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.appbartabs);
            tabLayout.setVisibility(View.GONE);
            ((MainActivity)getActivity()).toggleBottonNavigitionDrawer(true);
            setFragment(new Modificar_Clasificacion_ClasificacionEquipo_Registrar(), "Modificar_Clasificacion_ClasificacionEquipo_Registrar");
        }else if(id == R.id.actualizar_clasificacion_modificar_equipo){

        }else if(id == R.id.actualizar_clasificacion_borrar_equipo){

        }else if(id == R.id.actualizar_clasificacion_borrar_clasificacion){
            TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.appbartabs);
            tabLayout.setVisibility(View.GONE);
            ((MainActivity)getActivity()).toggleBottonNavigitionDrawer(true);
            setFragment(new Modificar_Clasificacion_Borrar_Clasificacion(), "Modificar_Clasificacion_Borrar_Clasificacion");
        }
        return super.onOptionsItemSelected(item);
    }
    public void setFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment, tag)
                .commit();
    }
}
