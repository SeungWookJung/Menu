package com.example.menu;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class LoginActivity extends Activity {

    EditText id;
    EditText pw;
    Button login;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        id = (EditText)findViewById(R.id.ID_input);
        pw = (EditText)findViewById(R.id.PW_input);
        login =(Button)findViewById(R.id.SIGNIN);
        signup = (Button)findViewById(R.id.SIGNUP);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        };
        login.setOnClickListener(listener);



    }

}
