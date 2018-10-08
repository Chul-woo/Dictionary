package com.example.owen_kim.dictionary.Game;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.View;

import com.example.owen_kim.dictionary.R;

public class SoundView extends View {

    MediaPlayer m_Sound_BackGround; //배경 음악


    public SoundView(Context context) {
        super(context);

        //MediaPlayer를 이용해서 리소스 로드
        m_Sound_BackGround = MediaPlayer.create(context, R.raw.pororo);

        m_Sound_BackGround.start();

        //  키 입력을 위해 포커스를 줍니다.
        setFocusable(true);
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        // 스페이스 바를 눌렀을 때 배경음 일시 정지/다시 재생
        if(KeyCode == KeyEvent.KEYCODE_SPACE){
            if (m_Sound_BackGround.isPlaying())
                m_Sound_BackGround.pause();
            else
                m_Sound_BackGround.start();

            // 화면을 갱신시킵니다.
            invalidate();
        }
        return super.onKeyDown(KeyCode, event);
    }
}