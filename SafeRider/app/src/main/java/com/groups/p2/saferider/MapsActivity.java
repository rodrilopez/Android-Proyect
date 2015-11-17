package com.groups.p2.saferider;


import android.app.AlertDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;


public class MapsActivity extends FragmentActivity {

    private Chronometer chronometer;
    GoogleMap  mMap = null; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        chronometer = (Chronometer)findViewById(R.id.chronometer);
        chronometer.start();
        LocationManager mLoc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener MyLocationListener = new MyLocationListener(this);
        mLoc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, (LocationListener) MyLocationListener);
        final Context cnt = this.getApplicationContext();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (chronometer.getText().equals("00:10")){
                    Toast.makeText(cnt, "Sono", Toast.LENGTH_LONG).show();
                    conf();
                    //TODO sonar alarma
                }
            }
        });



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

    public void setLocation(Location loc){
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

    }

    public AlertDialog conf(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.confirmar, null);

        builder.setView(v);
        return builder.show();
    }

    }


