package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText id;
    EditText pw;
    Button login;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        id = (EditText)findViewById(R.id.ID_input);
        pw = (EditText)findViewById(R.id.Password_input);
        login =(Button)findViewById(R.id.SignIn);
        signup = (Button)findViewById(R.id.SignUp);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Login_Server(id.getText().toString(),pw.getText().toString());
            }
        };
        login.setOnClickListener(listener);



    }

    private void  Login_Server(String id,String pw)
    {
        Retrofit retrofit =new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(LoginService.loginUrl)
                .build();
        LoginService loginService = retrofit.create(LoginService.class);

        loginService.login(id,pw).enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                LoginResult result = response.body();
                Log.d("1","통신성공");
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.d("2","연결실패");
            }
        });
    }
}
