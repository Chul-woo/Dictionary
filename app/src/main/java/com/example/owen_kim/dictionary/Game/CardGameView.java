package com.example.owen_kim.dictionary.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.owen_kim.dictionary.R;

public class CardGameView extends View {
    MediaPlayer m_Sound_BackGround; //배경 음악

    public static final int STATE_READY = 0; // 게임 시작 전 준비 상태
    public static final int STATE_GAME = 1; // 게임 중
    public static final int STATE_END = 2; // 게임 종료

    // 게임의 상태 값을 저장하는 멤버 변수
    private int m_state = STATE_READY; // 초깃값은 준비 상태

    Bitmap m_Card_Backside;
    Bitmap m_BackGroundImage;
    Bitmap m_Card_Red;
    Bitmap m_Card_Blue;
    Bitmap m_Card_Green;

    // 화면에 표시할 카드
    Card m_Shuffle[][];

    // 짝맞추기 비교를 위한 변수
    private Card m_SelectCard_1 = null; // 첫 번째로 선택한 카드
    private Card m_SelectCard_2 = null; // 두 번째로 선택한 카드

    public CardGameView(Context context) {
        super(context);
        //MediaPlayer를 이용해서 리소스 로드
        m_Sound_BackGround = MediaPlayer.create(context, R.raw.pororo);
        m_Sound_BackGround.start();

        //  키 입력을 위해 포커스를 줍니다.
        setFocusable(true);

        m_BackGroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.pororo);
        m_Card_Backside = BitmapFactory.decodeResource(getResources(), R.drawable.lion);
        m_Card_Red = BitmapFactory.decodeResource(getResources(), R.drawable.front_red);
        m_Card_Blue = BitmapFactory.decodeResource(getResources(), R.drawable.front_blue);
        m_Card_Green = BitmapFactory.decodeResource(getResources(), R.drawable.front_green);

        //화면에 표시할 카드만큼 할당받음(2X3)
        m_Shuffle = new Card[3][2];
        m_Shuffle[0][0] = new Card(Card.IMG_RED);

        // 카드 섞기
        SetCardShuffle();

