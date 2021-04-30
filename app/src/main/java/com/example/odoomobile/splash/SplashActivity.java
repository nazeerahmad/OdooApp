package com.example.odoomobile.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.odoomobile.LoginActivity;
import com.example.odoomobile.MainActivity;
import com.example.odoomobile.R;
import com.example.odoomobile.sharedPref.MyPreferences;
import com.example.odoomobile.sharedPref.PrefConf;


public class SplashActivity extends AppCompatActivity {
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        scheduleSplash();
    }

    private void scheduleSplash(){
        int SPLASH_TIME_OUT =4000;
        handler.postDelayed(this::openMainActivity,SPLASH_TIME_OUT);
    }
    private void openMainActivity(){
        if(MyPreferences.getInstance(this).getBoolean(PrefConf.KEY_IS_LOGGED_IN,false)){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}
