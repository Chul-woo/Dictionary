package com.example.owen_kim.dictionary.Game;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.owen_kim.dictionary.R;

import static com.android.volley.Request.Method.HEAD;

public class Stage1Activity extends AppCompatActivity {
    private TextView countdownText;
    private Button countdownButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 3000; // 3초
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CardGameView(this));
    }

}
