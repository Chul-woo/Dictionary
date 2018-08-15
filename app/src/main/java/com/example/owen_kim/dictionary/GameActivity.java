package com.example.owen_kim.dictionary;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.owen_kim.dictionary.APIS.User;
import com.example.owen_kim.dictionary.Game.Stage1Activity;
import com.example.owen_kim.dictionary.Game.Stage2Activity;
import com.example.owen_kim.dictionary.Game.Stage3Activity;


public class GameActivity extends AppCompatActivity {
    private Button bt_stage1;
    private Button bt_stage2;
    private Button bt_stage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        bt_stage1 = (Button)findViewById(R.id.stage1);
        bt_stage2 = (Button)findViewById(R.id.stage2);
        bt_stage3 = (Button)findViewById(R.id.stage3);

        //로그인 정보 받아오기
        final TextView nameText = (TextView) findViewById(R.id.nameText);

        User user = new User(GameActivity.this);
        final Intent intent = getIntent();
        final String user_id = intent.getStringExtra("user_id");

        //stage1 액티비티 이동
        bt_stage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, Stage1Activity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }

        });

        //stage2 액티비티 이동
        bt_stage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, Stage2Activity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        //stage3 액티비티 이동
        bt_stage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, Stage3Activity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });
    }
}
