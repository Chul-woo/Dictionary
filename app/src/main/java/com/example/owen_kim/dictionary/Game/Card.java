package com.example.owen_kim.dictionary.Game;

public class Card {
    // 색상 정의
    public static final int CARD_SHOW = 0;
    public static final int CARD_CLOSE = 1;
    public static final int CARD_PLAYEROPEN = 2;
    public static final int CARD_MATCHED = 3;

    public static final int IMG_BOOK = 1;
    public static final double WORD_BOOK = 1.5;

    public static final int IMG_CHAIR = 2;
    public static final double WORD_CHAIR = 2.5;

    public static final int IMG_LAPTOP = 3;
    public static final double WORD_LAPTOP = 3.5;

    // 카드 정보
    public int m_state;
    public double m_Color;

    public Card(double _color){
        m_state = CARD_CLOSE;
        m_Color = _color;
    }

}
