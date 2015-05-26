package com.ramon.umg.ramon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.View;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
        setContentView(R.layout.activity_flight_controls);
        vib = (Vibrator)this.getApplicationContext().getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        tvconexion = (TextView)findViewById(R.id.tvEstadoConexion);
        viewpager = (ViewPager)findViewById(R.id.pager);
        PagerAdapter padapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(padapter);
        fly = this;

        //hacemos referencia a los miembros del XML
        btnActualizar = (ImageButton)findViewById(R.id.ibReload);
        btnPower = (ImageButton)findViewById(R.id.ibPower);

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
        if (banderaEstadoConexion && (tvconexion != null)) {
            tvconexion.setText(new ContextThemeWrapper().getResources().getString(R.string.EstadoConexion1));
            tvconexion.setTextColor(new ContextThemeWrapper().getResources().getColor(R.color.verde));
            btnPower.setVisibility(View.VISIBLE);
            btnActualizar.setVisibility(View.INVISIBLE);
            //Fragment2.actualizarSensores(textSensores);
            contadorErrorPruebaConxion = 0;
        }
        else if (tvconexion != null) {
            tvconexion.setText(new ContextThemeWrapper().getResources().getString(R.string.EstadoConexion0));
            tvconexion.setTextColor(new ContextThemeWrapper().getResources().getColor(R.color.rojo));
            if(btnPower != null)
                btnPower.setVisibility(View.INVISIBLE);
            if(btnActualizar != null)
            btnActualizar.setVisibility(View.INVISIBLE);
            Fragment2.avisoSensoresDesactualizados();
            sumarContadorErrorPruebaConxion();
        }
    }

    /**
     * clickActualizar: Envia un bit al arduino y espera la respueta de este, si esta llega, se establece el modo conectado,
     * si no, se mantiene la desconexión.
     * @param v
     */
    public void clickActualizar(View v){
        try {
            if(Torre.inicioConexion()) {
                vib.vibrate(200);
                //if(hiloPruebaConexion.isInterrupted())
                    //hiloPruebaConexion.run();
                makeToast("Conexion establecida");
            }else{
                vib.vibrate(200);
                //if(!hiloPruebaConexion.isInterrupted())
                    //hiloPruebaConexion.resume();
                makeToast("No se pudo conectar con Ramon");
            }

        }catch (IOException e) {
            vib.vibrate(200);
            //if(!hiloPruebaConexion.isInterrupted())
                //hiloPruebaConexion.resume();
            makeToast("Error al tratar de establecer la comunicacion");
            e.printStackTrace();
        }
    }

    /**
     * clickAterrizaje: Envía un dato al arduino que le indica que guarde o desplegue el patín de aterrizaje.
     * @param v
     */
    public void clickAterrizaje(View v){
        if (!banderaEstadoConexion)
            return;
        vib.vibrate(200);
        Toast toast;
        if (aterrizaje) {
            Torre.guardarTrenAterrizaje();
            makeToast("Guardando Patín de Aterrizaje");
            aterrizaje = false;
        } else {
            Torre.despliegaTrenAterrizaje();
            makeToast("Desplegando Patín de Aterrizaje");
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

    /**
     * makeToast: Crea un toast con el texto que se le envíe.
     * @param texto : Es el texto a mostrar.
     */
    private void makeToast(String texto){
        Toast toast = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT);
        toast.show();
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