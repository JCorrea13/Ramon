package com.ramon.umg.ramon;

import android.widget.Toast;

/**
 * Created by JCORREA on 03/05/2015.
 */
public class Util {

    /**
     * makeToast: Crea un toast con el texto que se le envíe.
     * @param texto : Es el texto a mostrar.
     */
    public static synchronized void makeToast(String texto){
        Toast toast = Toast.makeText(FlightControls.fly.getApplicationContext(), texto, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static synchronized void makeToast(String texto, int duration){
        Toast toast = Toast.makeText(FlightControls.fly.getApplicationContext(), texto, duration);
        toast.show();
    }
}
