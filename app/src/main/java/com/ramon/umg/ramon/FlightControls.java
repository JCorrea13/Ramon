package com.ramon.umg.ramon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
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

/**
 *Autor Luis Alfonso Chávez Abbadie
 * Proyecto Ramón
 * Integrantes : Luis Alfonso, José Manuel Correa y Fernanda Islas
 * POR FAVOR, SIEMPRE HAY QUE LEER LOS COMENTARIOS Y DOCUMENTACION ANTES DE MODIFICAR CODIGO, Y CUANDO LO HAGAS
 * DEJA UN COMENTARIO INDICANDO LO QUE HACE
 *
 * OpenSource
 * FlighControls es la Activity main del proyecto
 */
public class FlightControls extends FragmentActivity{  //Activity principal
    /**
     * viewpager: Encargado de la vista de los 3 fragments.
     */
    private ViewPager viewpager;
    /**
     * aterrizaje: Bandera que indica el estado del mecanismo de aterrizaje. 0 = mecanismo guardado, 1 = mecanismo expuesto
     */
    public boolean aterrizaje = false;
    /**
     * tvconexion: TextView que se utilizará para acceder al letrero de conexión que forma parte de esta activity.
     */
    private static volatile TextView tvconexion;
    /**
     * vib: Instancia de la clase Vibrator, se utiliza para que el telefono vibre cuando se pulse algún botón.
     */
    Vibrator vib;

    public static volatile boolean banderaEstadoConexion = false; //Siempre que se modifique esta bandera, hay que llamar al metodo actualizaEstadoConexion()
    public static int contadorErrorPruebaConxion = 0;
    public static ImageButton btnPower;
    public static volatile ImageButton btnActualizar;
    public static FlightControls fly;

    private Button botonP1;
    private Button botonP2;
    private Button botonP3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_controls);

        vib = (Vibrator)this.getApplicationContext().getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        tvconexion = (TextView)findViewById(R.id.tvEstadoConexion);
        viewpager = (ViewPager)findViewById(R.id.pager);
        PagerAdapter padapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(padapter);
        btnActualizar = (ImageButton)findViewById(R.id.ibReload);
        btnPower = (ImageButton)findViewById(R.id.ibPower);
        botonP1 = (Button)findViewById(R.id.bControles);
        botonP2 = (Button)findViewById(R.id.bSensores);
        botonP3 = (Button)findViewById(R.id.bMapa);
        sombrearMenu(0);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(200);
                try {
                    if (Torre.inicioConexion()) {
                        //if(hiloPruebaConexion.isInterrupted())
                        //hiloPruebaConexion.run();
                        Util.makeToast("Conexion establecida");
                    } else {
                        //if(!hiloPruebaConexion.isInterrupted())
                        //hiloPruebaConexion.resume();
                        Util.makeToast("No se pudo conectar con Ramon");
                    }
                } catch (Exception e) {
                    //if(!hiloPruebaConexion.isInterrupted())
                    //hiloPruebaConexion.resume();
                    Util.makeToast("Error al tratar de establecer la comunicacion");
                    e.printStackTrace();
                }
            }
        });

        try {
            fly = this;
            //inicializamos conexion (Puerto Serial)
            Conexion.setConexion(this, 57600);
            Conexion.setTiempoCompruebaConexion(2000);
            Conexion.setLimiteReconexionAutomatica(3);
            Util.makeToast("Inicio");
        }catch (IOException e) {
            e.printStackTrace();
        }
        //Cuando un boton es presionado, y se arrastra hacia la siguiente pagina (se cambia de fragmen el viewpager)
        //los botones deben reiniciarse (volver a su imagen original) y enviar el codigo al arduino de que ya no se
        //debe seguir moviendo.
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                sombrearMenu(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (viewpager.getCurrentItem() == 0)
                    Fragment1.inicializarBotones(); // ESTE METODO Y ACTUALIZAR PARA QUE LE MANDE AL ARDUINO LA VARIABLE QUE
            }                                   //INDICA QUE YA DEJO DE SER PRESIONADO EL BOTON
        });
    }

    @Override
    protected void onDestroy(){
        Conexion.cerrarPuerto();
        super.onDestroy();
    }

    /**
     * actualizarConexion: Actualiza el letrero de conexión, y se encarga de todos los procesos que se alteran cuando
     * se pierde o se recupera la conexión.
     */
    public static synchronized void actualizaEstadoConexion(){
        //Si la bandera de conexion es 1 y el texto de conexion no es nulo
        if (banderaEstadoConexion && (tvconexion != null)) {
            tvconexion.setText(new ContextThemeWrapper().getResources().getString(R.string.EstadoConexion1));
            tvconexion.setTextColor(new ContextThemeWrapper().getResources().getColor(R.color.verde));

            //COMENTADO PARA VERSION ESTABLE
            //btnPower.setVisibility(View.VISIBLE);
            //btnActualizar.setVisibility(View.INVISIBLE);

            Fragment2.actualizarSensores("PONER AQUI DATOS DE LOS SENSORES SIN EXTRAER");
            contadorErrorPruebaConxion = 0;
        }
        //La bandera es cero y si no es nulo el textview
        else if (tvconexion != null) {
            tvconexion.setText(new ContextThemeWrapper().getResources().getString(R.string.EstadoConexion0));
            tvconexion.setTextColor(new ContextThemeWrapper().getResources().getColor(R.color.rojo));

            //COMENTADO PARA VERSION ESTABLE
            //if(btnPower != null)
            //    btnPower.setVisibility(View.INVISIBLE);
            //if(btnActualizar != null)
            //  btnActualizar.setVisibility(View.VISIBLE);

            Fragment2.avisoSensoresDesactualizados();
            sumarContadorErrorPruebaConxion();
        }
    }

    /**
     * clickAterrizaje: Envía un dato al arduino que le indica que guarde o desplegue el patín de aterrizaje.
     * @param v
     */
    public void clickAterrizaje(View v){

        //COMENTADO PARA VERSION ESTABLE
        //if (!banderaEstadoConexion)
        //    return;
        vib.vibrate(200);
        Toast toast;
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
        vib.vibrate(200);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("¿Seguir@?");
        builder.setMessage("Una posición de despeje o aterrizaje comprometida, podría afectar la integridad de Ramón.");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Torre.switchMotores();
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

    public static synchronized void sumarContadorErrorPruebaConxion(){
        contadorErrorPruebaConxion ++;
    }

    public static synchronized void resetContadorErrorPruebaConxion(){
        contadorErrorPruebaConxion = 0;
    }

    public static synchronized int getContadorErrorPruebaConxion(){
        return contadorErrorPruebaConxion;
    }
}