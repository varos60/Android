package com.alvarobasedatosfutbol.myapplication.Otros;

/**
 * Created by Álvaro on 18/01/2018.
 */

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Clasificacion;
import com.alvarobasedatosfutbol.myapplication.Modificar.Clasificacion.Modificar_Clasificacion_Borrar_Clasificacion;
import com.alvarobasedatosfutbol.myapplication.Modificar.Clasificacion.Modificar_Clasificacion_Borrar_Clasificacion_RecyclerView_Adapter;
import com.alvarobasedatosfutbol.myapplication.R;

import java.util.ArrayList;

/**
 * Created by SONU on 22/03/16.
 */
public class Toolbar_ActionMode_Callback implements ActionMode.Callback {

    private Context context;
    private ArrayList<Clase_Clasificacion> message_models;
    private Modificar_Clasificacion_Borrar_Clasificacion_RecyclerView_Adapter recyclerView_adapter;
    private FragmentManager fragmentManager;


    public Toolbar_ActionMode_Callback(Context context, ArrayList<Clase_Clasificacion> message_models, Modificar_Clasificacion_Borrar_Clasificacion_RecyclerView_Adapter recyclerView_adapter, FragmentManager fragmentManager) {
        this.context = context;
        this.message_models = message_models;
        this.recyclerView_adapter = recyclerView_adapter;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_delete, menu);//Inflate the menu over action mode
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        //Sometimes the meu will not be visible so for that we need to set their visibility manually in this method
        //So here show action menu according to SDK Levels
        if (Build.VERSION.SDK_INT < 11) {
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_delete), MenuItemCompat.SHOW_AS_ACTION_NEVER);
        } else {
            menu.findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                Fragment modificar_Clasificacion_Borrar_Clasificacion = fragmentManager.findFragmentByTag("Modificar_Clasificacion_Borrar_Clasificacion");
                if (modificar_Clasificacion_Borrar_Clasificacion != null){
                    ((Modificar_Clasificacion_Borrar_Clasificacion) modificar_Clasificacion_Borrar_Clasificacion).borrar_clasificaciones_seleccionadas();
                }
                break;
        }
        return false;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        recyclerView_adapter.removeSelected();
        Fragment modificar_Clasificacion_Borrar_Clasificacion = fragmentManager.findFragmentByTag("Modificar_Clasificacion_Borrar_Clasificacion");
        if (modificar_Clasificacion_Borrar_Clasificacion != null){
            ((Modificar_Clasificacion_Borrar_Clasificacion) modificar_Clasificacion_Borrar_Clasificacion).setNullToActionMode();
        }
    }
}