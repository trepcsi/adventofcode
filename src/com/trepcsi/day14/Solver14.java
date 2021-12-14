package com.trepcsi.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Solver14 {

    private String polymer = "";
    private final Map<String, String> reactions = new HashMap<>();
    private HashMap<Character, Long> letterCounterMap = new HashMap<>();
    private char lastLetter = 'c';
    private HashMap<Integer, HashMap<String, HashMap<Character, Integer>>> cache = new HashMap<>();

    public Solver14(String fileName) {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (i == 0) {
                    polymer = line;
                } else if (i > 1) {
                    Map<String, String> m = new HashMap<>();
                    reactions.put(line.split(" ")[0], line.split(" ")[2]);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long solveA() {
        letterCounterMap.put(polymer.charAt(polymer.length() - 1), 1L);
        for (int i = 0; i < polymer.length() - 1; i++) {
            count(polymer.charAt(i), polymer.charAt(i + 1), 0);
        }
        return max() - min();
    }

    private long max() {
        return letterCounterMap.values().stream().max(Long::compare).get();
    }

    private long min() {
        return letterCounterMap.values().stream().min(Long::compare).get();
    }

    private void count(char x, char y, int i) {
        if (i == 10) {
            if (letterCounterMap.containsKey(x)) {

                letterCounterMap.put(x, letterCounterMap.get(x) + 1L);
            } else {
                letterCounterMap.put(x, 1L);
            }
            return;
        }
        String key = String.valueOf(x) + String.valueOf(y);
        if (reactions.get(key) == null) return;

        i = i + 1;

        count(x, reactions.get(key).charAt(0), i);
        count(reactions.get(key).charAt(0), y, i);

    }

}
