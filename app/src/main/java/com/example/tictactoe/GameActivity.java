package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class GameActivity extends AppCompatActivity {
    private AdView mAdView;

    boolean gameActive = true;

    int activePlayer = 0;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    public void PlayerTap(View view){


        ImageView img = (ImageView) view;

        int tappedImage = Integer.parseInt(img.getTag().toString());
        if (!gameActive) {
            gameReset(view);
        }
        if(gameState[tappedImage] == 2) {
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);
            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);
                status.setText("O's Turn - Tap To Play");
            }
            else {
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);
                status.setText("X's Turn - Tap To Play");
            }
            img.animate().translationYBy(1000f).setDuration(300);

        }

        for(int[] winPosition: winPositions){
            if (gameState[winPosition[0]] == gameState[winPosition[1]] && gameState[winPosition[1]] == gameState[winPosition[2]] && gameState[winPosition[0]] != 2){
                String winnerStr = null;
                gameActive = false;
                if(gameState[winPosition[0]] == 0){
                    winnerStr = "Yeyy!!! X has Won!";
                } else if(gameState[winPosition[0]] == 1){
                    winnerStr = "Yeyy!!! O has Won!";
                }

                TextView status = findViewById(R.id.status);
                status.setText(winnerStr);

            }
        }
        boolean emptySquare = false;
        for(int squareState: gameState){
            if(squareState ==2){
                emptySquare = true;
                break;
            }
        }
        if(!emptySquare && gameActive){
            gameActive = false;
            String winnerStr;
            winnerStr = "Draw,Try Again!!";
            TextView status = findViewById(R.id.status);
            status.setText(winnerStr);
        }
    }

    public void gameReset(View view){
        gameActive = true;
        activePlayer = 0;
        for(int i=0; i<gameState.length; i++){
            gameState[i] = 2;
        }
        ((ImageView)findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView8)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView9)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView10)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView11)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView12)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView13)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView14)).setImageResource(0);
        ((ImageView)findViewById(R.id.imageView15)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("X's Turn - Tap To Play");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {

            }
        });

    }
}