package com.drizt.happy_date.ui.main.Configuration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigEditPlayer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigEditPlayer extends Fragment {

    private static final String ARG_PARAM1 = "position";
    private int [] mParam1;

    EditText name;
    RadioGroup gender;
    Button save;
    int genero;

    public ConfigEditPlayer() {
    }

    public static ConfigEditPlayer newInstance(int [] param1) {
        ConfigEditPlayer fragment = new ConfigEditPlayer();
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
        View view = inflater.inflate(R.layout.config_edit_player, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = view.findViewById(R.id.player_name);
        gender = view.findViewById(R.id.radio_group_genero);
        save = view.findViewById(R.id.save_player);

        fillData(name, gender);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = gender.findViewById(checkedId);
                int index = gender.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        genero = 0;
                        break;
                    case 1:
                        genero = 1;
                        break;
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act_player(name, genero);
                ((MainActivity)getActivity()).saveJson();
                Toast.makeText(getContext(), "Guardado", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).backBotton();
            }
        });

        return view;
    }

    private void fillData(EditText name, RadioGroup gender){
        name.setText(((MainActivity)getActivity()).players.get(mParam1[0]).getName());
        View radioButton = gender.getChildAt(((MainActivity)getActivity()).players.get(mParam1[0]).getGender());
        gender.check(radioButton.getId());
    }

    private void act_player(EditText name, int genero){
        ((MainActivity)getActivity()).players.get(mParam1[0]).setName(name.getText().toString());
        ((MainActivity)getActivity()).players.get(mParam1[0]).setGender(genero);

    }
}