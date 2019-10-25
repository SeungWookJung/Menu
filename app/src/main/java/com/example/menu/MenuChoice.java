package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuChoice extends AppCompatActivity {

    Button soup = (Button)findViewById(R.id.Soup);
    Button rice = (Button)findViewById(R.id.Rice);
    Button noodle = (Button)findViewById(R.id.Noodle);
    Button bunsik = (Button)findViewById(R.id.Bunsik);
    int choicemenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuchoice);

        //사용자가 국을 골랐을 경우
        View.OnClickListener s = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                choicemenu = 1;
                Intent intent = new Intent(getApplicationContext(),Weather_GPS.class);
                intent.putExtra("menu",choicemenu);
                startActivity(intent);
                finish();
            }
        };
        soup.setOnClickListener(s);

        //사용자가 밥을 골랐을 경우
        View.OnClickListener r = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                choicemenu = 2;
                Intent intent = new Intent(getApplicationContext(),Weather_GPS.class);
                intent.putExtra("menu",choicemenu);
                startActivity(intent);
                finish();
            }
        };
        soup.setOnClickListener(r);

        //사용자가 면을 골랐을 경우
        View.OnClickListener n = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                choicemenu = 3;
                Intent intent = new Intent(getApplicationContext(),Weather_GPS.class);
                intent.putExtra("menu",choicemenu);
                startActivity(intent);
                finish();
            }
        };
        soup.setOnClickListener(n);

        //사용자가 분식을 골랐을 경우
        View.OnClickListener b = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                choicemenu = 4;
                Intent intent = new Intent(getApplicationContext(),Weather_GPS.class);
                intent.putExtra("menu",choicemenu);
                startActivity(intent);
                finish();
            }
        };
        soup.setOnClickListener(b);


    }
}
