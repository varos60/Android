package com.drizt.happy_date.ui.main.Configuration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.drizt.happy_date.Clases.Challenge;
import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;

import androidx.fragment.app.Fragment;


public class ConfigEditReto extends Fragment {

    private static final String ARG_PARAM1 = "position";

    private int [] mParam1;
    EditText name;
    Button add_player_text;
    RadioGroup who;
    RadioGroup to_whom;
    Button add;
    ImageView image;
    int who_type = 2;
    int to_whom_type = 2;

    public ConfigEditReto() {
    }

    public static ConfigEditReto newInstance(int [] param1) {
        ConfigEditReto fragment = new ConfigEditReto();
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

        View view = inflater.inflate(R.layout.config_edit_reto, container, false);
        int [] args = new int []{mParam1[0]};
        ((MainActivity)getActivity()).backPosition = args;
        name = view.findViewById(R.id.reto_name);
        add_player_text = view.findViewById(R.id.add_tag_player);
        who = view.findViewById(R.id.radio_group_who);
        to_whom = view.findViewById(R.id.radio_group_whom);
        add = view.findViewById(R.id.save_reto);
        image = view.findViewById(R.id.image_reto_config);

        if (mParam1.length == 2){
            fillData();
        }

        who.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = who.findViewById(checkedId);
                int index = who.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        who_type = 0;
                        rButtonBehaviour(1, to_whom);
                        break;
                    case 1:
                        who_type = 1;
                        rButtonBehaviour(0, to_whom);
                        break;
                    case 2:
                        who_type = 2;
                        rButtonBehaviour(2, to_whom);
                        break;
                }
            }
        });

        for(int i = 0; i < to_whom.getChildCount(); i++){
            ((RadioButton)to_whom.getChildAt(i)).setClickable(false);
        }

        to_whom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = to_whom.findViewById(checkedId);
                int index = to_whom.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        to_whom_type = 0;
                        break;
                    case 1:
                        to_whom_type = 1;
                        break;
                    case 2:
                        to_whom_type = 2;
                        break;
                }
            }
        });

        add.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                if(!name.getText().toString().equals("")) {
                    if (mParam1.length == 2) {
                        changeData();
                        Toast.makeText(getContext(), "Guardado", Toast.LENGTH_SHORT).show();
                        ((MainActivity) getActivity()).backBotton();
                    } else {
                        newData();
                        Toast.makeText(getContext(), "Guardado", Toast.LENGTH_SHORT).show();
                        ((MainActivity) getActivity()).backBotton();
                    }
                }
            }
        });

        add_player_text.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                name.setText(name.getText().toString() + "{player}");
            }
        });

        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_delete, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            deleteReto();
        }
        return super.onOptionsItemSelected(item);
    }

    void fillData(){
        Challenge challenge = ((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().get(mParam1[1]);
        name.setText(challenge.getName());
        View radioButton = who.getChildAt(challenge.getWho());
        who_type = challenge.getWho();
        who.check(radioButton.getId());
        View radioButton2 = to_whom.getChildAt(challenge.getTo_whom());
        to_whom_type = challenge.getTo_whom();
        to_whom.check(radioButton2.getId());

    }

    void deleteReto(){
        if (mParam1.length == 2){
            ((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().remove(mParam1[1]);
            ((MainActivity)getActivity()).saveJson();
            Toast.makeText(getContext(), "Borrado", Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).backBotton();
        }
    }

    void rButtonBehaviour(int clickableId, RadioGroup to_whom){
        for(int i = 0; i < to_whom.getChildCount(); i++){
            if(i == clickableId){
                ((RadioButton)to_whom.getChildAt(i)).setChecked(true);
            }
            else {
            ((RadioButton)to_whom.getChildAt(i)).setChecked(false);
            }
        }
    }

    void newData(){
        int last_id;
        if (((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().size() > 0) {
            last_id = ((MainActivity) getActivity()).categorias.get(mParam1[0]).getChallenges().get(((MainActivity) getActivity()).categorias.get(mParam1[0]).getChallenges().size() - 1).getId() + 1;
        }else{
            last_id = 0;
        }
        Challenge nueva_categoria = new Challenge(last_id, name.getText().toString(), who_type, to_whom_type, "");
        ((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().add(nueva_categoria);
        ((MainActivity)getActivity()).saveJson();
    }

    void changeData(){
        ((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().get(mParam1[1]).setName(name.getText().toString());
        ((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().get(mParam1[1]).setWho(who_type);
        ((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().get(mParam1[1]).setTo_whom(to_whom_type);
        ((MainActivity)getActivity()).saveJson();
    }
}