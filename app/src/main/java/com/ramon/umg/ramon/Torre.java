package com.ramon.umg.ramon;

import java.io.IOException;

/**
 * Created by JCORREA on 30/03/2015.
 * Esta clase tinen el unico objetivo de comunicar al Piloto de Ramon los movimientos que tiene que realizar.
 * Contiene todas las instrucciones que Ramon soporta.
 */
public class Torre{

    //Protocolo:
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

    public static void estabilizar(){ Conexion.escribe((byte)MODO_ESTATICO);}
    public static void subir(){ Conexion.escribe((byte)DIR_SUBIR); }
    public static void bajar(){ Conexion.escribe((byte)DIR_BAJAR); }
    public static void avanzar(){ Conexion.escribe((byte)DIR_ADELANTE); }
    public static void retroceder(){ Conexion.escribe((byte)DIR_ATRAS); }
    public static void izquierda(){
        Conexion.escribe((byte)DIR_IZQUIERDA);
    }
    public static void derecha(){Conexion.escribe((byte)DIR_DERECHA); }
    public static void aterrizar(){
        Conexion.escribe((byte)ATERRIZA);
    }
    public static void guardarTrenAterrizaje(){ Conexion.escribe((byte)TREN_ATERRIZAJE_CAMBIOESTADO); }
    public static void despliegaTrenAterrizaje(){ Conexion.escribe((byte)TREN_ATERRIZAJE_CAMBIOESTADO); }
    public static void switchMotores(){
        Conexion.escribe((byte)ON_OFF_MOTORES);
    }
    public static void inicioConexion(){ Conexion.escribe((byte)INICIO_CONEXION); }
}
