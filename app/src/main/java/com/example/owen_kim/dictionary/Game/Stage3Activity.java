package com.example.owen_kim.dictionary.Game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Stage3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CardGameView(this,3));
    }
}
