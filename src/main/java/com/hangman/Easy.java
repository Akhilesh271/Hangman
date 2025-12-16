package com.hangman;

public class Easy extends Hangman {

    public Easy(Word word) {
        super("Easy", word);
    }

    @Override
    public void setAttempts() {
        attempts = 10;
    }

}
