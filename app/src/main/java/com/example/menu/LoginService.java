package com.example.menu;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {


    static final String loginUrl = "116.126.79.199/Menu_Service/";

    //로그인 서버로 전송
    @FormUrlEncoded
    @POST("LOGIN")
    Call<LoginResult> login(@Field("user_id") String id, @Field("user_pw") String pw);

    //회원가입 서버로 전송
    @FormUrlEncoded
    @POST("SIGNUP")
    Call<LoginResult> signup(@Field("id") String id, @Field("pw") String pw, @Field("name") String name, @Field("age") String age);

}
