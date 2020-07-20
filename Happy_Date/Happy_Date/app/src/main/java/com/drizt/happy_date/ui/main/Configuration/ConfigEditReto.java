package com.drizt.happy_date.ui.main.Configuration;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.drizt.happy_date.Clases.Challenge;
import com.drizt.happy_date.MainActivity;
import com.drizt.happy_date.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ConfigEditReto extends Fragment {

    private static final String ARG_PARAM1 = "position";
    public static final int GALLERY_REQUEST_CODE = 105;
    String currentPhotoPath;
    String currentUriString;

    private int [] mParam1;
    EditText name;
    Button add_player_text;
    RadioGroup who;
    RadioGroup to_whom;
    Button add;
    Button no_image;
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
        no_image = view.findViewById(R.id.no_image);

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

        no_image.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
               currentUriString = "";
               image.setImageResource(R.drawable.no_image);
            }
        });

        image.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openGallery(view.getContext());
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
        if (!challenge.getImage().equals("")){
            image.setImageURI(Uri.parse(challenge.getImage()));
        }else{
            image.setImageResource(R.drawable.no_image);
        }
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
        Challenge nueva_categoria = new Challenge(last_id, name.getText().toString(), who_type, to_whom_type, currentUriString);
        ((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().add(nueva_categoria);
        ((MainActivity)getActivity()).saveJson();
    }

    void changeData(){
        ((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().get(mParam1[1]).setName(name.getText().toString());
        ((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().get(mParam1[1]).setWho(who_type);
        ((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().get(mParam1[1]).setTo_whom(to_whom_type);
        ((MainActivity)getActivity()).categorias.get(mParam1[0]).getChallenges().get(mParam1[1]).setImage(currentUriString);
        ((MainActivity)getActivity()).saveJson();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GALLERY_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery((MainActivity)getActivity());
            }else {
                Toast.makeText(((MainActivity)getActivity()), "Permisos Necesarios", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==GALLERY_REQUEST_CODE){
           if (resultCode==Activity.RESULT_OK){
               Uri contentUri = data.getData();
               try {
                   Uri new_uri = createImageFilefromUri(contentUri);
                   currentUriString = new_uri.toString();
                   image.setImageURI(new_uri);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    Uri createImageFilefromUri(Uri contentUri) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = ((MainActivity)getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        //Copia la imagen de la galeria
        InputStream in =  ((MainActivity)getActivity()).getContentResolver().openInputStream(contentUri);
        OutputStream out = new FileOutputStream(image);
        byte[] buf = new byte[1024];
        int len;
        while((len=in.read(buf))>0){
            out.write(buf,0,len);
        }
        out.close();
        in.close();
        return Uri.fromFile(image);
    }

    void openGallery(Context context){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQUEST_CODE);
    }
}