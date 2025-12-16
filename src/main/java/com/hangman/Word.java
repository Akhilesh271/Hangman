package com.hangman;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Word {
    private HashMap<String, ArrayList<String>> wordList = new HashMap<>();
    private ArrayList<String> allWords = new ArrayList<>();

    public Word() {
        readWords();
    }

    private void readWords() {
        try {

            InputStream is = getClass().getResourceAsStream("/words.txt");

            if (is == null) {
                is = getClass().getResourceAsStream("/words");
            }

            if (is == null) {
                throw new RuntimeException("CRITICAL ERROR: Could not find '/words.txt' or '/words' in src/main/resources!");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length < 2) continue;

                String wordText = parts[0];
                String difficulty = parts[1];

                // Add to difficulty map
                wordList.putIfAbsent(difficulty, new ArrayList<>());
                wordList.get(difficulty).add(wordText);
                allWords.add(wordText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String selectWord(String difficulty) {
        ArrayList<String> list = wordList.get(difficulty);
        if (list == null || list.isEmpty()) return "DEFAULT"; // Fallback
        return list.get(new Random().nextInt(list.size()));
    }

    public List<String> getAllWords() {
        return allWords;
    }
}