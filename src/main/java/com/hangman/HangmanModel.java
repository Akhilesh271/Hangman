package com.hangman;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

// Model
public class HangmanModel {
    private String secretWord;
    private StringBuilder currentProgress;
    private int attemptsRemaining;
    private int maxAttempts;
    private Set<Character> guessedLetters;

    public enum GameStatus { PLAYING, WON, LOST }

    public HangmanModel(String word, int maxAttempts) {
        this.secretWord = word.toUpperCase();
        this.maxAttempts = maxAttempts;
        this.attemptsRemaining = maxAttempts;
        this.guessedLetters = new HashSet<>();
        this.currentProgress = new StringBuilder("_".repeat(word.length()));
    }

    // Returns true if the guess was valid and new
    public boolean processGuess(char c) {
        char guess = Character.toUpperCase(c);

        // Validation
        if (!Character.isLetter(guess) || guessedLetters.contains(guess)) {
            return false;
        }

        guessedLetters.add(guess);

        if (secretWord.indexOf(guess) >= 0) {
            // Update the progress string for correct guesses
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
        // Return all guessed letters that are not in the secret word
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

    // Getters
    public String getCurrentProgress() { return currentProgress.toString(); }
    public int getAttemptsRemaining() { return attemptsRemaining; }
    public String getSecretWord() { return secretWord; }
}