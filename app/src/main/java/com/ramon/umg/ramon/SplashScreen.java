package com.ramon.umg.ramon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by LUIS ALFONSO on 31/05/2015.
 */
public class SplashScreen extends Activity {

    /**
     * TIEMPO_SPLASH_SCREEN: Variable constante que determina el tiempo que se mostrara la pantalla de carga.
     */
    private final static int TIEMPO_SPLASH_SCREEN = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Mostrar la pantalla de carga durante un tiempo determinado y luego lanzar la activity FlightControls.
             */
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, FlightControls.class);
                startActivity(i);
                finish();
            }
        }, TIEMPO_SPLASH_SCREEN);
    }

}
