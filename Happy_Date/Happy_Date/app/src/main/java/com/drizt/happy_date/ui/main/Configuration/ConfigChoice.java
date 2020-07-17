package com.drizt.happy_date.ui.main.Configuration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;

import androidx.fragment.app.Fragment;

public class ConfigChoice extends Fragment {

    Button players_config;
    Button challenges_config;

    public static ConfigChoice newInstance(String param1, String param2) {
        ConfigChoice fragment = new ConfigChoice();
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
        View view = inflater.inflate(R.layout.config_choice, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        players_config = view.findViewById(R.id.players_config);
        challenges_config = view.findViewById(R.id.challenge_config);

        players_config.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                Fragment config_players = new ConfigPlayers();
                ((MainActivity)getActivity()).setFragment(config_players, "Jugadores", "Jugadores", null);
            }
        });

        challenges_config.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                Fragment config_challenges = new ConfigCategorias();
                ((MainActivity)getActivity()).setFragment(config_challenges, "Categorias", "Categorias", null);
            }
        });

        return view;
    }



}