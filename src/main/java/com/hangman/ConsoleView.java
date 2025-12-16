package com.hangman;

public class ConsoleView {
    public void displayState(HangmanModel model) {
        System.out.println("\n" + "=".repeat(20));
        System.out.println("Word: " + model.getCurrentProgress());
        System.out.println("Attempts Left: " + model.getAttemptsRemaining());
        System.out.print("Enter guess: ");
    }

    public void displayMessage(String message) {
        System.out.println(">> " + message);
    }

    public void displayGameOver(HangmanModel model) {
        if (model.getStatus() == HangmanModel.GameStatus.WON) {
            System.out.println("Congratulations! You won!");
        } else {
            System.out.println("Game Over! The word was: " + model.getSecretWord());
        }
    }
}