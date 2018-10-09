package com.example.owen_kim.dictionary.Game;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.owen_kim.dictionary.R;

public class Stage1Activity extends AppCompatActivity {
    private TextView countdownText;
    private Button countdownButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMilliseconds = 3000; // 3초
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage1);

        countdownText= (TextView)findViewById(R.id.countdown_text);
        countdownButton = (Button)findViewById(R.id.countdown_button);

        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }

    public void start() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 500) {
            @Override
            public void onTick(long l) {
                timeLeftInMilliseconds = l;
                updateTimer();
            }
            @Override
            public void onFinish() {
            }
        }.start();
        timerRunning = true;
    }

    public void updateTimer() {
        int seconds = (int)timeLeftInMilliseconds / 1000;
        String timeLeftText = "";
        timeLeftText += seconds;
        countdownText.setText(timeLeftText);
    }

}
