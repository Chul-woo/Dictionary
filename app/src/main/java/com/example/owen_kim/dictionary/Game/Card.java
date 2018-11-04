package com.example.owen_kim.dictionary.Game;

import android.widget.Button;

import java.util.HashMap;

public class Card {
    // 카드 상태(m_state)
    public static final int CARD_SHOW = 0;
    public static final int CARD_CLOSE = 1;
    public static final int CARD_PLAYEROPEN = 2;
    public static final int CARD_MATCHED = 3;

//    public static final int IMG_BOOK = 1;
//    public static final double WORD_BOOK = 1.5;
//
//    public static final int IMG_CHAIR = 2;
//    public static final double WORD_CHAIR = 2.5;
//
//    public static final int IMG_LAPTOP = 3;
//    public static final double WORD_LAPTOP = 3.5;

    //새로운 시도
    public static final HashMap<String, Double> imageSets;
    static {
        imageSets = new HashMap<String, Double>();
        imageSets.put("image_apple",1.0);
        imageSets.put("image_bed", 2.0);
        imageSets.put("image_book", 3.0);
        imageSets.put("image_camera", 4.0);
        imageSets.put("image_chair", 5.0);
        imageSets.put("image_dog", 6.0);
        imageSets.put("image_laptop", 7.0);
        imageSets.put("image_person", 8.0);
        imageSets.put("image_phone", 9.0);
        imageSets.put("image_watch", 10.0);
    }
    public static final HashMap<String, Double> wordSets;
    static {
        wordSets = new HashMap<String, Double>();
        wordSets.put("word_apple",1.5);
        wordSets.put("word_bed", 2.5);
        wordSets.put("word_book", 3.5);
        wordSets.put("word_camera", 4.5);
        wordSets.put("word_chair", 5.5);
        wordSets.put("word_dog", 6.5);
        wordSets.put("word_laptop", 7.5);
        wordSets.put("word_person", 8.5);
        wordSets.put("word_phone", 9.5);
        wordSets.put("word_watch", 10.5);
    }

    // 카드 정보
    public int m_state;
    public double m_Color;

    public Card(double _color){
        m_state = CARD_CLOSE;
        m_Color = _color;

    }
}
