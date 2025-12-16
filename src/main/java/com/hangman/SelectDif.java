package com.hangman;

import java.util.Scanner;

public class SelectDif {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        HangmanGame game;
        Word word = new Word();
//        word.readWords();

        while(true) {
            System.out.println("Welcome to Hangman. Please select your difficulty (Enter 1, 2, or 3): \n1. Easy\n2. Medium\n3. Hard");
            System.out.println();
            int difficulty = input.nextInt();
            System.out.println();

            if(difficulty == 1) {
                game = new Easy(word);
                break;
            }
            else if(difficulty == 2) {
                game = new Medium(word);
                break;
            }
            else if(difficulty == 3) {
                game = new Hard(word);
                break;
            }
            else {
                System.out.println("Invalid input. Please select 1, 2, or 3.");
            }
        }
        game.play();
    }
}
