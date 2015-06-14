package com.ramon.umg.ramon;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Autor: Luis Alfonso Ch.
 */
public class Fragment3 extends Fragment implements OnMapReadyCallback {

    private Button bEnfocarBA;
    private Button bEnfocarBT;

    private Marker marcadorAereo;
    private LatLng latitud_y_longitud;
    private GoogleMap.OnMyLocationChangeListener LocationCListener;
    private GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FragmentoMapa mapFragment = new FragmentoMapa();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        bEnfocarBA = (Button)getActivity().findViewById(R.id.bEnfocarBAerea);
        bEnfocarBT = (Button)getActivity().findViewById(R.id.bEnfocarBTerrena);

        bEnfocarBA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enfocarBaseAerea();
            }
        });
        bEnfocarBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enfocarBaseTerrena();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        final GoogleMap map2 = map;
        latitud_y_longitud = new LatLng(Fragment2.latitud, Fragment2.longitud);
        map2.setMyLocationEnabled(true);
        marcadorAereo = map.addMarker(new MarkerOptions()
                .position(latitud_y_longitud));

        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);
        LatLng loc;
        if(location!=null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            loc = new LatLng(latitude, longitude);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
        }

        LocationCListener = new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                if(map2 != null){
                    map2.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
                    map2.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                    latitud_y_longitud = new LatLng(Fragment2.latitud, Fragment2.longitud);
                    marcadorAereo = map2.addMarker(new MarkerOptions()
                            .position(latitud_y_longitud));
                }
            }
        };
    }

    /**
     * enfocarBaseAerea: Obtiene los datos de latitud y longitud del marcador aereo y lo muestra en el mapa.
     */
    private void enfocarBaseAerea(){
        LatLng loc = marcadorAereo.getPosition();
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
    }

    /**
     * enfocarBaseAerea: Obtiene los datos de latitud y longitud del usuario y lo muestra en el mapa.
     */
    private void enfocarBaseTerrena(){
        Location location = map.getMyLocation();
        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
    }
}