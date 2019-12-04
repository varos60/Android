package com.alvarobasedatosfutbol.myapplication.Otros;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.alvarobasedatosfutbol.myapplication.Base_Datos.Base_Datos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Álvaro on 04/10/2016.
 */
public class BD_Buckup {
    //Metodo para hacer buckup de la base de datos
    public static void BD_Backup() {
        try {
            String in_dir = "Futbol_Database";
            //File dir = new File(context.getFilesDir(), in_dir);
            File dir = new File(Environment.getExternalStorageDirectory(), in_dir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            String packageName  = "com.alvarobasedatosfutbol.myapplication";
            String sourceDBName = Base_Datos.bd_name;
            String targetDBName = Base_Datos.bd_name.substring(0, Base_Datos.bd_name.length()-3);
            if (sd.canWrite()) {
                Date now = new Date();
                String currentDBPath = "data/" + packageName + "/databases/" + sourceDBName;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String backupDBPath = in_dir + "/" + targetDBName + "_" + dateFormat.format(now) + ".db";

                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                Log.i("Backup","OrigenDB=" + currentDB.getAbsolutePath());
                Log.i("Backup","BackupDB=" + backupDB.getAbsolutePath());

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
            Log.i("Backup Error: ", e.toString());
        }
      }
        public static void Recover_BBDD(String path_bbdd, String scheme_bbdd, Context context){
            int ext_pos = path_bbdd.lastIndexOf(".");
            String file_extension = path_bbdd.substring(ext_pos);
            Log.i("Extension", file_extension);
            if (file_extension.equals(".db")){
                try {
                    String file_path;
                    if (scheme_bbdd.equals("content")){
                        file_path = path_bbdd.substring(path_bbdd.lastIndexOf(":")+1);
                    }else{
                        file_path = path_bbdd.replace(Environment.getExternalStorageDirectory().getAbsolutePath(),"");
                        file_path = file_path.substring(1);
                    }
                    File sd = Environment.getExternalStorageDirectory();
                    File data = Environment.getDataDirectory();
                    String packageName  = "com.alvarobasedatosfutbol.myapplication";
                    String sourceDBName = Base_Datos.bd_name;
                    if (sd.canWrite()) {
                        String currentDBPath = "data/" + packageName + "/databases/" + sourceDBName;
                        String recoverDBPath = file_path;

                        File currentDB = new File(data, currentDBPath);
                        File recoverDB = new File(sd, recoverDBPath);

                        Log.i("Backup","OrigenDB=" + recoverDB.getAbsolutePath());
                        Log.i("Backup","DestinoDB=" + currentDB.getAbsolutePath());

                        FileChannel src = new FileInputStream(recoverDB).getChannel();
                        FileChannel dst = new FileOutputStream(currentDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                        Toast.makeText(context,"BBDD Recuperada",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.i("Error: ", e.toString());
                    Toast.makeText(context,"Error al recuperar",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(context,"Error: fichero no es '.db'",Toast.LENGTH_SHORT).show();
            }
        }
   }
