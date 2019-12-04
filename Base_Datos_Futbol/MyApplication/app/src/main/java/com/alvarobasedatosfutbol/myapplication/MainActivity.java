package com.alvarobasedatosfutbol.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

import com.alvarobasedatosfutbol.myapplication.Alta.Equipo.Alta_Equipo;
import com.alvarobasedatosfutbol.myapplication.Alta.Equipo.Modificar_Equipo;
import com.alvarobasedatosfutbol.myapplication.Alta.Equipo.Registrar_Equipo;
import com.alvarobasedatosfutbol.myapplication.Alta.Jugador.Alta_Jugador;
import com.alvarobasedatosfutbol.myapplication.Alta.Jugador.Modificar_Jugador_Alta;
import com.alvarobasedatosfutbol.myapplication.Alta.Jugador.Registrar_Jugador;
import com.alvarobasedatosfutbol.myapplication.Base_Datos.Base_Datos;
import com.alvarobasedatosfutbol.myapplication.Borrado.Borra;
import com.alvarobasedatosfutbol.myapplication.Consultas.Clasificacion.Consulta_Clasificacion;
import com.alvarobasedatosfutbol.myapplication.Consultas.Partidos.Consulta_Partidos;
import com.alvarobasedatosfutbol.myapplication.Consultas.Plantilla.Consulta_Plantilla;
import com.alvarobasedatosfutbol.myapplication.Modificar.Clasificacion.Modificar_Clasificacion;
import com.alvarobasedatosfutbol.myapplication.Modificar.Clasificacion.Modificar_Clasificacion_Borrar_Clasificacion;
import com.alvarobasedatosfutbol.myapplication.Modificar.Clasificacion.Modificar_Clasificacion_ClasificacionEquipo_Registrar;
import com.alvarobasedatosfutbol.myapplication.Modificar.Clasificacion.Modificar_Clasificacion_Modificar;
import com.alvarobasedatosfutbol.myapplication.Modificar.Clasificacion.Modificar_Clasificacion_Registrar;
import com.alvarobasedatosfutbol.myapplication.Modificar.Jugador.Modificar_Jugador;
import com.alvarobasedatosfutbol.myapplication.Modificar.Partido.Modificar_Partidos;
import com.alvarobasedatosfutbol.myapplication.Otros.BD_Buckup;

