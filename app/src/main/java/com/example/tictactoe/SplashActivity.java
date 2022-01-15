package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences Language;

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

                    Language = getSharedPreferences("Language",MODE_PRIVATE);
                    boolean isFirstTime = Language.getBoolean("firstTime",true);

                    if(isFirstTime){
                        SharedPreferences.Editor editor = Language.edit();
                        editor.putBoolean("firstTime",false);
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(),Language.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(SplashActivity.this , Login.class);
                        startActivity(intent);
                        finish();
                    }

                    /*Intent intent = new Intent(SplashActivity.this, Language.class);
                    startActivity(intent);
                    finish();*/

                }

            }

        };
        thread.start();
    }
}