package com.groups.p2.saferider;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by rodrigo on 17/11/15.
 */
public class MyLocationListener implements LocationListener {
    MapsActivity mapsActivity;

    public MyLocationListener(MapsActivity mapsActivity) {
        this.mapsActivity=mapsActivity;
    }

    public MapsActivity getMapsActivity(){
        return mapsActivity;
    }

    public void setMapsActivity (MapsActivity mapsActivity){
        this.mapsActivity = mapsActivity;
    }
    @Override
    public void onLocationChanged(Location loc) {
        mapsActivity.setLocation(loc);
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
