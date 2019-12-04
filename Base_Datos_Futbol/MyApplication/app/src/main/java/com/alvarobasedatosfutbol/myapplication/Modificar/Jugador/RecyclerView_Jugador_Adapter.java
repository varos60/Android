package com.alvarobasedatosfutbol.myapplication.Modificar.Jugador;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alvarobasedatosfutbol.myapplication.Clases.Clase_Jugador;
import com.alvarobasedatosfutbol.myapplication.R;

import java.util.ArrayList;

/**
 * Created by Álvaro on 16/09/2017.
 */

public class RecyclerView_Jugador_Adapter extends RecyclerView.Adapter<RecyclerView_Jugador_Adapter.RecyclerView_Jugador_Adapter_View_Holder>{

    ArrayList<Clase_Jugador> array_jugadores;
    int tipo_equipo;
    private OnEditTextChanged onEditTextChanged;


    public interface OnEditTextChanged {
        void onTextChanged(int position, String charSeq, int edit_text);
    }

    public RecyclerView_Jugador_Adapter (ArrayList<Clase_Jugador> array_jugadores, int tipo_equipo, OnEditTextChanged onEditTextChanged){
        this.array_jugadores = array_jugadores;
        this.tipo_equipo = tipo_equipo;
        this.onEditTextChanged = onEditTextChanged;
    }
    @Override
    public RecyclerView_Jugador_Adapter_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_actualizar_jugador_recycle_view, parent, false);
        RecyclerView_Jugador_Adapter_View_Holder rjavh = new RecyclerView_Jugador_Adapter_View_Holder(itemview);
        return rjavh;
    }

    @Override
    public void onBindViewHolder(RecyclerView_Jugador_Adapter_View_Holder jugadores_vh, final int position) {
        Clase_Jugador jugador = array_jugadores.get(position);
        jugadores_vh.dorsal_jugador.setText((Integer.toString(jugador.getDorsal())));
        jugadores_vh.nombre_jugador.setText(jugador.getNombre());
        jugadores_vh.partidos_jugador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(position, charSequence.toString(),0);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        jugadores_vh.partidos_suplentes_jugador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(position, charSequence.toString(),1);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        jugadores_vh.goles_jugador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(position, charSequence.toString(),2);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        jugadores_vh.asistencias_jugador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(position, charSequence.toString(),3);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        jugadores_vh.amarillas_jugador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(position, charSequence.toString(),4);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        jugadores_vh.rojas_jugador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(position, charSequence.toString(),5);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    public int getItemCount() {
        return this.array_jugadores.size();
    }



    public class RecyclerView_Jugador_Adapter_View_Holder extends RecyclerView.ViewHolder{
        private TextView dorsal_jugador;
        private TextView nombre_jugador;
        private EditText partidos_jugador;
        private EditText partidos_suplentes_jugador;
        private EditText amarillas_jugador;
        private EditText rojas_jugador;
        private EditText asistencias_jugador;
        private EditText goles_jugador;
        private TextInputLayout partidos_suplentes_jugador_layout;

        public RecyclerView_Jugador_Adapter_View_Holder(View itemView){
            super(itemView);
            dorsal_jugador=(TextView)itemView.findViewById(R.id.dorsal_jugador_modificar);
            nombre_jugador=(TextView)itemView.findViewById(R.id.nombre_jugador_modificar);
            partidos_jugador=(EditText) itemView.findViewById(R.id.edit_text_partidos);
            partidos_suplentes_jugador=(EditText) itemView.findViewById(R.id.edit_text_partidos_suplente);
            partidos_suplentes_jugador_layout=(TextInputLayout) itemView.findViewById(R.id.edit_text_partidos_suplente_layout);
            if (tipo_equipo == 0) {
                partidos_suplentes_jugador_layout.setVisibility(View.GONE);
            }else{partidos_suplentes_jugador_layout.setVisibility(View.VISIBLE);}
            amarillas_jugador=(EditText) itemView.findViewById(R.id.edit_text_amarillas);
            rojas_jugador=(EditText) itemView.findViewById(R.id.edit_text_rojas);
            asistencias_jugador=(EditText) itemView.findViewById(R.id.edit_text_asistencias);
            goles_jugador=(EditText) itemView.findViewById(R.id.edit_text_goles);
        }
    }
}
