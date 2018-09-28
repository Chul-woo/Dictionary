package com.example.owen_kim.dictionary.Game;

public class Card {
    // 색상 정의
    public static final int CARD_SHOW = 0;
    public static final int CARD_CLOSE = 1;
    public static final int CARD_PLAYEROPEN = 2;
    public static final int CARD_MATCHED = 3;
    public static final int IMG_RED = 1;
    public static final int IMG_GREEN = 2;
    public static final int IMG_BLUE = 3;

    // 카드 정보
    public int m_state;
    public int m_Color;

    public Card(int _Color){
        m_state = CARD_SHOW;
        m_Color = _Color;
    }

}
