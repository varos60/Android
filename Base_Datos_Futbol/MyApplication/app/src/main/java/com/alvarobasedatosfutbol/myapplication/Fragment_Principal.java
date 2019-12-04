package com.alvarobasedatosfutbol.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Base_Datos;

public class Fragment_Principal extends Fragment {

    private OnFragmentInteractionListener mListener;
    AlertDialog alert_dialog;
    SQLiteDatabase bd;

    //Cargamos las variables y objetos que vamos a necesitar

    public static Fragment_Principal newInstance(String param1, String param2) {
        Fragment_Principal fragment = new Fragment_Principal();
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
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText edit_text_sql = new EditText(getContext());
        alert.setTitle("Sentencia SQL");
        alert.setMessage("Introduzca sentencia SQL a ejecutar");
        alert.setView(edit_text_sql);
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               String sentencia = edit_text_sql.getText().toString();
                Base_Datos base_data = new Base_Datos(getContext(), Base_Datos.bd_name, null, Base_Datos.bd_version);
                bd =  base_data.getWritableDatabase();
                try {
                    bd.execSQL(sentencia.toString());
                    Toast exito;
                    exito = Toast.makeText(getContext(),"Ejecucion Exitosa", Toast.LENGTH_SHORT);
                    exito.show();
                    dialog.cancel();
                }catch (Exception e){
                    Toast exito;
                    exito = Toast.makeText(getContext(),"Error en la ejecucion", Toast.LENGTH_SHORT);
                    exito.show();
                    dialog.cancel();
                }
                bd.close();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alert_dialog = alert.create();
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
        setHasOptionsMenu(true);
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
        inflater.inflate(R.menu.menu_principal_sql, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Boton del menu que cierra la bd hace copia y cierra la aplicacion
        if (id == R.id.SQL) {
            alert_dialog.show();
        }else if(id == R.id.Recover){
            ((MainActivity)getActivity()).getPermision();
            ((MainActivity)getActivity()).FilePicker();
        }
        return super.onOptionsItemSelected(item);
    }
}
