package com.example.menu;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class GpsInformation extends Service implements LocationListener {

    private  final Context mContext;

    // 최소 GPS 정보 업데이트 거리 10미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    //현재 gps 사용 유무
    boolean isGPSEnabled = false;

    // 네트워크 사용유무
    boolean isNetworkEnabled = false;

    // 최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    // GPS 상태값
    boolean isGetLocation = false;

    Location location;
    protected LocationManager locationManager;


    double lat; // 위도
    double lon; // 경도


    @TargetApi(23)

    public Location getLocation() {

        if ( Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION )
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        try {

            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);



            // GPS 정보 가져오기
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // 현재 네트워크 상태 값 알아오기
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!isGPSEnabled && !isNetworkEnabled) {
                //위치와 네트워크를 사용하지 못할 때
                //Toast.makeText("",,Toast.LENGTH_SHORT);

            } else {
                this.isGetLocation = true;

                // 네트워크 정보로 부터 위치값 가져오기
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) { location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {

                            // 위도 경도 저장
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }



                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null) {

                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location != null) {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public void stopUsingGPS(){

        if(locationManager != null){

            locationManager.removeUpdates(GpsInformation.this);

        }

    }



    /**

     * 위도값을 가져옵니다.

     * */

    public double getLatitude(){

        if(location != null){

            lat = location.getLatitude();

        }

        return lat;

    }



    /**

     * 경도값을 가져옵니다.

     * */

    public double getLongitude(){

        if(location != null){

            lon = location.getLongitude();

        }

        return lon;

    }



    /**

     * GPS 나 wife 정보가 켜져있는지 확인합니다.

     * */

    public boolean isGetLocation() {

        return this.isGetLocation;

    }

    public GpsInformation(Context mContext) {
        this.mContext = mContext;
    }

    public void showSettingsAlert(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);



        alertDialog.setTitle("GPS 사용유무셋팅");

        alertDialog.setMessage("GPS 셋팅이 되지 않았을수도 있습니다. \n 설정창으로 가시겠습니까?");



        // OK 를 누르게 되면 설정창으로 이동합니다.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });

        // Cancle 하면 종료 합니다.

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
                });

        alertDialog.show();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
