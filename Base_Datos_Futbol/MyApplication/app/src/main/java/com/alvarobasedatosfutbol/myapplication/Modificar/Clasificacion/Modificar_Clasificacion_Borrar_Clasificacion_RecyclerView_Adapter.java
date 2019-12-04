package com.alvarobasedatosfutbol.myapplication.Modificar.Clasificacion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Clasificacion;
import com.alvarobasedatosfutbol.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Álvaro on 16/09/2017.
 */

public class Modificar_Clasificacion_Borrar_Clasificacion_RecyclerView_Adapter extends RecyclerView.Adapter<Modificar_Clasificacion_Borrar_Clasificacion_RecyclerView_Adapter.RecyclerView_Borrar_Clasificacion_Adapter_View_Holder>{

    ArrayList<Clase_Clasificacion> array_clasificaciones;
    private onCheckChange onCheckChange;
    private SparseBooleanArray mSelectedItemsIds;
    private Context context;

    public interface onCheckChange{
        void onCheckedChange(boolean check);
    }

    public Modificar_Clasificacion_Borrar_Clasificacion_RecyclerView_Adapter(Context context, ArrayList<Clase_Clasificacion> array_clasificaciones, onCheckChange onCheckChange){
        this.array_clasificaciones = array_clasificaciones;
        this.onCheckChange = onCheckChange;
        this.context = context;
        mSelectedItemsIds = new SparseBooleanArray();
    }
    @Override
    public RecyclerView_Borrar_Clasificacion_Adapter_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_actualizar_clasificacion_borrar_clasificacion_recycle_view, parent, false);
        RecyclerView_Borrar_Clasificacion_Adapter_View_Holder rjavh = new RecyclerView_Borrar_Clasificacion_Adapter_View_Holder(itemview);
        return rjavh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView_Borrar_Clasificacion_Adapter_View_Holder clasificaciones_vh, int position) {
        Clase_Clasificacion clasificacion = array_clasificaciones.get(position);
        clasificaciones_vh.clasificacion_check.setText(clasificacion.getNombre());
        clasificaciones_vh.clasificacion_check.setOnCheckedChangeListener(null);
        clasificaciones_vh.clasificacion_check.setChecked(array_clasificaciones.get(position).isSelected());
        clasificaciones_vh.clasificacion_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                array_clasificaciones.get(clasificaciones_vh.getAdapterPosition()).setSelected(isChecked);
                onCheckChange.onCheckedChange(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.array_clasificaciones.size();
    }


    public class RecyclerView_Borrar_Clasificacion_Adapter_View_Holder extends RecyclerView.ViewHolder{
        private CheckBox clasificacion_check;

        public RecyclerView_Borrar_Clasificacion_Adapter_View_Holder(View itemView){
            super(itemView);
            clasificacion_check =(CheckBox) itemView.findViewById(R.id.actualizar_calsificacion_borrar_calsificacion_checkbox);
        }
    }

    public void removeSelected() {
        for (int i = 0; i < array_clasificaciones.size(); i++){
            Clase_Clasificacion clasificacion = array_clasificaciones.get(i);
            if (clasificacion.isSelected()){
                array_clasificaciones.get(i).setSelected(false);
            }
        }
        notifyDataSetChanged();
    }
}
