package com.example.menu;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    //베이스 Url
    static final String BASEURL = "http://api.openweathermap.org/data/2.5/";
    static final String APIKEY ="7963811ba5621e777382116592d5584e";
    //get 메소드를 통한 http rest api 통신
    @GET("weather")
    Call<JsonObject> getWeahter (@Query("lat") double lat, @Query("lon") double lon, @Query("ApiKey") String ApiKey);

}
