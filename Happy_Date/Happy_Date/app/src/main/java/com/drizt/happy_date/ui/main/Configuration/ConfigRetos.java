package com.drizt.happy_date.ui.main.Configuration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ConfigRetos extends Fragment {

    private static final String ARG_PARAM1 = "position";
    private int [] mParam1;
    AlertDialog alert_dialog;
    Button add;
    RecyclerView retos_rv;
    ConfigRetosAdapter adapter;

    public ConfigRetos() {}

    public static ConfigRetos newInstance(int [] param1) {
        ConfigRetos fragment = new ConfigRetos();
        Bundle args = new Bundle();
        args.putIntArray(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getIntArray(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.config_retos, container, false);
        add = view.findViewById(R.id.add_reto);
        retos_rv = view.findViewById(R.id.retos_rv);

        if (((MainActivity)getActivity()).getSupportActionBar().getTitle().equals("Configurar Retos"))
            try {
                ((MainActivity)getActivity()).getSupportActionBar().setTitle(((MainActivity)getActivity()).categorias.get(mParam1[0]).getName());
            }catch (Exception e) {
                Log.e("Excepcion", String.valueOf(e));
            }

        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        final EditText edit_text = new EditText(getContext());
        edit_text.setText(((MainActivity)getActivity()).categorias.get(mParam1[0]).getName());
        alert.setTitle("Categoría");
        alert.setMessage("Introduzca nombre de la Categoría");
        alert.setView(edit_text);
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = edit_text.getText().toString();
                ((MainActivity)getActivity()).categorias.get(mParam1[0]).setName(name);
                ((MainActivity)getActivity()).saveJson();
                try {
                    ((MainActivity)getActivity()).getSupportActionBar().setTitle(name);
                }catch (Exception e) {
                    Log.e("Excepcion", String.valueOf(e));
                }
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
                Fragment edit_retos = new ConfigEditReto();
                int [] args = new int []{mParam1[0]};
                ((MainActivity)getActivity()).setFragment(edit_retos, "Editar Reto", "Nuevo Reto" ,args);
            }
        });

        retos_rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        retos_rv.setHasFixedSize(true);
        adapter = new ConfigRetosAdapter(((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges());
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment edit_retos = new ConfigEditReto();
                int [] args = new int []{mParam1[0], retos_rv.getChildAdapterPosition(v)};
                ((MainActivity)getActivity()).setFragment(edit_retos, "Editar Reto", "Editar Reto" ,args);
            }
        });
        retos_rv.setAdapter(adapter);

        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_edit_delete, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            deleteCategoria();
        }else if(
            id == R.id.action_edit) {
            editCategoria();
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteCategoria(){
        ((MainActivity)getActivity()).categorias.remove(mParam1[0]);
        ((MainActivity)getActivity()).saveJson();
        Toast.makeText(getContext(), "Borrado", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).backBotton();
    }

    public void editCategoria(){
        alert_dialog.show();
    }
}