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
 * Autor: Luis Alfonso Ch.
 * Fragment1: Clase que hereda de fragment, mostrará controles de vuelo y botón para despleguar o guardar el patín de aterrizaje.
 */
public class Fragment1 extends Fragment {

     //Botones de los controles
     public static ImageButton bAdelante;
     public static ImageButton bAtras;
     public static ImageButton bDerecha;
     public static ImageButton bIzquierda;
     public static ImageButton bArriba;
     public static ImageButton bAbajo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        bAdelante = (ImageButton)getActivity().findViewById(R.id.ibAdelante);
        bAtras = (ImageButton)getActivity().findViewById(R.id.ibAtras);
        bDerecha = (ImageButton)getActivity().findViewById(R.id.ibDerecha);
        bIzquierda = (ImageButton)getActivity().findViewById(R.id.ibIzquierda);
        bArriba = (ImageButton)getActivity().findViewById(R.id.ibArriba);
        bAbajo = (ImageButton)getActivity().findViewById(R.id.ibAbajo);

        bAdelante.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (estadoDeArranque()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bAdelante.setImageResource(R.drawable.flecha_arriba);
                        Torre.avanzar();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        bAdelante.setImageResource(R.drawable.flecha_adelante);
                        Torre.estabilizar();
                    }
                }
                return true;
            }
        });
        bAtras.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (estadoDeArranque()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bAtras.setImageResource(R.drawable.flecha_abajo);
                        Torre.retroceder();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        bAtras.setImageResource(R.drawable.flecha_atras);
                        Torre.estabilizar();
                    }
                }
                return true;
            }
        });
        bDerecha.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (estadoDeArranque()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bDerecha.setImageResource(R.drawable.flecha_derecha2);
                        Torre.derecha();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        bDerecha.setImageResource(R.drawable.flecha_derecha);
                        Torre.estabilizar();
                    }
                }
                return true;
            }
        });
        bIzquierda.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (estadoDeArranque()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bIzquierda.setImageResource(R.drawable.flecha_izquierda2);
                        Torre.izquierda();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        bIzquierda.setImageResource(R.drawable.flecha_izquierda);
                        Torre.estabilizar();
                    }
                }
                return true;
            }
        });
        bArriba.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (estadoDeArranque()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bArriba.setImageResource(R.drawable.flecha_adelante);
                        Torre.subir();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        bArriba.setImageResource(R.drawable.flecha_arriba);
                        Torre.estabilizar();
                    }
                }
                return true;
            }
        });
        bAbajo.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event){
                if (estadoDeArranque()) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        bAbajo.setImageResource(R.drawable.flecha_atras);
                        Torre.bajar();
                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        bAbajo.setImageResource(R.drawable.flecha_abajo);
                        Torre.estabilizar();
                    }
                }
                return true;
            }
        });
    }

    /**
     * estadoDeArranque: Regresa True si los motores estan encendidos y la conexion establecida, de lo contrario regresa False.
     * @return
     */
    private boolean estadoDeArranque(){
        if (FlightControls.banderaEstadoConexion && FlightControls.motoresEncendidos)
            return true;
        return false;
    }

    /**
     * inicializarBotones: Reinicia el estado de los botones del control de vuelo.
     */
    public static void inicializarBotones(){
        if(FlightControls.banderaEstadoConexion && FlightControls.motoresEncendidos) {
            Torre.estabilizar();
            bAdelante.setImageResource(R.drawable.flecha_adelante);
            bAtras.setImageResource(R.drawable.flecha_atras);
            bDerecha.setImageResource(R.drawable.flecha_derecha);
            bIzquierda.setImageResource(R.drawable.flecha_izquierda);
            bArriba.setImageResource(R.drawable.flecha_arriba);
            bAbajo.setImageResource(R.drawable.flecha_abajo);
        }
    }

}
