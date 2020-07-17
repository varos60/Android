package com.drizt.happy_date;

import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.drizt.happy_date.Clases.Categorie;
import com.drizt.happy_date.Clases.Challenge;
import com.drizt.happy_date.Clases.Player;
import com.drizt.happy_date.ui.main.Configuration.ConfigCategorias;
import com.drizt.happy_date.ui.main.Configuration.ConfigChoice;
import com.drizt.happy_date.ui.main.Configuration.ConfigPlayers;
import com.drizt.happy_date.ui.main.Configuration.ConfigRetos;
import com.drizt.happy_date.ui.main.MainFragment;
import com.drizt.happy_date.ui.main.Play.PlaySelectCategories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Player> players;
    public ArrayList<Categorie> categorias;
    public int [] backPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        try {
            getSupportActionBar().setTitle("Happy Date");
        }catch (Exception e) {
            Log.e("Excepcion", String.valueOf(e));
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        players = new ArrayList<>();
        categorias = new ArrayList<>();
        getAllDataJson(getJsonDataFromLocalFile());
    }

    @Override
    public void onBackPressed(){
        backBotton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void setFragment(Fragment fragment, String tag, String title, int [] arg) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (arg != null){
            Bundle bundle = new Bundle();
            bundle.putIntArray("position", arg);
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.container, fragment, tag);
        transaction.addToBackStack(fragment.getTag());
        transaction.commit();
        try {
            getSupportActionBar().setTitle(title);
        }catch (Exception e) {
            Log.e("Excepcion", String.valueOf(e));
        }
    }

    private void backFragment(Fragment fragment, String tag, String title, int [] arg){
        if (tag.equals("Happy Date")){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        getFragmentManager().popBackStack();
        setFragment(fragment, tag, title, arg);
    }

    public void backBotton(){
        String [] fragments = {"Configuracion", "Jugadores", "Categorias", "Editar Jugador", "Selecciona Categorias", "Reto", "Configurar Retos", "Editar Reto"};
        if(checkFragment(fragments[0])){
            backFragment(new MainFragment(), "Happy Date", "Happy Date", null);
        }
        if(checkFragment(fragments[1]) || checkFragment(fragments[2]) ){
            backFragment(new ConfigChoice(), "Configuracion", "Configuracion", null);
        }
        if(checkFragment(fragments[3]) ){
            backFragment(new ConfigPlayers(), "Jugadores", "Jugadores", null);
        }
        if(checkFragment(fragments[4]) ){
            backFragment(new MainFragment(), "Happy Date", "Happy Date", null);
        }
        if(checkFragment(fragments[5]) ){
            backFragment(new PlaySelectCategories(), "Selecciona Categorias", "Selecciona Categorias",null);
        }
        if(checkFragment(fragments[6]) ){
            backFragment(new ConfigCategorias(), "Categorias", "Categorias", null);
        }
        if(checkFragment(fragments[7]) ){
            backFragment(new ConfigRetos(), "Configurar Retos", "Configurar Retos", backPosition);
        }

    }

    private boolean checkFragment(String fragment_name) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        fragment = fragmentManager.findFragmentByTag(fragment_name);
        return fragment != null && fragment.isVisible();
    }

    public String getJsonDataFromLocalFile() {
        String jsonString = null;
        File file = new File(getFilesDir(), "database.json");
        if (!file.exists()){
            FileWriter writer = null;
            try {
                String defaultDtabase = "{\"players\": [ {\"name\": \"Álvaro\", \"gender\": 0, \"id\": 1 }, {\"name\": \"Atenea\", \"gender\": 1, \"id\": 2 } ], \"categories\": [ {\"id\": 1,  \"name\": \"Preliminares\",  \"challenges\": [  {\"id\": 1,  \"name\": \"Besa el cuerpo de {player}\",  \"who\": 2,  \"to_whom\": 2,  \"image\": \"\"}  ] } ] } ";
                writer = new FileWriter(file);
                writer.write(defaultDtabase);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //InputStream inputStream = getAssets().open("database.json");
            InputStream inputStream = new FileInputStream(file);
            int fileSize = inputStream.available();
            byte[] buffer = new byte[fileSize];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }

    public void getAllDataJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray players_array = jsonObject.getJSONArray("players");
            for (int i = 0; i < players_array.length(); i++) {
                JSONObject object = players_array.getJSONObject(i);
                Player player = new Player(object);
                players.add(player);
            }
            JSONArray categories_array = jsonObject.getJSONArray("categories");
            for (int i = 0; i < categories_array.length(); i++) {
                JSONObject object = categories_array.getJSONObject(i);
                Categorie categorie = new Categorie(object);
                categorias.add(categorie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void saveJson(){
        //File file = new File(context.getFilesDir(), filename);
        File JSONfile = new File(getFilesDir().getPath(), "database.json");
        OutputStream out = null;
        try {
            out = new FileOutputStream(JSONfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        writer.setIndent("  ");
        try {
            writer.beginObject();
            writePlayers(writer);
            writeCategories(writer);
            writer.endObject();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePlayers(JsonWriter writer){
        try {
            writer.name("players");
            writer.beginArray();
            for (Player player: players){
                writer.beginObject();
                writer.name("id").value(player.getId());
                writer.name("name").value(player.getName());
                writer.name("gender").value(player.getGender());
                writer.endObject();
            }
            writer.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCategories(JsonWriter writer){
        try {
            writer.name("categories");
            writer.beginArray();
            for (Categorie categorie: categorias){
                writer.beginObject();
                writer.name("id").value(categorie.getId());
                writer.name("name").value(categorie.getName());
                writer.name("challenges");
                writeChallenges(writer, categorie.getChallenges());
                writer.endObject();
            }
            writer.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeChallenges(JsonWriter writer, List<Challenge> challenges_array){
        try {
            writer.beginArray();
            for (Challenge challenge: challenges_array){
                writer.beginObject();
                writer.name("id").value(challenge.getId());
                writer.name("name").value(challenge.getName());
                writer.name("who").value(challenge.getWho());
                writer.name("to_whom").value(challenge.getTo_whom());
                writer.name("image").value(challenge.getImage());
                writer.endObject();
            }
            writer.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}