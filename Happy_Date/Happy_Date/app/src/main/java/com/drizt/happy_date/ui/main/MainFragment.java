package com.drizt.happy_date.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;
import com.drizt.happy_date.ui.main.Configuration.ConfigChoice;
import com.drizt.happy_date.ui.main.Play.PlaySelectCategories;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    Button play;
    Button config;
    Button exit;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        play = view.findViewById(R.id.play);
        config = view.findViewById(R.id.config);
        exit = view.findViewById(R.id.salir);

        play.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                Fragment play_frament = new PlaySelectCategories();
                ((MainActivity)getActivity()).setFragment(play_frament, "Selecciona Categorias", "Selecciona Categorias", null);
            }
        });

        config.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                Fragment config_frament = new ConfigChoice();
                ((MainActivity)getActivity()).setFragment(config_frament, "Configuracion", "Configuracion", null);
            }
        });

        exit.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

}