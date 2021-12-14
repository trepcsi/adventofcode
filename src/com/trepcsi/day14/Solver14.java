package com.trepcsi.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solver14 {

    private String polymer = "";
    private final Map<String, String> reactions = new HashMap<>();
    private Map<Character, Integer> letterOccurrenceNumber = new HashMap<>();
    private Map<Character, List<Integer>> letterTendency = new HashMap<>();

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

    public int solveA() {
        for (int j = 0; j < 10; j++) {
            String newPolymer = "";
            newPolymer += polymer.substring(0, 1);
            for (int i = 0; i < polymer.length() - 1; i++) {
                String key = polymer.substring(i, i + 2);
                if (reactions.containsKey(key)) {
                    newPolymer += reactions.get(key);
                }
                newPolymer += polymer.substring(i + 1, i + 2);
            }
            polymer = newPolymer;
            fillOccurency();
            System.out.println(letterOccurrenceNumber);
        }
        return getMaxOccurency() - getMinOccurency();
    }



    private void fillTendency() {
        List<Character> keys = letterOccurrenceNumber.keySet().stream().toList();
        for (Character key : keys) {
            if (letterTendency.containsKey(key)) {
                letterTendency.get(key).add(letterOccurrenceNumber.get(key));
            } else {
                List<Integer> value = new ArrayList<>();
                value.add(letterOccurrenceNumber.get(key));
                letterTendency.put(key, value);
            }
        }
    }

    private void initTendency() {
        letterTendency = new HashMap<>();
        List<Character> keys = letterOccurrenceNumber.keySet().stream().toList();
        for (Character key : keys) {
            List<Integer> value = new ArrayList<>();
            value.add(letterOccurrenceNumber.get(key));
            letterTendency.put(key, value);
        }
    }

    private int getMaxOccurency() {
        int max = 0;
        for (var key : letterOccurrenceNumber.keySet()) {
            if (letterOccurrenceNumber.get(key) > max) {
                max = letterOccurrenceNumber.get(key);
            }
        }
        return max;
    }

    private int getMinOccurency() {
        int min = 5000;
        for (var key : letterOccurrenceNumber.keySet()) {
            if (letterOccurrenceNumber.get(key) < min) {
                min = letterOccurrenceNumber.get(key);
            }
        }
        return min;
    }

    private void fillOccurency() {
        Set<Character> keys = new HashSet<>();
        for (int i = 0; i < polymer.length(); i++) {
            keys.add(polymer.charAt(i));
        }
        keys.forEach(k -> letterOccurrenceNumber.put(k, 0));
        for (int i = 0; i < polymer.length(); i++) {
            letterOccurrenceNumber.put(polymer.charAt(i), letterOccurrenceNumber.get(polymer.charAt(i)) + 1);
        }
    }

}
