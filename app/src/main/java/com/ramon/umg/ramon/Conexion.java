package com.ramon.umg.ramon;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JCORREA on 30/03/2015.
 */
public class Conexion{

    public static volatile UsbManager manager;
    public static volatile UsbSerialDriver driver;
    public static FlightControls activity;
    private static final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private static SerialInputOutputManager manejadorSerial;

    /**
     * Este metodo configura y crea la conexion con
     * Ramon(configura puerto serial)
     */
    public static void setConexion(FlightControls act) throws IOException{
        activity  = act;
        manager = (UsbManager) act.getSystemService(Context.USB_SERVICE);
        manager.getDeviceList();
        driver = UsbSerialProber.acquire(manager);;

        if(driver != null) {
            driver.open();
            driver.setBaudRate(57600);
            manejadorSerial = new SerialInputOutputManager(driver, mListener);
            mExecutor.submit(manejadorSerial);
        }else
            Util.makeToast("Conectar Antena");
    }

    //Evento para recibir datos del serial USB.
    private static final SerialInputOutputManager.Listener mListener = new SerialInputOutputManager.Listener() {
                @Override
                public void onRunError(Exception e) {
                }

                @Override
                public void onNewData(final byte[] datos) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Conexion.actualizarDatosRecibidos(datos);
                        }
                    });
                }
            };

    /**
     * actualizarDatosRecibidos: Recibe los datos que llegan al serial usb, y si el hilo de conexion esta
     * corriendo, los manipula. De lo contrario los ignora para que el usuario tenga que reconectar al drone.
     * @param datos: Los datos recibidos por el evento.
     */
    private static void actualizarDatosRecibidos(byte[] datos) {
        if (FlightControls.correrHiloConexion) {
            FlightControls.banderaEstadoConexion = true;
            final String str = convierteDatosAString(datos);
            Fragment2.actualizarSensores(str);
        }
    }

    /**
     * convierteDatosAString: Convierte los bytes recibidos a una cadena de texto.
     * @param datos: Datos recibidos por el evento de SerialInputOutpuManager.
     * @return: Los datos en forma de cadena de texto.
     */
    private static String convierteDatosAString(byte[] datos){
        String str = "";
        for(byte i : datos)
            str += (char)i;
        return str;
    }

    /**
     * Este metodo escribe en el serial del dispositivo
     * Android
     * @param dato (byte)
     */
    public static synchronized void escribe(byte dato){
        if(driver == null) return;
        try {
            byte [] datos  = new byte[1];
            datos[0] = dato;
            driver.write(datos,0);
        } catch (IOException e) {
            Util.makeToast("Error enviando datos");
            e.printStackTrace();
        }
    }

    /**
     * cerrarPuerto: Llamar para cuando se termine la aplicacion para liberar espacio del driver.
     */
    public static void cerrarPuerto(){
        try {
            if (driver != null)
                driver.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}