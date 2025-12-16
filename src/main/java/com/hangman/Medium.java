package com.hangman;

public class Medium extends Hangman {

    public Medium(Word word) {
        super("Medium", word);
    }

    @Override
    public void setAttempts() {
        attempts = 8;
    }
}
