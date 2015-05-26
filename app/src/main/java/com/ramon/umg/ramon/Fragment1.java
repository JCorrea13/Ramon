package com.ramon.umg.ramon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Fragment1: Clase que hereda de fragment, mostrará controles de vuelo y botón para despleguar o guardar el patín de aterrizaje.
 */
public class Fragment1 extends Fragment {
    /**
     * bAdelante: Botón para hacer al drone moverse hacia adelante.
     */
     public static ImageButton bAdelante;
    /**
     * bAtras: Botón para hacer al drone moverse hacia atrás.
     */
     public static ImageButton bAtras;
    /**
     * bDerecha: Botón para hacer al drone moverse hacia la derecha.
     */
     public static ImageButton bDerecha;
    /**
     * bIzquierda: Botón para hacer al drone moverse hacia la izquierda.
     */
     public static ImageButton bIzquierda;
    /**
     * bArriba: Botón para hacer al drone moverse hacia arriba.
     */
     public static ImageButton bArriba;
    /**
     * bAbajo: Botón para hacer al drone moverse hacia abajo.
     */
     public static ImageButton bAbajo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        bAdelante = (ImageButton)view.findViewById(R.id.ibAdelante);
        bAtras = (ImageButton)view.findViewById(R.id.ibAtras);
        bDerecha = (ImageButton)view.findViewById(R.id.ibDerecha);
        bIzquierda = (ImageButton)view.findViewById(R.id.ibIzquierda);
        bArriba = (ImageButton)view.findViewById(R.id.ibArriba);
        bAbajo = (ImageButton)view.findViewById(R.id.ibAbajo);

        //AÑADIR EN CADA EVENTO EL DATO QUE SE LE ENVIARÁ AL ARDUINO PARA QUE ESTE SE ENCARGUE DEL MOVIENTO
        bAdelante.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (!FlightControls.banderaEstadoConexion) {
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    bAdelante.setImageResource(R.drawable.flecha_arriba);
                    Torre.desplazar(Torre.DIR_ADELANTE);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    bAdelante.setImageResource(R.drawable.flecha_adelante);
                    Torre.desplazar(Torre.MODO_ESTATICO);
                }
                return true;
            }
        });
        bAtras.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (!FlightControls.banderaEstadoConexion) {
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    bAtras.setImageResource(R.drawable.flecha_abajo);
                    Torre.desplazar(Torre.DIR_ATRAS);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    bAtras.setImageResource(R.drawable.flecha_atras);
                    Torre.desplazar(Torre.MODO_ESTATICO);
                }
                return true;
            }
        });
        bDerecha.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (!FlightControls.banderaEstadoConexion) {
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    bDerecha.setImageResource(R.drawable.flecha_derecha2);
                    Torre.desplazar(Torre.DIR_DERECHA);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    bDerecha.setImageResource(R.drawable.flecha_derecha);
                    Torre.desplazar(Torre.MODO_ESTATICO);
                }
                return true;
            }
        });
        bIzquierda.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (!FlightControls.banderaEstadoConexion) {
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    bIzquierda.setImageResource(R.drawable.flecha_izquierda2);
                    Torre.desplazar(Torre.DIR_IZQUIERDA);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    bIzquierda.setImageResource(R.drawable.flecha_izquierda);
                    Torre.desplazar(Torre.MODO_ESTATICO);
                }
                return true;
            }
        });
        bArriba.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (!FlightControls.banderaEstadoConexion) {
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    bArriba.setImageResource(R.drawable.flecha_adelante);
                    Torre.subir();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    bArriba.setImageResource(R.drawable.flecha_arriba);
                    Torre.desplazar(Torre.MODO_ESTATICO);
                }
                return true;
            }
        });
        bAbajo.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (!FlightControls.banderaEstadoConexion) {
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    bAbajo.setImageResource(R.drawable.flecha_atras);
                    Torre.bajar();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    bAbajo.setImageResource(R.drawable.flecha_abajo);
                    Torre.desplazar(Torre.MODO_ESTATICO);
                }
                return true;
            }
        });

        return view;
    }

    /**
     * inicializarBotones: Reinicia el estado de los botones del control de vuelo.
     */
    public static void inicializarBotones(){
        bAdelante.setImageResource(R.drawable.flecha_adelante);
        bAtras.setImageResource(R.drawable.flecha_atras);
        bDerecha.setImageResource(R.drawable.flecha_derecha);
        bIzquierda.setImageResource(R.drawable.flecha_izquierda);
        bArriba.setImageResource(R.drawable.flecha_arriba);
        bAbajo.setImageResource(R.drawable.flecha_abajo);
        if(FlightControls.banderaEstadoConexion)
            Torre.desplazar(Torre.MODO_ESTATICO);
    }

}
