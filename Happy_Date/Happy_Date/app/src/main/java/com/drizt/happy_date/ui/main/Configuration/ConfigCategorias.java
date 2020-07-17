package com.drizt.happy_date.ui.main.Configuration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.drizt.happy_date.Clases.Categorie;
import com.drizt.happy_date.Clases.Challenge;
import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ConfigCategorias extends Fragment {

    Button add;
    RecyclerView categorias_rv;
    AlertDialog alert_dialog;
    ConfigCategoriasAdapter adapter;

    public static ConfigCategorias newInstance(String param1, String param2) {
        ConfigCategorias fragment = new ConfigCategorias();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.config_categorias, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add = view.findViewById(R.id.add_categoria);
        categorias_rv = view.findViewById(R.id.categorias_rv);

        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText edit_text = new EditText(getContext());
        alert.setTitle("Categoría");
        alert.setMessage("Introduzca nombre de la Categoría");
        alert.setView(edit_text);
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = edit_text.getText().toString();
                int last_id = ((MainActivity)getActivity()).categorias.get(((MainActivity)getActivity()).categorias.size()-1).getId()+1;
                ArrayList<Challenge> empty_challenge = new ArrayList<>();
                Categorie nueva_categoria = new Categorie(last_id, name, empty_challenge);
                ((MainActivity)getActivity()).categorias.add(nueva_categoria);
                ((MainActivity)getActivity()).saveJson();
                categorias_rv.setAdapter(adapter);
                try {
                    dialog.cancel();
                }catch (Exception e){
                    dialog.cancel();
                }

            }
        });
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alert_dialog = alert.create();

        add.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                alert_dialog.show();
            }
        });

        categorias_rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        categorias_rv.setHasFixedSize(true);
        adapter = new ConfigCategoriasAdapter(((MainActivity)getActivity()).categorias);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment config_retos = new ConfigRetos();
                int [] args = new int []{categorias_rv.getChildAdapterPosition(v)};
                ((MainActivity)getActivity()).setFragment(config_retos, "Configurar Retos", ((MainActivity)getActivity()).categorias.get(categorias_rv.getChildAdapterPosition(v)).getName() ,args);
            }
        });
        categorias_rv.setAdapter(adapter);

        return view;
    }
}