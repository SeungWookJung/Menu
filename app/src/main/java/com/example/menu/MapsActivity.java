package com.example.menu;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;


    Location location;
    LocationManager Lm;
    public static Weather_GPS gps = new Weather_GPS();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double lat = gps.getLat();
        double lon = gps.getLon();

        Log.d("위도","위도: "+ lat);
        Log.d("경도","경도: "+ lon);
        
        LatLng busan = new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(busan).title("요는 현재위치"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(busan));


    }



}
