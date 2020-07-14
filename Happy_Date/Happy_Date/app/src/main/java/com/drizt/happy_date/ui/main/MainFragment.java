package com.drizt.happy_date.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.drizt.happy_date.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    Button play;
    Button config;
    Button exit;
    Toast prueba;
    String prueba_string;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        play = view.findViewById(R.id.play);


        play.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                prueba_string = "PLAY";
                prueba = Toast.makeText(getContext(),prueba_string, Toast.LENGTH_SHORT);
                View toast_view = prueba.getView();
                toast_view.setBackgroundColor(Color.GRAY);
                TextView toast_text = toast_view.findViewById(android.R.id.message);
                toast_text.setTextColor(Color.WHITE);
                prueba.show();
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