package com.ramon.umg.ramon;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by LUIS ALFONSO on 25/05/2015.
 */
public class FragmentoMapa extends com.google.android.gms.maps.SupportMapFragment {

    private GoogleMap.OnMyLocationChangeListener LocationCListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final GoogleMap googleMap = getMap();

    }

}