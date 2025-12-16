package com.hangman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SolverAI {
    public char getBestGuess(String currentPattern, Set<Character> wrongGuesses, List<String> dictionary) {
        int targetLength = currentPattern.length();
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (String word : dictionary) {
            // Filter for word length
            if (word.length() != targetLength) continue;

            // Filter by known wrong guesses
            boolean containsWrong = false;
            for (char w : wrongGuesses) {
                if (word.toLowerCase().indexOf(Character.toLowerCase(w)) >= 0) {
                    containsWrong = true;
                    break;
                }
            }
            if (containsWrong) continue;

            // Filter by pattern matching
            boolean matchesPattern = true;
            for (int i = 0; i < targetLength; i++) {
                char patChar = currentPattern.charAt(i);
                if (patChar != '_' && Character.toLowerCase(patChar) != Character.toLowerCase(word.charAt(i))) {
                    matchesPattern = false;
                    break;
                }
            }
            if (!matchesPattern) continue;

            // Count frequencies in valid candidates
            for (char c : word.toCharArray()) {
                char lowerC = Character.toLowerCase(c);
                // Don't suggest guessed letters
                if (currentPattern.toLowerCase().indexOf(lowerC) == -1) {
                    frequencyMap.put(lowerC, frequencyMap.getOrDefault(lowerC, 0) + 1);
                }
            }
        }

        // Return the character with the highest count
        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse('?'); // Default to '?' if no valid candidates
    }
}
