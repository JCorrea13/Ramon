package com.ramon.umg.ramon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

import static com.ramon.umg.ramon.R.color.verde;

/**
 *Autor Luis Alfonso Chávez Abbadie
 * Proyecto Ramón
 * Integrantes : Luis Alfonso, José Manuel Correa y Fernanda Islas
 * POR FAVOR, SIEMPRE HAY QUE LEER LOS COMENTARIOS Y DOCUMENTACION ANTES DE MODIFICAR CODIGO, Y CUANDO LO HAGAS
 * DEJA UN COMENTARIO INDICANDO LO QUE HACE
 *
 * FlighControls es la Activity main del proyecto
 */
public class FlightControls extends FragmentActivity{

    public static Activity activity;
    private ViewPager viewpager;
    Vibrator vibrar;

    private static TextView tvconexion;
    public static ImageButton btnPower;
    public static volatile ImageButton btnActualizar;
    private Button botonP1; //Botones del menu
    private Button botonP2;
    private Button botonP3;

    public boolean aterrizaje = true;
    public static volatile boolean banderaEstadoConexion = false; //Hay que llamar al metodo actualizaEstadoConexion() para eejcutar cambio
    public static boolean motoresEncendidos = false;

    public static volatile boolean correrHiloConexion = false;

    BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                Util.makeToast("Antena conectada, reiniciar aplicación");
                new Handler().postDelayed(new Runnable() {
                    /*
                     * Mostrar la pantalla de carga durante un tiempo determinado y luego lanzar la activity FlightControls.
                     */
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2500);
            } else if(UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                    Util.makeToast("Antena desconectada");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_controls);
        activity = this;
        vibrar = (Vibrator)this.getApplicationContext().getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        tvconexion = (TextView)findViewById(R.id.tvEstadoConexion);
        viewpager = (ViewPager)findViewById(R.id.pager);
        PagerAdapter padapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(padapter);
        btnActualizar = (ImageButton)findViewById(R.id.ibReload);
        btnPower = (ImageButton)findViewById(R.id.ibPower);
        botonP1 = (Button)findViewById(R.id.bControles);
        botonP2 = (Button) findViewById(R.id.bSensores);
        botonP3 = (Button)findViewById(R.id.bMapa);

        IntentFilter filter = new IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(mUsbReceiver, filter);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrar.vibrate(200);
                Torre.inicioConexion();
                correrHiloConexion = true;
                new Thread(new Runnable(){
                    Object lock = new Object();
                    @Override
                    public void run() {
                        while(correrHiloConexion){
                            try {
                                Thread.sleep(1400);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    actualizaEstadoConexion();
                                }
                            });
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (banderaEstadoConexion) {
                                banderaEstadoConexion = false;
                                continue;
                            }
                            correrHiloConexion = false;
                        }
                    }
                }).start();
            }
        });
        //Cuando un boton es presionado, y se arrastra hacia la siguiente pagina (se cambia de fragmen el viewpager)
        //los botones deben reiniciarse (volver a su imagen original) y enviar el codigo al arduino de que ya no se
        //debe seguir moviendo.
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) { }

            @Override
            public void onPageSelected(int i) {
                sombrearMenu(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (viewpager.getCurrentItem() == 0)
                    Fragment1.inicializarBotones();
            }
        });
        try {
            Conexion.setConexion(this);
        }catch (IOException e) {
            e.printStackTrace();
            Util.makeToast("Error iniciando conexión");
        }
    }

    @Override
    protected void onDestroy(){
        Conexion.cerrarPuerto();
        unregisterReceiver(mUsbReceiver);
        super.onDestroy();
    }

    /**
     * actualizarConexion: Actualiza el letrero de conexión, y se encarga de todos los procesos que se alteran cuando
     * se pierde o se recupera la conexión.
     */
    public void actualizaEstadoConexion(){
        //Si la bandera de conexion es 1 y el texto de conexion no es nulo
        if (banderaEstadoConexion && (tvconexion != null)) {
            tvconexion.setText(R.string.EstadoConexion1);
            tvconexion.setTextColor(getResources().getColor(R.color.verde));
            btnPower.setVisibility(View.VISIBLE);
            btnActualizar.setVisibility(View.INVISIBLE);
        }
        //La bandera es cero y si no es nulo el textview
        else if (tvconexion != null) {
            tvconexion.setText(R.string.EstadoConexion0);
            tvconexion.setTextColor(getResources().getColor(R.color.rojo));
            if(btnPower != null)
                btnPower.setVisibility(View.INVISIBLE);
            if(btnActualizar != null)
            btnActualizar.setVisibility(View.VISIBLE);
            Fragment2.avisoSensoresDesactualizados();
        }
    }

    /**
     * clickAterrizaje: Envía un dato al arduino que le indica que guarde o desplegue el patín de aterrizaje.
     * @param v
     */
    public void clickAterrizaje(View v){
        if (!banderaEstadoConexion)
            return;
        vibrar.vibrate(200);
        if (aterrizaje) {
            Torre.guardarTrenAterrizaje();
            Util.makeToast("Guardando Patín de Aterrizaje");
            aterrizaje = false;
        } else {
            Torre.despliegaTrenAterrizaje();
            Util.makeToast("Desplegando Patín de Aterrizaje");
            aterrizaje = true;
        }
    }

    /**
     * clickPower: Lanza un dialogo de confirmación cuando el usuario pulse el botón de btnpower.
     * @param v
     */
    public void clickPower(View v) {
        vibrar.vibrate(200);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("¿Seguir@?");
        builder.setMessage("Una posición de despeje o aterrizaje comprometida, podría afectar la integridad del drone.");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Torre.switchMotores();
                if (motoresEncendidos)
                    motoresEncendidos = false;
                else
                    motoresEncendidos = true;
                dialog.dismiss();
            }

        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void botonControles(View view){
        viewpager.setCurrentItem(0, true);
        sombrearMenu(0);
    }

    public void botonSensores(View view){
        viewpager.setCurrentItem(1, true);
        sombrearMenu(1);
    }

    public void botonMapeo(View view){
        viewpager.setCurrentItem(2, true);
        sombrearMenu(2);
    }

    /**
     * sombrearMenu: Método que se encarga de sombrear la pestaña del menu en el que se encuentra el usuario.
     * @param pagina: Numero de pagina en el que se encuentra.
     */
    private void sombrearMenu(int pagina){
        switch(pagina){
            case 0:
                botonP1.setBackgroundColor(getResources().getColor(R.color.grisMenu));
                botonP2.setBackgroundColor(getResources().getColor(R.color.blanco));
                botonP3.setBackgroundColor(getResources().getColor(R.color.blanco));
                break;
            case 1:
                botonP1.setBackgroundColor(getResources().getColor(R.color.blanco));
                botonP2.setBackgroundColor(getResources().getColor(R.color.grisMenu));
                botonP3.setBackgroundColor(getResources().getColor(R.color.blanco));
                break;
            case 2:
                botonP1.setBackgroundColor(getResources().getColor(R.color.blanco));
                botonP2.setBackgroundColor(getResources().getColor(R.color.blanco));
                botonP3.setBackgroundColor(getResources().getColor(R.color.grisMenu));
                break;
        }
    }
}