package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static java.lang.System.exit;

public class LoadingActivity extends AppCompatActivity {


    LocationManager locationManager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView imageViewRes = (ImageView) findViewById(R.id.loading);
        Picasso.get().load(R.drawable.loading).into(imageViewRes);

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoadingActivity.this);
        alertDialog.setTitle("Weather_GPS 사용유무셋팅");
        alertDialog.setMessage("Weather_GPS 셋팅이 되지 않았을수도 있습니다. \n 설정창으로 가시겠습니까?");

        //OK 를 누르게 되면 설정창으로 이동합니다.
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent,0);
                Toast.makeText(LoadingActivity.this,"GPS를 활성화 시켜주세요",Toast.LENGTH_LONG).show();

            }
        });

        //Cancel 하면 종료 합니다.
        alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                Toast.makeText(LoadingActivity.this, "GPS와 인터넷이 연결되어 있어야 합니다.", Toast.LENGTH_LONG).show();
                ToEnd();
            }
        });


            //gps가 꺼져 있을시 설정 화면으로 이동
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                alertDialog.show();
            }

            //gps가 켜져 있을시 자동으로 메인화면으로 넘어감
            else { ToMain(); }

    }
    private void ToMain() //메인화면으로 넘어감
    {
        intent = new Intent(this, MenuChoice.class);
        startActivity(intent);
        finish();
    }
    private void ToEnd() //종료
    {
        exit(1);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) //설정화면에서 복귀시에 처리하는 함수
    {
        switch(requestCode){
            case 0:
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    Toast.makeText(LoadingActivity.this, "GPS와 인터넷이 연결되어 있어야 합니다.", Toast.LENGTH_LONG).show();
                    ToEnd();
                }
                else
                    ToMain();
                break;
        }
    }


}
