package com.trepcsi.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver14 {

    private String polymer = "";
    private final Map<String, String> reactions = new HashMap<>();
    private HashMap<Character, Long> letterCounterMap = new HashMap<>();
    private char lastLetter = 'c';

    private HashMap<Integer, List<HashMap<String, HashMap<Character, Long>>>> cache = new HashMap<>();

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
        System.out.println(cache);
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
        HashMap<Character, Long> localLetterCounterMap = new HashMap<>(letterCounterMap);
        count(x, reactions.get(key).charAt(0), i);

        count(reactions.get(key).charAt(0), y, i);
        addToCache(localLetterCounterMap, letterCounterMap, i-1, key);

    }

    private void addToCache(HashMap<Character, Long> localLetterCounterMap, HashMap<Character, Long> letterCounterMap, int i, String sample) {
        HashMap<String, HashMap<Character, Long>> map = new HashMap<>();
        HashMap<Character, Long> charCounterMap = new HashMap<>();
        List<Character> keys = new ArrayList<>(letterCounterMap.keySet());
        for (var key : keys) {
            long charCounterDiff;

            if (!localLetterCounterMap.containsKey(key)) {
                charCounterDiff = letterCounterMap.get(key);
            } else {
                charCounterDiff = letterCounterMap.get(key) - localLetterCounterMap.get(key);
            }
            charCounterMap.put(key, charCounterDiff);
            map.put(sample, charCounterMap);
        }
        List<HashMap<String, HashMap<Character, Long>>> mapList;
        if (cache.containsKey(i)) {
            mapList = cache.get(i);
        } else {
            mapList = new ArrayList<>();
        }
        mapList.add(map);
        cache.put(i, mapList);
    }
}
