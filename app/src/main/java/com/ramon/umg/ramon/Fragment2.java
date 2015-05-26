package com.ramon.umg.ramon;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Fragment2 extends Fragment {
    /**
     * tvSensoresDesactualizados: TextView predefinido, que se hace visible sí y solo sí se desconecta el drone.
     */
    private static TextView tvSensoresDesactualizados;
    /**
     * tvSensorGPS: TextView con string GPS: .
     */
    private static TextView tvSensorGPS;
    /**
     * tvSensorGyro: TextView con string Giroscopio: .
     */
    private static TextView tvSensorGyro;
    /**
     * tvSensorTemp: TextView con string Temperatura: .
     */
    private static TextView tvSensorTemp;
    /**
     * tvDatosGps: TextView para modificar e imprimir datos actuales del sensor.
     */
    private static TextView tvDatosGPS;
    /**
     * tvDatosGyro: TextView para modificar e imprimir datos actuales del sensor.
     */
    private static TextView tvDatosGyro;
    /**
     * tvDatosTemp: TextView para modificar e imprimir datos actuales del sensor.
     */
    private static TextView tvDatosTemp;
    private static boolean primerConexion = false; //La primera vez que se conecta muestra los sensores y ya se quedan alli, esta bandera se encarga de eso.

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        tvSensoresDesactualizados = (TextView)view.findViewById(R.id.tvSensoresDesactualizados);
        tvSensorGPS = (TextView)view.findViewById(R.id.tvGPS);
        tvSensorGyro = (TextView)view.findViewById(R.id.tvGyro);
        tvSensorTemp = (TextView)view.findViewById(R.id.tvTemp);
        tvDatosGPS = (TextView)view.findViewById(R.id.tvInfoGPS);
        tvDatosGyro = (TextView)view.findViewById(R.id.tvInfoGyro);
        tvDatosTemp = (TextView)view.findViewById(R.id.tvInfoTemp);
        //tvSensores.post(Conexion.hiloListernerSerial);
        return view;
    }

    /**
     * actualizatrSensores: Actualiza el texto de tvSensores y hace invisible a tvSensoresDesactualizados
     * @param text es el texto, ya con el formato, que se mostrará al usuario en la app.
     */
    public static synchronized void actualizarSensores(String text){
        if (!primerConexion){
            primerConexion = true;
            tvSensorGPS.setVisibility(View.VISIBLE);
            tvSensorGyro.setVisibility(View.VISIBLE);
            tvSensorTemp.setVisibility(View.VISIBLE);
            tvDatosGPS.setVisibility(View.VISIBLE);
            tvDatosGyro.setVisibility(View.VISIBLE);
            tvDatosTemp.setVisibility(View.VISIBLE);
        }
        if(tvSensoresDesactualizados != null)
            tvSensoresDesactualizados.setVisibility(View.INVISIBLE);
        if (!text.isEmpty()) {
            //AQUI HAY QUE EXTRAER LOS DATOS DE LOS SENSORES Y ACOMODARLOS EN SUS VARIABLES:
            String GPS = "";
            String Gyro = "";
            String Temp = "";
            tvDatosGPS.setText(GPS);
            tvDatosGyro.setText(Gyro);
            tvDatosTemp.setText(Temp);
        }
    }

    /**
     * avisoSensoresDesactualizados: Se debe llamar siempre que se pierda conexión, avisa al usuario que la información
     * de los sensores no está actualizada.
     */
    public static void avisoSensoresDesactualizados(){
        if(tvSensoresDesactualizados != null)
            tvSensoresDesactualizados.setVisibility(View.VISIBLE);
    }

    private static void makeToast(String texto){
        Toast toast = Toast.makeText(FlightControls.fly.getApplicationContext(), texto, Toast.LENGTH_SHORT);
        toast.show();
    }
}