package com.drizt.happy_date.ui.main.Configuration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;

import androidx.fragment.app.Fragment;

public class ConfigPlayers extends Fragment {
    LinearLayout player1_click;
    LinearLayout player2_click;
    ImageView gen_image_player1;
    TextView text_player1;
    ImageView gen_image_player2;
    TextView text_player2;


    public static ConfigPlayers newInstance(String param1) {
        ConfigPlayers fragment = new ConfigPlayers();
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
        View view = inflater.inflate(R.layout.config_players, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        player1_click = view.findViewById(R.id.player1_click);
        player2_click = view.findViewById(R.id.player2_click);

        player1_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment edit_players = new ConfigEditPlayer();
                int [] args = new int []{0};
                ((MainActivity)getActivity()).setFragment(edit_players, "Editar Jugador", "Editar Jugador", args);
            }
        });

        player2_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment edit_players = new ConfigEditPlayer();
                int [] args = new int []{1};
                ((MainActivity)getActivity()).setFragment(edit_players, "Editar Jugador", "Editar Jugador", args);
            }
        });

        gen_image_player1 = view.findViewById(R.id.gen_image_1);
        text_player1 = view.findViewById(R.id.player_name_1);
        gen_image_player2 = view.findViewById(R.id.gen_image_2);
        text_player2 = view.findViewById(R.id.player_name_2);

        fillData(gen_image_player1, text_player1, gen_image_player2, text_player2);

        return view;
    }

    private void fillData(ImageView gen_image_player1, TextView text_player1, ImageView gen_image_player2, TextView text_player2){
        text_player1.setText(((MainActivity)getActivity()).players.get(0).getName());
        text_player2.setText(((MainActivity)getActivity()).players.get(1).getName());
        gen_image_player1.setImageResource(setGenderImage(((MainActivity)getActivity()).players.get(0).getGender()));
        gen_image_player2.setImageResource(setGenderImage(((MainActivity)getActivity()).players.get(1).getGender()));
    }

    private int setGenderImage(int gender){
        switch (gender){
            case 0:
                return R.drawable.male_icon;
            case 1:
                return R.drawable.femlae_icon;
            default:
                return R.drawable.male_icon;
        }
    }

}