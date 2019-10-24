package com.example.menu;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Weather_GPS extends Activity

{
    private double lat;
    private double lon;


    LocationManager Lm;
    String id; //날씨의 아이디 값

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testweather);

         Lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( Weather_GPS.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 0 );
        }
        else{

            Location location = Lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            SetLat(location.getLatitude());
            SetLon(location.getLongitude());

            Lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, gpsLocationListener);
            Lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, gpsLocationListener);

        }


    }


    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

             SetLon((location.getLongitude()));
             SetLat((location.getLatitude()));


            getWeather(getLat(), getLon());
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

    private void getWeather(double latitude, double longitude) {
        Retrofit retrofit =new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WeatherApi.BASEURL)
                .build();
        final WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        Call<JsonObject> call = weatherApi.getWeahter(latitude,longitude, WeatherApi.APIKEY);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()){

                    //날씨데이터를 받아옴
                    JsonObject object = response.body();

                    //데이터가 null 이 아니라면
                    if (object != null)
                    {

                        JsonArray jarray = object.getAsJsonArray("weather");
                        JsonElement je = jarray.get(0);
                        id = je.getAsJsonObject().get("id").getAsString();

                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"날씨값이 없습니다.",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

            }
        });
    }

}
