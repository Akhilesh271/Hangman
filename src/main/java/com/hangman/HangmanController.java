package com.hangman;

import java.util.Scanner;

// Connects User Input to Model
public class HangmanController {
    private HangmanModel model;
    private ConsoleView view;
    private Scanner scanner;

    public HangmanController(HangmanModel model, ConsoleView view) {
        this.model = model;
        this.view = view;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        while (model.getStatus() == HangmanModel.GameStatus.PLAYING) {
            view.displayState(model);

            String input = scanner.nextLine();

            // Basic input validation
            if (input.isEmpty()) {
                view.displayMessage("Exiting game...");
                return;
            }

            // Send data to Model
            boolean processed = model.processGuess(input.charAt(0));

            if (!processed) {
                view.displayMessage("Invalid or already guessed letter!");
            }
        }

        view.displayGameOver(model);
    }
}