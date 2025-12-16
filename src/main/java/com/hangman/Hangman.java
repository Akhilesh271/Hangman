package com.hangman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

    public abstract class Hangman implements HangmanGame {
        protected int attempts;
        protected String word;

        Scanner input = new Scanner(System.in);

        public Hangman(String difficulty, Word word) {
            this.word = word.selectWord(difficulty);
            setAttempts();
        }


        public void play() {
            StringBuilder sb = new StringBuilder();
            HashMap<Character, ArrayList<Integer>> indexes = new HashMap<>();

            for(int i = 0; i < word.length(); i++) {
                if(word.charAt(i) != ' ') {
                    sb.append("_");
                    indexes.putIfAbsent(word.toLowerCase().charAt(i), new ArrayList<>());
                    indexes.get(word.toLowerCase().charAt(i)).add(i);
                }
                else {
                    sb.append(" ");
                }
            }

            // Loops the game until it's over
            while(attempts > 0 && sb.indexOf("_") != -1) {
                String guess;
                char g;

                // Makes sure that the input is correct (A singular character)
                while (true) {
                    System.out.println("Attempts Left: " + attempts);
                    System.out.println("Your word: " + sb);
                    System.out.print("Enter your guess (Enter blank to quit): ");
                    guess = input.nextLine();


                    // Conditional statement to check correctness of input. Exits loop once confirmed
                    if (((guess.length() == 1) && Character.isLetter(guess.charAt(0))) || (guess.isEmpty())) {
                        break;
                    }

                    System.out.println("Invalid input: You can only guess one letter at a time. Try again.");
                }

                // Shuts down the program if the input is empty
                if (guess.isEmpty()) {
                    break;
                }

                g = guess.toLowerCase().charAt(0);

                // Checks to make sure that the player does not waste any attempts when trying an already guessed letter.
                if(indexes.containsKey(g)) {
                    if(sb.indexOf(guess.toLowerCase()) != -1 || sb.indexOf(guess.toUpperCase()) != -1){
                        System.out.println("You have already guessed "+ guess +". Enter a new value.");
                    }
                    else {
                        for (int c : indexes.get(g)) {
                            sb.setCharAt(c, word.charAt(c));
                        }
                        System.out.println("Correct!");
                    }
                }
                else {
                    System.out.println("Incorrect!");
                    attempts--;
                }
            }

            // Checks if there are any underscores left in the guessed word. If there's none, then every letter is guessed.
            if(sb.indexOf("_") == -1) {
                System.out.println("Congratulations! You have successfully guessed the word!");
            }
            else {
                System.out.println("Game Over. Better luck next time! (The word was '"+ word +"').");
            }
        }

        // Abstract method that sets the number of attempts depending on the difficulty
        public abstract void setAttempts();

    }

