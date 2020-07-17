package com.drizt.happy_date.ui.main.Play;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaySelectCategories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaySelectCategories extends Fragment {

    Button play_game;
    public static PlaySelectCategories newInstance(String param1, String param2) {
        PlaySelectCategories fragment = new PlaySelectCategories();
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
        View view = inflater.inflate(R.layout.play_select_categories, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        play_game = view.findViewById(R.id.play_game);

        play_game.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                Fragment play_frament = new PlayReto();
                ((MainActivity)getActivity()).setFragment(play_frament, "Reto", "Reto", null);
            }
        });

        return view;
    }
}