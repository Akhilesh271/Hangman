package com.hangman;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

// THE MODEL: Pure Data and Logic
public class HangmanModel {
    private String secretWord;
    private StringBuilder currentProgress; // The "___a_b_" string
    private int attemptsRemaining;
    private int maxAttempts;
    private Set<Character> guessedLetters;

    // Enum to represent the state of the game
    public enum GameStatus { PLAYING, WON, LOST }

    public HangmanModel(String word, int maxAttempts) {
        this.secretWord = word.toUpperCase();
        this.maxAttempts = maxAttempts;
        this.attemptsRemaining = maxAttempts;
        this.guessedLetters = new HashSet<>();
        this.currentProgress = new StringBuilder("_".repeat(word.length()));
    }

    // The Core Logic: Returns true if the guess was valid and new
    public boolean processGuess(char c) {
        char guess = Character.toUpperCase(c);

        // Validation: Is it a letter? Is it already guessed?
        if (!Character.isLetter(guess) || guessedLetters.contains(guess)) {
            return false;
        }

        guessedLetters.add(guess);

        if (secretWord.indexOf(guess) >= 0) {
            // Correct Guess: Update the progress string
            for (int i = 0; i < secretWord.length(); i++) {
                if (secretWord.charAt(i) == guess) {
                    currentProgress.setCharAt(i, guess);
                }
            }
        } else {
            // Incorrect Guess
            attemptsRemaining--;
        }
        return true;
    }

    public Set<Character> getWrongGuesses() {
        // Return all guessed letters that are NOT in the secret word
        Set<Character> wrong = new HashSet<>(guessedLetters);
        for(char c : secretWord.toCharArray()) {
            wrong.remove(c);
        }
        return wrong;
    }

    public GameStatus getStatus() {
        if (currentProgress.indexOf("_") == -1) return GameStatus.WON;
        if (attemptsRemaining <= 0) return GameStatus.LOST;
        return GameStatus.PLAYING;
    }

    // Getters for the View to display
    public String getCurrentProgress() { return currentProgress.toString(); }
    public int getAttemptsRemaining() { return attemptsRemaining; }
    public String getSecretWord() { return secretWord; }
}