        // 짝맞추기를 검사하는 스레드 실행
        CardGameThread _thread = new CardGameThread(this);
        _thread.start();
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
            //invalidate();
        }
        return super.onKeyDown(KeyCode, event);
    }

    @Override
    public void onDraw(Canvas canvas) {
        // 배경 이미지 그려주기
        canvas.drawBitmap(m_BackGroundImage, 0, 0, null);

        // 카드들을 그려주기
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 3; x++)
                // 카드의 앞면을 그려야 하는 경우
            if (m_Shuffle[x][y].m_state == Card.CARD_SHOW ||
                    m_Shuffle[x][y].m_state == Card.CARD_PLAYEROPEN ||
                    m_Shuffle[x][y].m_state == Card.CARD_MATCHED) {
                // 가지고 있는 색상 값에 따라 다른 이미지 그려주기
                if (m_Shuffle[x][y].m_Color == Card.IMG_RED)
                    canvas.drawBitmap(m_Card_Red, 170 + x * 250, 230 + y * 360, null);
                else if (m_Shuffle[x][y].m_Color == Card.IMG_GREEN)
                    canvas.drawBitmap(m_Card_Green, 170 + x * 250, 230 + y * 360, null);
                else if (m_Shuffle[x][y].m_Color == Card.IMG_BLUE)
                    canvas.drawBitmap(m_Card_Blue, 170 + x * 250, 230 + y * 360, null);
            }
            // 카드의 뒷면을 그려야 하는 경우
            else
                canvas.drawBitmap(m_Card_Backside, 170 + x * 250, 230 + y * 360, null);
        }
    }

    public void SetCardShuffle() {
        // 각각의 색을 가진 카드들을 생성
        m_Shuffle[0][0] = new Card(Card.IMG_RED);
        m_Shuffle[0][1] = new Card(Card.IMG_BLUE);
        m_Shuffle[1][0] = new Card(Card.IMG_GREEN);
        m_Shuffle[1][1] = new Card(Card.IMG_GREEN);
        m_Shuffle[2][0] = new Card(Card.IMG_BLUE);
        m_Shuffle[2][1] = new Card(Card.IMG_RED);
    }

    public void startGame() {
        // 모든 카드를 뒷면 상태로 만듭니다.
        m_Shuffle[0][0].m_state = Card.CARD_CLOSE;
        m_Shuffle[0][1].m_state = Card.CARD_CLOSE;
        m_Shuffle[1][0].m_state = Card.CARD_CLOSE;
        m_Shuffle[1][1].m_state = Card.CARD_CLOSE;
        m_Shuffle[2][0].m_state = Card.CARD_CLOSE;
        m_Shuffle[2][1].m_state = Card.CARD_CLOSE;

        // 화면을 갱신합니다.
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 게임 준비 중
        if (m_state == STATE_READY) {
            startGame(); //게임을 시작합니다.
            m_state = STATE_GAME;
        }
        // 게임 중
        else if (m_state == STATE_GAME) {
            // 비교하려고 두 개의 카드를 이미 뒤집은 경우
            if (m_SelectCard_1 != null && m_SelectCard_2 != null)
                return true;
            int px = (int) event.getX();
            int py = (int) event.getY();
            for (int y = 0; y < 2; y++) {
                for (int x = 0; x < 3; x++) {
                    //각 카드의 박스 값을 생성
                    Rect box_card = new Rect(170 + x * 250, 230 + y * 360,
                            170 + x * 250 + 80 , 230 + y * 360 + 115);
                    if (box_card.contains(px, py)) {
                        // (x,y)에 위치한 카드가 선택되었습니다.
                        if (m_Shuffle[x][y].m_state != Card.CARD_MATCHED)
                        // 맞춘 카드는 뒤집을 필요가 없습니다.
                        if (m_SelectCard_1 == null) { // 첫 카드를 뒤집으려는 것이라면
                            m_SelectCard_1 = m_Shuffle[x][y];
                            m_SelectCard_1.m_state = Card.CARD_PLAYEROPEN;
                        }
                        else {// 이미 첫 번째 카드가 뒤집혀 있으니 두 번째로 뒤집으려는 거라면
                            if (m_SelectCard_1 != m_Shuffle[x][y]) {
                                m_SelectCard_2 = m_Shuffle[x][y];
                                m_SelectCard_2.m_state = Card.CARD_PLAYEROPEN;
                            }
                        }
                    }
                }
            }
        }

        // 게임 종료
        else if (m_state == STATE_END) {
            // 게임 준비 상태로 변경
            m_state = STATE_READY;
        }

        // 화면을 갱신합니다.
        invalidate();
        return true;
    }

    public void checkMatch(){
        // 두 카드 중 하나라도 선택이 안 되었다면 비교할 필요가 없습니다.
        if(m_SelectCard_1 == null || m_SelectCard_2 == null)
            return;;
        // 두 카드의 색상을 비교합니다.
        if(m_SelectCard_1.m_Color == m_SelectCard_2.m_Color){
            // 두 카드의 색상이 같으면 두 카드를 맞춘 상태로 바꿉니다.
            m_SelectCard_1.m_state = Card.CARD_MATCHED;
            m_SelectCard_2.m_state = Card.CARD_MATCHED;
            // 다시 선택할 수 있게 null 값을 넣습니다.
            m_SelectCard_1 = null;
            m_SelectCard_2 = null;
        }
        else {
            // 두 카드의 색상이 다른 경우 대기 시간을 주어 결과를 확인하게 합니다.
            try{
                Thread.sleep(500);
            }catch (InterruptedException r){
            }
            // 두 카드를 이전처럼 뒷면으로 돌려줍니다.
            m_SelectCard_1.m_state = Card.CARD_CLOSE;
            m_SelectCard_2.m_state = Card.CARD_CLOSE;

            // 다시 선택할 수 있게 null 값을 넣습니다.
            m_SelectCard_1 = null;
            m_SelectCard_2 = null;
        }

        // 화면을 갱신합니다.
        postInvalidate();
    }
}
