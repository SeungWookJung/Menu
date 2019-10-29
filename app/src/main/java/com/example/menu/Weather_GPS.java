package com.example.menu;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Random;

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
    int id = 0; //날씨의 아이디 값
    static String NowWeather; //현재 날씨 값
    Intent intent;
    int UserMenu; //유저가 고른 밥,국,면,분식 값

    //설문지의 국 메뉴
    private static String SoupMenu[]  = {"김치찌개","된장찌개","돼지국밥","마라탕","곱창전골","수제비","감자탕", "순두부찌개","해장국"};
    //설문지의 밥메뉴
    private static String RiceMenu[] = {"돼지국밥","두루치기"};
    //설문지의 면메뉴
    private static String NoodleMenu[] = {"칼국수","밀면","파스타","냉면","짬뽕","라면","우동"};
    //설문지의 분식 및 나머지 메뉴
    private static String BunsikMenu[] = {"김치전","떡볶이","파전","탕수육","치킨","햄버거"};

    //나쁜 날씨의 최종 메뉴 리스트
    ArrayList<String> BadWeatherMenu = new ArrayList<String>();
    //좋은날의 최종 메뉴 리스트
    ArrayList<String> GoodWeatherMenu = new ArrayList<String>();
    //랜덤 클래스
    Random rnd = new Random();

    //최종메뉴 선정 함수안 최종 메뉴
    String pickMenu = null;
    //최종 선정 메뉴
    static String menu;

    TextView choice;
    TextView weather;
    Button check;
    Button back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_menu);

        choice = (TextView)findViewById(R.id.choice);
        weather = (TextView)findViewById(R.id.weather);
        check = (Button)findViewById(R.id.ok);
        back = (Button)findViewById(R.id.cancel);

        //확인 버튼 클릭시
        View.OnClickListener o = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //현재 로그인된 유저 아이디를 preference 로부터 얻어옴
               String userId = SharedPreference.getAttribute(getApplicationContext(),"userId");

                // 맵 화면으로 진행
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("user_id",userId);
                startActivity(intent);
                finish();
            }
        };
        check.setOnClickListener(o);

        //취소버튼 클릭시
        View.OnClickListener x = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //유저 메뉴 선택 화면으로 진행
                Intent intent = new Intent(getApplicationContext(),MenuChoice.class);
                startActivity(intent);
                finish();
            }
        };
        back.setOnClickListener(x);

         intent = getIntent();
         UserMenu = intent.getIntExtra("usermenu",0);

         Lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( Weather_GPS.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 0 );
        }
        else{

            Lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, gpsLocationListener);
            Lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1, gpsLocationListener);

            Location location = Lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }

    }

    @Override
    public void onPause()
    {
        super.onPause();
        Lm.removeUpdates(gpsLocationListener);
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
                        id = Integer.parseInt(je.getAsJsonObject().get("id").getAsString());


                        getMenu(id,UserMenu); //메뉴를 최종선택 해주는 함수

                    /*intent = new Intent(getApplicationContext(),MapsActivity.class);
                    intent.putExtra("PickMenu",Menu);
                    startActivity(intent);*/
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

    private void getMenu(int id,int userMenu)
    {


        //랜덤값을 담을 변수
        int random =0;

        if(200<=id && id<300) //천둥 번개
        {
            NowWeather = "비";
        }
        else if(300<=id && id<400) //이슬비
        {
            NowWeather = "비";
        }
        else if(500<=id && id<600) //비
        {
            NowWeather = "비";
        }
        else if(600<=id && id<700) //눈
        {
            NowWeather = "눈";
        }
        else if(700<=id && id<800) //안개 미세먼지 등
        {
            NowWeather = "흐림";
        }
        else if(800<=id && id<900) //좋은 날씨
        {
            NowWeather = "좋음";
        }

        switch (userMenu)
        {
            case 1: //유저가 국을 선택 했을 때

                if(NowWeather == "비" || NowWeather == "흐림" || NowWeather == "눈")
                {
                    for(int i=0;i<SoupMenu.length;i++)
                    {
                        if(SoupMenu[i].matches(".*찌개.*") || SoupMenu[i].matches(".*국.*") || SoupMenu[i].matches(".*탕.*"))
                        {
                            BadWeatherMenu.add(SoupMenu[i]);
                            random = rnd.nextInt(BadWeatherMenu.size());
                            pickMenu = BadWeatherMenu.get(random);
                            random = 0;
                            break;
                        }

                    }
                }

                else if(NowWeather == "좋음")
                {
                    for(int i=0;i<SoupMenu.length;i++)
                    {
                        if(SoupMenu[i].matches(".*국.*") || SoupMenu[i].matches(".*탕.*"))
                        {
                            GoodWeatherMenu.add(SoupMenu[i]);
                            random = rnd.nextInt(GoodWeatherMenu.size());
                            pickMenu = GoodWeatherMenu.get(random);
                            random = 0;
                            break;
                        }

                    }
                }

            case 2: //유저가 밥을 선택했을 때

                if(NowWeather == "비" || NowWeather == "흐림" || NowWeather == "눈")
                {
                    for(int i=0;i<RiceMenu.length;i++)
                    {
                        if(RiceMenu[i].matches(".*찌개.*") || RiceMenu[i].matches(".*국.*") || RiceMenu[i].matches(".*탕.*"))
                        {
                            BadWeatherMenu.add(RiceMenu[i]);
                            random = rnd.nextInt(BadWeatherMenu.size());
                            pickMenu = BadWeatherMenu.get(random);
                            random = 0;
                            break;
                        }

                    }
                }

                else if(NowWeather == "좋음")
                {
                    for(int i=0;i<RiceMenu.length;i++)
                    {
                        if(RiceMenu[i].matches(".*국.*") || RiceMenu[i].matches(".*탕.*"))
                        {
                            GoodWeatherMenu.add(RiceMenu[i]);
                            random = rnd.nextInt(GoodWeatherMenu.size());
                            pickMenu = GoodWeatherMenu.get(random);
                            random = 0;
                            break;
                        }

                    }
                }

                case 3: //유저가 면을 선택한 경우

                    if(NowWeather == "비" || NowWeather == "흐림" || NowWeather == "눈")
                    {
                        for(int i=0;i<NoodleMenu.length;i++)
                        {
                            if(NoodleMenu[i].matches(".*찌개.*") || NoodleMenu[i].matches(".*국.*") || NoodleMenu[i].matches(".*탕.*"))
                            {
                                BadWeatherMenu.add(NoodleMenu[i]);
                                random = rnd.nextInt(BadWeatherMenu.size());
                                pickMenu = BadWeatherMenu.get(random);
                                random = 0;
                                break;
                            }

                        }
                    }

                    else if(NowWeather == "좋음")
                    {
                        for(int i=0;i<NoodleMenu.length;i++)
                        {
                            if(NoodleMenu[i].matches(".*국.*") || NoodleMenu[i].matches(".*탕.*"))
                            {
                                GoodWeatherMenu.add(NoodleMenu[i]);
                                random = rnd.nextInt(GoodWeatherMenu.size());
                                pickMenu = GoodWeatherMenu.get(random);
                                random =0;
                                break;
                            }

                        }
                    }

            case 4: //유저가 4분식을 골랐을 경우

                if(NowWeather == "비" || NowWeather == "흐림" || NowWeather == "눈")
                {
                    for(int i=0;i<BunsikMenu.length;i++)
                    {
                        if(BunsikMenu[i].matches(".*찌개.*") || BunsikMenu[i].matches(".*국.*") || BunsikMenu[i].matches(".*탕.*"))
                        {
                            BadWeatherMenu.add(BunsikMenu[i]);
                            random = rnd.nextInt(BadWeatherMenu.size());
                            pickMenu = BadWeatherMenu.get(random);
                            random = 0;
                            break;
                        }

                    }
                }

                else if(NowWeather == "좋음")
                {
                    for(int i=0;i<BunsikMenu.length;i++)
                    {
                        if(BunsikMenu[i].matches(".*국.*") || BunsikMenu[i].matches(".*탕.*"))
                        {
                            GoodWeatherMenu.add(BunsikMenu[i]);
                            random = rnd.nextInt(GoodWeatherMenu.size());
                            pickMenu = GoodWeatherMenu.get(random);
                            random = 0;
                            break;
                        }

                    }
                }

        }

        menu = pickMenu;

        choice.setText(menu);
        weather.setText(NowWeather);

        BadWeatherMenu.clear();
        GoodWeatherMenu.clear();
    }

}
