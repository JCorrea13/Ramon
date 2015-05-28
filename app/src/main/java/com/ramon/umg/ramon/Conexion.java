package com.ramon.umg.ramon;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;
import java.util.Timer;

/**
 * Created by JCORREA on 30/03/2015.
 */
public class Conexion{

    public static UsbDevice device;
    // Get UsbManager from Android.
    public static volatile UsbManager manager;
    // Find the first available driver.
    public static volatile UsbSerialDriver driver;
    private static int baudRate = 115200;
    private static int tiempoCompruebaConexion = 5000;
    private static int LimiteReconexionAutomatica = 3;
    private static String datos = null;
    public static Timer tListenerSerial = null;
    public static Timer tPruebaConexion = null;

    /**
     * Este metodo configura y crea la conexion con
     * Ramon(configura puerto serial)
     */
    public static void setConexion(FlightControls activity, int baudRate) throws IOException{
        Conexion.manager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        Conexion.manager.getDeviceList();
        Conexion.driver = UsbSerialProber.acquire(Conexion.manager);
        setBaudRate(baudRate);

        if(driver != null) {
            driver.open();
            driver.setBaudRate(getBaudRate());
        }else{
            Util.makeToast("No se reconocio ningun dispositivo conectado");
        }

        //iniciarHiloListenerSerial();
        //iniciarHiloPruebaConexion();

    }

    /**
     * Este metodo verifica que exista una conexion con Ramon
     * @return True si existe la conexion, False si no existe
     */
    public static synchronized Boolean pruebaConexion() throws IOException {
        String datos = lee();
        if(datos != null && datos.length() > 0) {
            System.out.println("prueba conexion: " + datos);
            return true;
        }
        return false;
    }

    /**
     * Este metodo lee el puerto serial y
     * toma lo que hay en este
     * @return String con datos leidos
     */
    public static synchronized String lee() throws IOException{
        if (driver != null) try {

            byte buffer[] = new byte[8];
            int numBytesRead = driver.read(buffer, 8);
            if(numBytesRead > 0) {
                FlightControls.banderaEstadoConexion = true;
                FlightControls.actualizaEstadoConexion();
            }
            datos = buffer.toString();
        }catch (IOException e) {
            System.out.println("Error en la lectura de datos");
            driver.close();
        }
        return datos;
    }

    /**
     * Este metodo escribe en el serial del dispositivo
     * Android
     * @param dato (byte)
     */
    public static synchronized void escribe(byte dato){

        if(driver == null)
            return;

        try {
            byte [] datos  = new byte[1];
            datos[0] = dato;
            System.out.println("escribiendo : " + (char)dato);
            driver.write(datos,0);

        } catch (IOException e) {
            System.out.println("Error al ecribir en el puerto" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static int getBaudRate() {
        return baudRate;
    }

    public static void setBaudRate(int baudRate) {
        Conexion.baudRate = baudRate;
    }
    public static void setTiempoCompruebaConexion(int tiempoCompruebaConexion) {
        Conexion.tiempoCompruebaConexion = tiempoCompruebaConexion;
    }
    public static int getTiempoCompruebaConexion() {
        return tiempoCompruebaConexion;
    }

    public static int getLimiteReconexionAutomatica() {
        return LimiteReconexionAutomatica;
    }
    public static void setLimiteReconexionAutomatica(int limiteReconexionAutomatica) {
        LimiteReconexionAutomatica = limiteReconexionAutomatica;
    }

    public static synchronized void setDatos(String nuevosdatos){
        datos = nuevosdatos;
    }

    public static synchronized String getDatos(){
        return datos;
    }

    public static void cerrarPuerto(){
        try {
            if (driver != null)
                driver.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void iniciarHiloListenerSerial(){
        if(tListenerSerial == null) {
            tListenerSerial = new Timer("ListenerSerial", true);
            tListenerSerial.scheduleAtFixedRate(new SubProcesos.ListenerSerial(), 1000, 0);
        }
    }

    public static void terminarHiloListenerSerial(){
        if(tListenerSerial != null){
            tListenerSerial.cancel();
            tListenerSerial = null;
        }
    }

    public static void iniciarHiloPruebaConexion(){
        if(tPruebaConexion == null) {
            tPruebaConexion = new Timer("PruebaConexion",true);
            tPruebaConexion.scheduleAtFixedRate(new SubProcesos.PruebaConexion(), 1000, 0);
        }
    }

    public static void terminarHiloPruebaConexion(){
        if(tPruebaConexion != null){
            tPruebaConexion.cancel();
            tPruebaConexion = null;
        }
    }
}