//Se crea la clase y le implementamos los fragments
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Alta_Equipo.OnFragmentInteractionListener, Borra.OnFragmentInteractionListener, Alta_Jugador.OnFragmentInteractionListener,
        Consulta_Plantilla.OnFragmentInteractionListener, Consulta_Clasificacion.OnFragmentInteractionListener, Consulta_Partidos.OnFragmentInteractionListener,
        Modificar_Partidos.OnFragmentInteractionListener, Modificar_Jugador.OnFragmentInteractionListener, Modificar_Clasificacion.OnFragmentInteractionListener,
        Fragment_Principal.OnFragmentInteractionListener, Registrar_Equipo.OnFragmentInteractionListener, Modificar_Equipo.OnFragmentInteractionListener, Registrar_Jugador.OnFragmentInteractionListener,
        Modificar_Jugador_Alta.OnFragmentInteractionListener, Modificar_Clasificacion_Registrar.OnFragmentInteractionListener, Modificar_Clasificacion_Modificar.OnFragmentInteractionListener,
        Modificar_Clasificacion_Borrar_Clasificacion.OnFragmentInteractionListener, Modificar_Clasificacion_ClasificacionEquipo_Registrar.OnFragmentInteractionListener {
    //Declaro la clase base de datos
    SQLiteDatabase bd;
    ActionBarDrawerToggle toggle;
    private boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Creacion de la base de datos en el main activity
            Base_Datos base_data = new Base_Datos(this, Base_Datos.bd_name, null, Base_Datos.bd_version);
            bd = base_data.getWritableDatabase();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            setFragment(new Fragment_Principal(), "Fragment_Principal");
        }else{}
    }
    private boolean exit = false;

    @Override
    public void onBackPressed() {
        if(backBottonOnNavigationDrawer()){
            return;
        }
        if(exit){
            bd.close();
            getPermision();
            BD_Buckup.BD_Backup();
            finish();
        }
        else {
            if (backBottonOnFragmentPrincipal()){
                return;
            }
            if(backBottonModificarClasificacion()){
                return;
            }
            if(backBottonOnGenericFragment()){
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Boton del menu que cierra la bd hace copia y cierra la aplicacion
        if (id == R.id.exit) {
            bd.close();
            getPermision();
            BD_Buckup.BD_Backup();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Menu del navigation y cargamos los fragments
        int id = item.getItemId();
        boolean FragmentTransition = false;
        Fragment screen = null;
        exit = false;
        String tag = "";
        if (id == R.id.nav_equipo) {
            //Cargamos el de Dar de Alta Equipos
            bd.close();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
            tabLayout.setVisibility(View.GONE);
            screen = new Alta_Equipo();
            FragmentTransition = true;

        } else if (id == R.id.nav_borrar) {
            //Cargamos el Fragment para Borrar
            bd.close();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
            tabLayout.setVisibility(View.GONE);
            screen = new Borra();
            FragmentTransition = true;

        } else if (id == R.id.nav_jugador_alta) {
            //Cargamos el Fragment para dar de alta jugadores
            bd.close();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
            tabLayout.setVisibility(View.GONE);
            screen = new Alta_Jugador();
            FragmentTransition = true;

        } else if (id == R.id.nav_plantilla) {
            bd.close();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
            tabLayout.setVisibility(View.GONE);
            screen = new Consulta_Plantilla();
            FragmentTransition = true;

        } else if (id == R.id.nav_clasificacion_consulta) {
            bd.close();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
            tabLayout.setVisibility(View.GONE);
            screen = new Consulta_Clasificacion();
            FragmentTransition = true;

        } else if (id == R.id.nav_partidos) {
            bd.close();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
            tabLayout.setVisibility(View.GONE);
            screen = new Consulta_Partidos();
            FragmentTransition = true;

        } else if (id == R.id.nav_partido) {
            bd.close();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
            tabLayout.setVisibility(View.GONE);
            screen = new Modificar_Partidos();
            FragmentTransition = true;

        } else if (id == R.id.nav_jugador) {
            bd.close();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
            tabLayout.setVisibility(View.GONE);
            screen = new Modificar_Jugador();
            FragmentTransition = true;

        } else if (id == R.id.nav_clasificacion) {
            bd.close();
            TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
            tabLayout.setVisibility(View.GONE);
            screen = new Modificar_Clasificacion();
            FragmentTransition = true;

        }

        if (FragmentTransition){

            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, screen)
                    .commit();
            item.setChecked(true);
            try {
                getSupportActionBar().setTitle(item.getTitle());
            }catch (Exception e) {
                Exception em = e;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void setFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment, tag)
                .commit();
    }
    public void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .remove(fragment)
                .commit();
    }
    private int PICKER = 1;
    public void FilePicker(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        try {
            startActivityForResult(Intent.createChooser(intent, "Elige Fichero:"), PICKER);
        }catch (android.content.ActivityNotFoundException e){
            Toast.makeText(this, "Instala un Explorador de Archivos",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String data_path = data.getData().getPath();
                    String data_scheme = data.getScheme();
                    Log.i("Ruta:", data_path);
                    Log.i("Esquema:", data_scheme);
                    BD_Buckup.Recover_BBDD(data_path, data_scheme, this);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void getPermision(){

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }

        }
    }

    public void setToolbar(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().hide();
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            getSupportActionBar().show();
        } else {
            drawer.setDrawerListener(null);
        }
    }

    private boolean backBottonOnNavigationDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    private boolean backBottonOnFragmentPrincipal(){
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag("Fragment_Principal");
            if (fragment != null && fragment.isVisible()) {
                this.exit = true;
                Toast.makeText(this, "Pulse otra vez para Salir", Toast.LENGTH_SHORT).show();
                return true;
            }
            else {
                return false;
            }
    }

    private boolean backBottonOnGenericFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        setFragment(new Fragment_Principal(), "Fragment_Principal");
        getSupportActionBar().setTitle("Base de datos Futbol");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.appbartabs);
        tabLayout.setVisibility(View.GONE);
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        int size = mNavigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            SubMenu submenu =  mNavigationView.getMenu().getItem(i).getSubMenu();
            for (int j = 0; j < submenu.size(); j++) {
                submenu.getItem(j).setChecked(false);
            }
        }
        return  true;
    }
    private boolean backBottonModificarClasificacion(){
        String [] fragments = {"Modificar_Clasificacion_Borrar_Clasificacion", "Modificar_Clasificacion_ClasificacionEquipo_Registrar"};
        if (checkFragmentList(fragments)) {
            toggleBottonNavigitionDrawer(true);
            getFragmentManager().popBackStack();
            setFragment(new Modificar_Clasificacion(), "Modificar_Clasificacion");
            return true;
        }else {return false;}
    }
    public void toggleBottonNavigitionDrawer(boolean enable) {
        if (enable) {
            toggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (!mToolBarNavigationListenerIsRegistered) {
                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Doesn't have to be onBackPressed
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            } else {
                // Remove back button
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                // Show hamburger
                toggle.setDrawerIndicatorEnabled(true);
                // Remove the/any drawer toggle listener
                toggle.setToolbarNavigationClickListener(null);
                mToolBarNavigationListenerIsRegistered = false;
            }
        }
    }
    private boolean checkFragmentList(String [] fragment_list) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        boolean found = false;
        for (int i = 0; i < fragment_list.length; i++) {
            fragment = fragmentManager.findFragmentByTag(fragment_list[i]);
            if (fragment != null && fragment.isVisible()) {
                found = true;
                break;
            }
        }
        if(found){
            return true;
        }else{return false;}
    }
}
