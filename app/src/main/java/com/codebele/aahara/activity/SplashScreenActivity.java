package com.codebele.aahara.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.codebele.aahara.LoginActivity;
import com.codebele.aahara.R;

public class SplashScreenActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, LoginActivity .class);
                startActivity(i);
                finish();
//                if (sessionManager.checkLogin()) {
//                    Intent i = new Intent(SplashScreenActivity.this, OnBoardingActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(i);
//                    finish();
//                } else {
//                    Intent i = new Intent(SplashScreenActivity.this, HomeActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(i);
//                    finish();
//                }//
            }
        },2000);
    }
}
