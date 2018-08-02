package com.example.owen_kim.dictionary;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.owen_kim.dictionary.APIS.User;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    protected static final String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.CAMERA
            , Manifest.permission.INTERNET
            , Manifest.permission.ACCESS_NETWORK_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        //getSupportActionBar().hide();

        final User user = new User(LoadingActivity.this);
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if(user.getId() != ""){
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    intent.putExtra("user_name", user.getId());
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },2000);


            //checkPermission();
        /*
            Handler handler = new Handler() {
                public void handleMessage(Message msg) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            };
            handler.sendEmptyMessageDelayed(0, 3000);*/



    }

}
