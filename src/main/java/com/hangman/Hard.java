package com.hangman;

public class Hard extends Hangman {
    public Hard(Word word) {
        super("Hard", word);
    }

    @Override
    public void setAttempts() {
        attempts = 6;
    }
}
