package com.alvarobasedatosfutbol.myapplication.Alta.Jugador;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alvarobasedatosfutbol.myapplication.R;

public class Alta_Jugador extends Fragment {
    private Alta_Jugador.OnFragmentInteractionListener mListener;

    public static Alta_Jugador newInstance(String param1, String param2) {
        Alta_Jugador fragment = new Alta_Jugador();
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
        View view = inflater.inflate(R.layout.fragment_alta_jugador_page_viewer, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager_alta_jugadores);
        viewPager.setAdapter(new Alta_Jugador_Page_Adapter(this.getChildFragmentManager()));
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
        if (context instanceof Alta_Jugador.OnFragmentInteractionListener) {
            mListener = (Alta_Jugador.OnFragmentInteractionListener) context;
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
