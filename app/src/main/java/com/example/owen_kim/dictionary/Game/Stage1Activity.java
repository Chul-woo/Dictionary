package com.example.owen_kim.dictionary.Game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Stage1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CardGameView(this));
    }

}
