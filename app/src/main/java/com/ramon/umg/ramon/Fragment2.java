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
     * tvSensores: TextView que muestra los datos enviados por los sensores del drone.
     */
    private static volatile TextView tvSensores;
    /**
     * tvSensoresDesactualizados: TextView predefinido, que se hace visible sí y solo sí se desconecta el drone.
     */
    private static TextView tvSensoresDesactualizados;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        tvSensores = (TextView)view.findViewById(R.id.tvInfoSensores);
        tvSensoresDesactualizados = (TextView)view.findViewById(R.id.tvSensoresDesactualizados);
        //tvSensores.post(Conexion.hiloListernerSerial);
        return view;
    }

    /**
     * actualizatrSensores: Actualiza el texto de tvSensores y hace invisible a tvSensoresDesactualizados
     * @param text es el texto, ya con el formato, que se mostrará al usuario en la app.
     */
    public static synchronized void actualizarSensores(String text){

        if(tvSensoresDesactualizados != null)
            tvSensoresDesactualizados.setVisibility(View.INVISIBLE);
        if (!text.isEmpty() && tvSensores != null) {
            tvSensores.setText(text);
            makeToast("los datos no son nulos, pero no se nota");
        }else
            makeToast("los datos son nulos");
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