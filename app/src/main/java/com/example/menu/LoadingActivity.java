package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import static java.lang.System.exit;

public class LoadingActivity extends AppCompatActivity {

    LocationManager locationManager;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았을수도 있습니다. \n 설정창으로 가시겠습니까?");

        //OK 를 누르게 되면 설정창으로 이동합니다.
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        //Cancle 하면 종료 합니다.
        alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                exit(1);
            }
        });

            //gps가 꺼져 있을시 설정 화면으로 이동
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                alertDialog.show();
            }
            //gps가 켜져 있을시 자동으로 메인화면으로 넘어감
            else
                {
                ToMain();
                }

    }
    private void ToMain()
    {
        intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
