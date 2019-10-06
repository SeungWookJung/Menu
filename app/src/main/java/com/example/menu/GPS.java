package com.example.menu;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

public class GPS extends Activity {


    public double lat;
    public double lon;

    private final Context mcontext;

    public GPS(Context context)
    {
        this.mcontext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( GPS.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 0 );
        }
        else{

            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            SetLat(location.getLatitude());
            SetLon(location.getLongitude());

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, gpsLocationListener);

        }


    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            lon = location.getLongitude();
            lat = location.getLatitude();

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    public void SetLat(double lat)
    {
        this.lat = lat;
    }

    public double getLat()
    {
        return this.lat;
    }

    public void SetLon(double lon)
    {
        this.lon = lon;
    }

    public double getLon()
    {
        return this.lon;
    }

}
