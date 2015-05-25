package com.ramon.umg.ramon;

import java.io.IOException;

/**
 * Created by JCORREA on 30/03/2015.
 * Esta clase tinen el unico objetivo de comunicar al Piloto de Ramon
 * los movimientos que tiene que realiza.
 * Contiene todas las instrucciones que Ramon soporta.
 */
public class Torre{

    public static final char MODO_ESTATICO = '0';
    public static final char DIR_IZQUIERDA = '1';
    public static final char DIR_DERECHA = '2';
    public static final char DIR_ADELANTE = '3';
    public static final char DIR_ATRAS = '4';
    private static final char DIR_BAJAR = '5';
    private static final char DIR_SUBIR = '6';
    private static final char ATERRIZA = '7';
    private static final char TREN_ATERRIZAJE_CAMBIOESTADO = '8';
    private static final char ON_OFF_MOTORES = '9';
    private static final char INICIO_CONEXION = 'A';

    public static boolean desplazar(char direccion){

        Conexion.escribe((byte)direccion);
        return true;
    }
    public static void subir(){
        Conexion.escribe((byte)DIR_SUBIR);
    }
    public static void bajar(){
        Conexion.escribe((byte)DIR_BAJAR);
    }
    public static void aterriza(){
        Conexion.escribe((byte)ATERRIZA);
    }
    public static void guardarTrenAterrizaje(){
        Conexion.escribe((byte)TREN_ATERRIZAJE_CAMBIOESTADO);
    }
    public static void despliegaTrenAterrizaje(){
        Conexion.escribe((byte)TREN_ATERRIZAJE_CAMBIOESTADO);
    }
    /**
     * Este metodo cambia el estado de Ramon de encendido a apagado
     * o de pagado a encendido dependiendo del estado en el que se encuentre
     */
    public static void switchMotores(){
        Conexion.escribe((byte)ON_OFF_MOTORES);
    }
    public static boolean inicioConexion() throws IOException{

        //Conexion.escribe((byte)INICIO_CONEXION);
        if(Conexion.pruebaConexion())
            return true;
        else
            return false;
    }
}
