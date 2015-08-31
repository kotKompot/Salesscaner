package com.kirichko.salesscanner.Controllers;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Киричко on 04.08.2015.
 */
public class MapPositioningController implements OnMapReadyCallback, LocationListener {

    private GoogleMap mGoogleMap;


    @Override
    public void onMapReady(GoogleMap map) {
        mGoogleMap = map;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng testMarker = new LatLng(55.929431, 37.518340);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(testMarker, 13));
        map.addMarker(new MarkerOptions()
                .title("MIPT")
                .snippet("moscow institute of physics and technology")
                .position(testMarker));
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}


