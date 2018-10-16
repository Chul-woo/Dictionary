package com.example.owen_kim.dictionary.Game;

public class CardGameThread extends Thread {
    CardGameView m_View;

    public CardGameThread( CardGameView _view){
        m_View = _view;
    }

    public void run(){
        while(true) {
            m_View.checkMatch();
        }
    }
}
