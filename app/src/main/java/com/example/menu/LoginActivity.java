package com.example.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends Activity {

    EditText id;
    EditText pw;
    Button login;
    Button signup;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        id = (EditText)findViewById(R.id.ID_input);
        pw = (EditText)findViewById(R.id.PW_input);
        login =(Button)findViewById(R.id.SIGNIN);
        signup = (Button)findViewById(R.id.SIGNUP);



        View.OnClickListener LOGIN = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {
                    Login login1 = new Login();
                    result = login1.execute(id.getText().toString(),pw.getText().toString()).get();
                    if(result.equals("1"))
                    {
                        Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_LONG).show();

                        //사용자정보 내장메모리에 저장
                        SharedPreference.setAttribute(getApplicationContext(), "userId", id.getText().toString());

                        //메뉴 골라주는 액티비티로 진행
                        Intent intent = new Intent(getApplicationContext(),Weather_GPS.class);
                        login1.cancel(true);

                        startActivity(intent);
                        finish();
                    }
                    else if(result.equals("0"))
                    {
                        Toast.makeText(getApplicationContext(),"비밀번호를 확인해 주세요",Toast.LENGTH_LONG).show();
                    }
                    else if(result.equals("2"))
                    {
                        Toast.makeText(getApplicationContext(),"아이디가 없습니다",Toast.LENGTH_LONG).show();
                    }

                    }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        login.setOnClickListener(LOGIN);


        View.OnClickListener SIGNUP = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //회원가입 화면으로 진행
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                finish();
            }
        };
        signup.setOnClickListener(SIGNUP);



    }


    class Login extends AsyncTask<String,Void,String> {

        private String result;

        @Override
        protected String doInBackground(String... params) {
            String  sendMsg,str;
            try {

                String url = "http://116.126.79.199:8081/Menu_Service/login.jsp";
                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestProperty("Accept-Charset", "UTF-8");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "id="+params[0]+"&pwd="+params[1];

                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK)
                {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(),"UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    while((str= reader.readLine()) != null)
                    {
                        buffer.append(str);
                    }

                    result = buffer.toString();
                    reader.close();
                    tmp.close();
                }
                else Log.d("결과 : ","에러입니다.");
                osw.close();
                conn.disconnect();
            }
            catch (Exception e) { e.printStackTrace(); }


            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
        @Override
        protected void onCancelled()
        {
            super.onCancelled();
        }

    }



}
