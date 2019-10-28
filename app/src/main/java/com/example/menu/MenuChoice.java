package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuChoice extends Activity {

    Button soup;
    Button rice;
    Button noodle;
    Button bunsik;
    int choicemenu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuchoice);

        soup = (Button)findViewById(R.id.Soup);
        rice = (Button)findViewById(R.id.Rice);
        noodle = (Button)findViewById(R.id.Noodle);
        bunsik = (Button)findViewById(R.id.Bunsik);

        //사용자가 국을 골랐을 경우
        View.OnClickListener s = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                choicemenu = 1;
                Intent intent = new Intent(getApplicationContext(),Weather_GPS.class);
                intent.putExtra("usermenu",choicemenu);
                startActivity(intent);
                choicemenu=0;
                finish();
            }
        };
        rice.setOnClickListener(s);

        //사용자가 밥을 골랐을 경우
        View.OnClickListener r = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                choicemenu = 2;
                Intent intent = new Intent(getApplicationContext(),Weather_GPS.class);
                intent.putExtra("usermenu",choicemenu);
                startActivity(intent);
                choicemenu=0;
                finish();
            }
        };
        noodle.setOnClickListener(r);

        //사용자가 면을 골랐을 경우
        View.OnClickListener n = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                choicemenu = 3;
                Intent intent = new Intent(getApplicationContext(),Weather_GPS.class);
                intent.putExtra("usermenu",choicemenu);
                startActivity(intent);
                choicemenu=0;
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
                intent.putExtra("usermenu",choicemenu);
                startActivity(intent);
                choicemenu=0;
                finish();
            }
        };
        bunsik.setOnClickListener(b);


    }
}
