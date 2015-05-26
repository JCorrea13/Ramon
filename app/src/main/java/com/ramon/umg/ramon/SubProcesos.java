package com.ramon.umg.ramon;

import android.view.View;

import com.hoho.android.usbserial.driver.UsbSerialDriver;

import java.io.IOException;
import java.util.TimerTask;

/**
 * Created by JCORREA on 23/04/2015.
 * Esta clase sirve para administrat todos los subprocesos
 * que corren en RamonApp
 */
public class SubProcesos {
    public static UsbSerialDriver driver;

    SubProcesos(UsbSerialDriver driver){
        if(driver != null)
            SubProcesos.driver = driver;
    }

    /**
     *Esta clase funciona como listener del Serial
     * para escuchar cuando los datos de los sensores han sido actualizados
     */
    public static class ListenerSerial extends TimerTask{

        ListenerSerial(){
            Util.makeToast("se inicio el hilo listener");
        }


        /**
         * Este metod sirve como listener para escuchar unicamente el puerto Serial
         *
         */
        @Override
        public void run() {
            try {
                if(driver != null) {
                    Conexion.lee();
                    Fragment2.actualizarSensores(Conexion.getDatos());

                }else{
                    Fragment2.actualizarSensores("driver null");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class PruebaConexion extends TimerTask{
        PruebaConexion(){
            Util.makeToast("Se inicio hilo PruebaConeixon");

        }

        /**
         * Este hilo corre para comprobar el estado de conexion
         * de la aplicacoin con Ramon
         * @autor JCORREA
         */
        @Override
        public void run() {
            FlightControls.actualizaEstadoConexion();
            if(FlightControls.getContadorErrorPruebaConxion() > Conexion.getLimiteReconexionAutomatica()) {
                FlightControls.btnActualizar.setVisibility(View.VISIBLE);
                FlightControls.resetContadorErrorPruebaConxion();
            }
            FlightControls.banderaEstadoConexion = false;

        }
    }
}
