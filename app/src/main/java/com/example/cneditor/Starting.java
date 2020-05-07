package com.example.cneditor;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class Starting extends AppCompatActivity {

    Timer time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        try{
            getSupportActionBar().hide();
        }catch (NullPointerException e){}
        time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {

                Intent i = new Intent(Starting.this,login.class);
                startActivity(i);
                finish();

            }
        },2000);
    }
}
