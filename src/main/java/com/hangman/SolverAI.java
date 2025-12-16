package com.hangman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SolverAI {

    public char getBestGuess(String currentPattern, Set<Character> wrongGuesses, List<String> dictionary) {
        // pattern looks like "_ P P L _"
        int targetLength = currentPattern.length();
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (String word : dictionary) {
            // 1. Filter: Must match length
            if (word.length() != targetLength) continue;

            // 2. Filter: Must not contain known wrong letters
            boolean containsWrong = false;
            for (char w : wrongGuesses) {
                if (word.toLowerCase().indexOf(Character.toLowerCase(w)) >= 0) {
                    containsWrong = true;
                    break;
                }
            }
            if (containsWrong) continue;

            // 3. Filter: Must match current pattern (e.g., 'A' at index 0)
            boolean matchesPattern = true;
            for (int i = 0; i < targetLength; i++) {
                char patChar = currentPattern.charAt(i);
                if (patChar != '_' && Character.toLowerCase(patChar) != Character.toLowerCase(word.charAt(i))) {
                    matchesPattern = false;
                    break;
                }
            }
            if (!matchesPattern) continue;

            // 4. Count frequencies in valid candidates
            for (char c : word.toCharArray()) {
                char lowerC = Character.toLowerCase(c);
                // Don't suggest letters we already guessed (either right or wrong)
                if (currentPattern.toLowerCase().indexOf(lowerC) == -1) {
                    frequencyMap.put(lowerC, frequencyMap.getOrDefault(lowerC, 0) + 1);
                }
            }
        }

        // Return the character with the highest count
        return frequencyMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse('?'); // '?' means no good guess found
    }
}
