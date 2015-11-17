package com.groups.p2.saferider;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLOutput;


public class MapsActivity extends FragmentActivity {
    private Thread thread;
    GoogleMap  mMap = null; // Might be null if Google Play services APK is not available.
    double VARIACION_POR_METRO_LAT= 0.000008983;
    double VARIACION_POR_METRO_LONG= 0.000008998;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        LocationManager mLoc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener mLocationListener = new mLocationListener();
        mLoc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,(LocationListener) mLocationListener);

        setUpMapIfNeeded();

    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
        if (mMap != null) {

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMyLocationEnabled(true);

            //llamar location de actvity 2

            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));

            if (location != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                        .zoom(15)       //nivel de zoom
                        .bearing(0)     //orientacion de la camara
                        .tilt(0)        //establece la inclinacion de la camara en grados
                        .build();       //constructor
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        }
    }

    public void setLocation(Location loc) {

        thread=  new Thread(){
            
            public void run(Location loc) throws InterruptedException {
                    boolean salir = true;
                    while (salir == true) {
                        System.out.println("mientras");
                        //Geteo Latitud y Longitud
                        double lat = loc.getLatitude();
                        double log = loc.getLongitude();
                        //Duermo 5 min
                        synchronized(this){
                            wait(3000);
                        }
                        //Geteo de vuelta Latitud y Longitud
                        double mLat = loc.getLatitude();
                        double mLog = loc.getLongitude();
                        //Resto las longitudes y latitudes
                        double mRLat = mLat - lat;
                        double mRLog = mLog - log;
                        //Paso a metros y veo si son mayores a 5 metros
                        if (mRLat / VARIACION_POR_METRO_LAT > 5 || mRLog / VARIACION_POR_METRO_LONG > 5) {
                            System.out.println("todo ok");
                        } else {
                            //Sonar alarma
                            salir = false;
                        }
                    }
                }
            };

        thread.start();
    }
}


class mLocationListener implements LocationListener{
    MapsActivity mapsActivity;

    public MapsActivity getMapsActivity(){
        return mapsActivity;
    }

    public void setMapsActivity (MapsActivity mapsActivity){
        this.mapsActivity = mapsActivity;
    }

    @Override
    public void onLocationChanged(Location loc){
        this.mapsActivity.setLocation(loc);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        System.out.println("GPS Enabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        System.out.println("GPS Disabled");
    }
}
