package com.drizt.happy_date.ui.main.Play;

import android.animation.TimeAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaySelectCategories extends Fragment {

    ArrayList<Integer> positions;
    Button play_game;
    RecyclerView categorias_rv;
    PlaySelectCategoriasAdapter adapter;

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
        categorias_rv = view.findViewById(R.id.all_categories_rv);
        categorias_rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        categorias_rv.setHasFixedSize(true);
        positions = new ArrayList<>();

        adapter = new PlaySelectCategoriasAdapter(((MainActivity)getActivity()).categorias, new PlaySelectCategoriasAdapter.onCheckChange() {
            @Override
            public void onCheckedChange(boolean check) {}
        }, positions);

        categorias_rv.setAdapter(adapter);
        categorias_rv.setItemViewCacheSize(((MainActivity)getActivity()).categorias.size());

        play_game.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                if (positions.size()>0) {
                    Fragment play_frament = new PlayReto();
                    int [] params = new int[positions.size()];
                    for (int i=0; i<positions.size(); i++){
                        params[i] = positions.get(i);
                    }
                    ((MainActivity) getActivity()).setFragment(play_frament, "Reto", "Reto", params);
                }else {
                    Toast.makeText(view.getContext(), "Categorias Sin Retos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}