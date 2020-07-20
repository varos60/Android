package com.drizt.happy_date.ui.main.Play;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.drizt.happy_date.Clases.Categorie;
import com.drizt.happy_date.Clases.Challenge;
import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class PlayReto extends Fragment {

    private static final String ARG_PARAM1 = "position";
    private int [] mParam1;
    ArrayList<Challenge> doneRetos;
    ArrayList<Challenge> currentRetos;
    ArrayList<int []> retoCategoria;
    TextView categoria;
    TextView player;
    TextView reto;
    ImageView image;
    Button next;
    int randomPosition;

    public PlayReto() {
        // Required empty public constructor
    }

    public static PlayReto newInstance(int[] param1) {
        PlayReto fragment = new PlayReto();
        Bundle args = new Bundle();
        args.putIntArray(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getIntArray(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_reto, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        categoria = view.findViewById(R.id.categoria_text);
        player = view.findViewById(R.id.player_text);
        reto = view.findViewById(R.id.reto_text);
        image = view.findViewById(R.id.image_reto);
        next = view.findViewById(R.id.next_reto);

        currentRetos = new ArrayList<>();
        doneRetos = new ArrayList<>();
        retoCategoria = new ArrayList<>();
        for (int i = 0;i < mParam1.length; i++){
            for (Challenge challenge: ((MainActivity)getActivity()).categorias.get(mParam1[i]).getChallenges()) {
                int [] reto_categoria = new int[2];
                reto_categoria[0] = i;
                reto_categoria[1] = challenge.getId();
                retoCategoria.add(reto_categoria);
                currentRetos.add(challenge);
            }
        }
        randomPosition = randomReto();
        fillData(randomPosition);

        next.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                if (currentRetos.size()==0) {
                    for (Challenge challenge: doneRetos){
                        currentRetos.add(challenge);
                    }
                    doneRetos.clear();
                }
                randomPosition = randomReto();
                fillData(randomPosition);
            }});

        return view;
    }

    String[] playerLogic(Challenge challenge){
        String [] players = new String[2];
        switch (challenge.getWho()) {
            case 0 :
                players[0] = ((MainActivity) getActivity()).players.get(0).getName();
                players[1] = ((MainActivity) getActivity()).players.get(1).getName();
                break;
            case 1:
                players[0] = ((MainActivity) getActivity()).players.get(1).getName();
                players[1] = ((MainActivity) getActivity()).players.get(0).getName();
                break;
            case 2:
                Random rd = new Random();
                int randomPlayer = rd.nextInt(2);
                players[0] = ((MainActivity) getActivity()).players.get(randomPlayer).getName();
                for (int i = 0; i < ((MainActivity) getActivity()).players.size(); i++) {
                    if (i != randomPlayer) {
                        players[1] = ((MainActivity) getActivity()).players.get(i).getName();
                    }
                }
                break;
            default:
                players[0] = ((MainActivity) getActivity()).players.get(0).getName();
                players[1] = ((MainActivity) getActivity()).players.get(1).getName();
                break;
        }
        return players;
    }

    int randomReto(){
        Random rd = new Random();
        int max = currentRetos.size();
        int random_num = rd.nextInt(max);
        return random_num;

    }

    void fillData(int randomPosition){
        Challenge challenge = currentRetos.get(randomPosition);
        doneRetos.add(currentRetos.get(randomPosition));
        currentRetos.remove(currentRetos.get(randomPosition));

        for (int i = 0;i<retoCategoria.size();i++){
            if (challenge.getId() == retoCategoria.get(i)[1]){
                categoria.setText(((MainActivity)getActivity()).categorias.get(retoCategoria.get(i)[0]).getName());
            }
        }
        String [] players = playerLogic(challenge);
        player.setText(players[0]);
        reto.setText(challenge.getName().replace("{player}", players[1]));
        if (!challenge.getImage().equals("")){
            image.setImageURI(Uri.parse(challenge.getImage()));
        }else{
            image.setImageResource(R.drawable.no_image);
        }
    }
}