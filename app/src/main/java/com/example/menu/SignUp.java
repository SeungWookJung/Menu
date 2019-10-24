package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class SignUp extends AppCompatActivity {

    TextView id = (TextView)findViewById(R.id.signup_inputID);
    TextView pw = (TextView)findViewById(R.id.signup_inputPW);
    TextView age = (TextView)findViewById(R.id.signup_inputAGE);
    TextView name = (TextView)findViewById(R.id.signup_inputNAME);
    Button check = (Button)findViewById(R.id.check);
    Button cancel = (Button)findViewById(R.id.cancel);
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        View.OnClickListener ok = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Signup su = new Signup();
                try {
                    result = su.execute(id.getText().toString(), pw.getText().toString(), age.getText().toString(), name.getText().toString()).get();
                    if(result.equals("1"))
                    {
                        Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        su.cancel(true);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"회원가입 실패",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        check.setOnClickListener(ok);

        View.OnClickListener no = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        cancel.setOnClickListener(no);
    }

    class Signup extends AsyncTask<String,Void,String> {

        public String result;
        @Override
        protected String doInBackground(String... params) {
            String  sendMsg;

            try {

                String url = "http://116.126.79.199:8081/Menu_Service/Signup.jsp";
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
                sendMsg = "id="+params[0]+"&pwd="+params[1]+"&age="+params[2]+"&name="+params[3];

                osw.write(sendMsg);
                osw.flush();

                if(conn.getResponseCode() == conn.HTTP_OK)
                {
                    result = "1";
                }
                else {
                        result = "0";
                        Log.d("결과 : ","에러입니다.");
                    }
                osw.close();
                conn.disconnect();
            }
            catch (Exception e) { e.printStackTrace(); }

            return result;

        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
        }
        @Override
        protected void onCancelled()
        {
            super.onCancelled();
        }

    }

}
