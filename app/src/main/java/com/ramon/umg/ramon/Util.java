package com.ramon.umg.ramon;

import android.widget.Toast;

public class Util {
    /**
     * makeToast: Crea un toast con el texto que se le envíe.
     * @param texto : Es el texto a mostrar.
     */
    public static void makeToast(String texto){
        Toast toast = Toast.makeText(FlightControls.activity.getApplicationContext(), texto, Toast.LENGTH_SHORT);
        toast.show();
    }
}
