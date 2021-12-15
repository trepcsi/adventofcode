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
    private final Map<Character, Long> letterCounterMap = new HashMap<>();

    private final Map<Integer, List<Map<String, Map<Character, Long>>>> cache = new HashMap<>();

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

    private void count(char x, char y, int i) {
        String reactionLeft = String.valueOf(x) + String.valueOf(y);
        if (i == 40) {
            if (letterCounterMap.containsKey(x)) {
                letterCounterMap.put(x, letterCounterMap.get(x) + 1L);
            } else {
                letterCounterMap.put(x, 1L);
            }
            return;
        }

        if (reactions.get(reactionLeft) == null) return;

        if (cache.containsKey(i)) {
            for (int reactionCaches = 0; reactionCaches < cache.get(i).size(); reactionCaches++) {
                if (cache.get(i).get(reactionCaches).containsKey(reactionLeft)) {
                    var keys = new ArrayList<>(cache.get(i).get(reactionCaches).get(reactionLeft).keySet());
                    for (var character : keys) {
                        letterCounterMap.put(character, letterCounterMap.get(character) + cache.get(i).get(reactionCaches).get(reactionLeft).get(character));
                    }
                    return;
                }
            }
        }
        i = i + 1;
        HashMap<Character, Long> localLetterCounterMap = new HashMap<>(letterCounterMap);
        count(x, reactions.get(reactionLeft).charAt(0), i);
        count(reactions.get(reactionLeft).charAt(0), y, i);
        addToCache(localLetterCounterMap, letterCounterMap, i - 1, reactionLeft);

    }

    private void addToCache(Map<Character, Long> localLetterCounterMap, Map<Character, Long> letterCounterMap, int i, String sample) {
        Map<String, Map<Character, Long>> reactionLeftCache = new HashMap<>();
        Map<Character, Long> charCounterMap = new HashMap<>();
        List<Character> keys = new ArrayList<>(letterCounterMap.keySet());
        for (var key : keys) {
            long charCounterDiff;
            if (!localLetterCounterMap.containsKey(key)) {
                charCounterDiff = letterCounterMap.get(key);
            } else {
                charCounterDiff = letterCounterMap.get(key) - localLetterCounterMap.get(key);
            }
            charCounterMap.put(key, charCounterDiff);
            reactionLeftCache.put(sample, charCounterMap);
        }
        List<Map<String, Map<Character, Long>>> mapList;
        if (cache.containsKey(i)) {
            mapList = cache.get(i);
        } else {
            mapList = new ArrayList<>();
        }
        mapList.add(reactionLeftCache);
        cache.put(i, mapList);
    }

    private long max() {
        return letterCounterMap.values().stream().max(Long::compare).get();
    }

    private long min() {
        return letterCounterMap.values().stream().min(Long::compare).get();
    }
}
