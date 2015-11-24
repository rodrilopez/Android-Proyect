package com.groups.p2.saferider;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity {

    private Chronometer chronometer;
    Context context = this;
    GoogleMap mMap = null; // Might be null if Google Play services APK is not available.
    SQLiteAdapter sql = new SQLiteAdapter(context, "Config", null, 1);
    private Uri notification;
    private Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();

        LocationManager mLoc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener MyLocationListener = new MyLocationListener(this);
        mLoc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, (LocationListener) MyLocationListener);


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (chronometer.getText().equals("00:10")) {
                    conf();
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

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));

                //Setear el mapa
                if (location != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

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

    public void setLocation() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

    }

    public AlertDialog conf() {
        try {
            notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r= RingtoneManager.getRingtone(context, notification);
            r.play();
        }catch (Exception e){
            e.printStackTrace();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you ok ?").setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                r.stop();
                String phone = sql.Leer(context, "Config", 1, 3);

                //Sms
                PendingIntent sentIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("SMS_SENT"), 0);
                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        switch (getResultCode()) {
                            case Activity.RESULT_OK:
                                Toast.makeText(getApplicationContext(),
                                        "SMS send",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                Toast.makeText(getApplicationContext(),
                                        "No se pudo enviar SMS",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_NO_SERVICE:
                                Toast.makeText(getApplicationContext(),
                                        "Servicio no diponible",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_NULL_PDU:
                                Toast.makeText(getApplicationContext(),
                                        "PDU (Protocol Data Unit) es NULL",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case SmsManager.RESULT_ERROR_RADIO_OFF:
                                Toast.makeText(getApplicationContext(),
                                        "Failed because radio was explicitly turned off",
                                        Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new IntentFilter("SMS_SENT"));

                SmsManager mensaje = SmsManager.getDefault();
                if (phone.toString().length() > 0) {
                    mensaje.sendTextMessage(phone.toString(), null,
                            "I'm in trouble my location is: Latitude " + "and Longitude ",
                            sentIntent,
                            null);
                    Toast.makeText(getApplicationContext(),
                            "Sms send a " + phone,
                            Toast.LENGTH_SHORT).show();
                    setLocation();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "No se puede enviar, los datos son incorrectos",
                            Toast.LENGTH_SHORT).show();
                    setLocation();
                }
            }
        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                r.stop();
                confpass();
                setLocation();
            }
        });

        return builder.show();
    }

    public AlertDialog confpass() {
        LayoutInflater li = LayoutInflater.from(context);
        View v = li.inflate(R.layout.passconfirm, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v);


        final EditText userInput = (EditText) v
                .findViewById(R.id.editTextPassConfirm);

        //Set el dialog
        builder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input
                                String pass = String.valueOf(userInput.getText());
                                String passing = sql.Leer(context, "Config", 1, 3);
                                if (pass == passing) {
                                    setLocation();
                                } else {
                                    Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT);
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String pass = String.valueOf(userInput.getText());
                                String passing = sql.Leer(context, "Config", 1, 3);
                                if (pass == passing) {
                                    conf();
                                    dialog.cancel();
                                } else {
                                    Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT);
                                }
                            }
                        });

        return builder.show();
    }
}


