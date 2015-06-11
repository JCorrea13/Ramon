package com.ramon.umg.ramon;

import android.app.Activity;
import android.view.View;

import com.hoho.android.usbserial.driver.UsbSerialDriver;

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


    public static class PruebaConexion extends TimerTask{
        Activity act;
        PruebaConexion(Activity act){
            this.act = act;
        }

        /**
         * Este hilo corre para comprobar el estado de conexion
         * de la aplicacoin con Ramon
         * @autor JCORREA
         */
        @Override
        public synchronized void run() {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FlightControls.actualizaEstadoConexion();
                    if(FlightControls.getContadorErrorPruebaConxion() > Conexion.getLimiteReconexionAutomatica()) {
                        FlightControls.btnActualizar.setVisibility(View.VISIBLE);
                        FlightControls.resetContadorErrorPruebaConxion();
                    }
                    FlightControls.banderaEstadoConexion = false;
                }
            });
        }
    }
}
