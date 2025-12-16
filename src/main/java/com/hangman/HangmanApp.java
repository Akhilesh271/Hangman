package com.hangman;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HangmanApp extends Application {

    private HangmanModel model;
    private SolverAI ai;
    private Word wordBank;

    private Canvas canvas;
    private Label wordLabel;
    private Label statusLabel;
    private Label hintLabel;

    @Override
    public void start(Stage primaryStage) {
        wordBank = new Word();
        ai = new SolverAI();
        // Start a Hard game (Change to variable selected difficulty later)
        model = new HangmanModel(wordBank.selectWord("Hard"), 6);

        // UI
        canvas = new Canvas(300, 250);
        wordLabel = new Label(formatWord(model.getCurrentProgress()));
        wordLabel.setFont(new Font("Courier New", 30)); // Monospace for alignment

        statusLabel = new Label("Attempts Left: " + model.getAttemptsRemaining());
        hintLabel = new Label("Need a hint?");
        hintLabel.setTextFill(Color.BLUE);

        TextField inputField = new TextField();
        inputField.setMaxWidth(50);

        Button guessButton = new Button("Guess");
        guessButton.setOnAction(e -> handleGuess(inputField));

        Button aiButton = new Button("Ask AI for Hint");
        aiButton.setOnAction(e -> {
            char suggestion = ai.getBestGuess(
                    model.getCurrentProgress(),
                    model.getWrongGuesses(),
                    wordBank.getAllWords()
            );
            hintLabel.setText("AI Suggests: " + Character.toUpperCase(suggestion));
        });

        // Layout
        VBox centerLayout = new VBox(15);
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.getChildren().addAll(canvas, wordLabel, statusLabel, inputField, guessButton, aiButton, hintLabel);

        BorderPane root = new BorderPane();
        root.setCenter(centerLayout);

        Scene scene = new Scene(root, 500, 600);
        primaryStage.setTitle("Smart Hangman (Maven + AI)");
        primaryStage.setScene(scene);
        primaryStage.show();

        drawGallows(canvas.getGraphicsContext2D());
    }

    private void handleGuess(TextField input) {
        String text = input.getText();
        if (text.isEmpty()) return;

        char guess = text.charAt(0);
        boolean success = model.processGuess(guess);

        // Update UI
        input.clear();
        wordLabel.setText(formatWord(model.getCurrentProgress()));
        statusLabel.setText("Attempts Left: " + model.getAttemptsRemaining());

        drawBodyPart(model.getAttemptsRemaining());

        if (model.getStatus() == HangmanModel.GameStatus.WON) {
            statusLabel.setText("YOU WON!");
            statusLabel.setTextFill(Color.GREEN);
            input.setDisable(true);
        } else if (model.getStatus() == HangmanModel.GameStatus.LOST) {
            statusLabel.setText("GAME OVER: " + model.getSecretWord());
            statusLabel.setTextFill(Color.RED);
            input.setDisable(true);
        }
    }

    private String formatWord(String word) {
        return word.replace("", " ").trim();
    }

    private void drawGallows(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeLine(50, 230, 250, 230); // Base
        gc.strokeLine(100, 230, 100, 50); // Pole
        gc.strokeLine(100, 50, 200, 50);  // Top
        gc.strokeLine(200, 50, 200, 80);  // Rope
    }

    private void drawBodyPart(int attemptsLeft) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        // Assuming max 6 attempts
        switch (attemptsLeft) {
            case 5: gc.strokeOval(180, 80, 40, 40); break; // Head
            case 4: gc.strokeLine(200, 120, 200, 190); break; // Body
            case 3: gc.strokeLine(200, 130, 170, 160); break; // L Arm
            case 2: gc.strokeLine(200, 130, 230, 160); break; // R Arm
            case 1: gc.strokeLine(200, 190, 170, 230); break; // L Leg
            case 0: gc.strokeLine(200, 190, 230, 230); break; // R Leg
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}