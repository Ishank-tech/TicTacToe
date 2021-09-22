package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View image = findViewById(R.id.logoImage);
        View logo = findViewById(R.id.logo_name);

        getSupportActionBar().hide();

        Thread thread =new Thread(){

            public void run(){

                try {
                   sleep(4000);
                }

                catch (Exception e){
                    e.printStackTrace();
                }

                finally {

                    Intent intent = new Intent(SplashActivity.this, Language.class);
                    startActivity(intent);
                    finish();




                }

            }

        };
        thread.start();
    }
}