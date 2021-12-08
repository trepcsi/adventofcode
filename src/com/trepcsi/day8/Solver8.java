package com.trepcsi.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solver8 {
    private List<List<String>> input;
    private List<List<String>> output;

    Map<Integer, List<Character>> map;

    public Solver8(String fileName) {
        input = new ArrayList<>();
        output = new ArrayList<>();
        map = new HashMap<>();
        Consumer<String> parseLine = this::fillData;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(parseLine);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public int solveA() {
        int sum = 0;
        for (int i = 0; i < output.size(); i++) {
            calculateMap(input.get(i), output.get(i));
            sum += Integer.parseInt(readOutput(output.get(i)));
        }
        return sum;
    }

    private void calculateMap(List<String> input, List<String> output) {
        map.clear();
        List<String> allData = new ArrayList<>();
        allData.addAll(input);
        allData.addAll(output);

        //1
        for (String s : allData) {
            if (s.length() == 2) map.put(1, s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        }
        //4
        for (String s : allData) {
            if (s.length() == 4) map.put(4, s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        }
        //7
        for (String s : allData) {
            if (s.length() == 3) map.put(7, s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        }
        //8
        for (String s : allData) {
            if (s.length() == 7) map.put(8, s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
        }
        //6,9
        for (String s : allData) {
            if (s.length() == 6) {
                List<Character> sChars = s.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
                if (sChars.containsAll(map.get(1))) {
                    if (sChars.containsAll(map.get(4))) {
                        map.put(9, sChars);
                    } else {
                        map.put(0, sChars);
                    }
                } else {
                    map.put(6, sChars);
                }
            }
        }
        //2,3,5
        for (String s : allData) {
            if (s.length() == 5) {
                List<Character> sChars = s.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
                if (sChars.containsAll(map.get(1))) {
                    map.put(3, sChars);
                } else {
                    //2,5
                    List<Character> help = new ArrayList<>(map.get(4));
                    help.removeAll(map.get(1)); //4-1
                    if (sChars.containsAll(help)) {
                        map.put(5, sChars);
                    } else {
                        map.put(2, sChars);
                    }
                }
            }
        }
    }

    private String readOutput(List<String> list) {
        String number = "";
        for (String s : list) {
            var a = mapValues(s);
            System.out.println("map " + s + " -> " + a);
            number += a;
        }
        System.out.println(number);
        return number;
    }

    private void fillData(String line) {
        List<String> input = new ArrayList<>();
        List<String> output = new ArrayList<>();
        String[] lineStrArray = line.split(" ");
        boolean outputMarkReached = false;
        for (String s : lineStrArray) {
            if (s.equals("|")) {
                outputMarkReached = true;
            } else {
                if (!outputMarkReached) {
                    input.add(s);
                } else {
                    output.add(s);
                }
            }
        }
        this.input.add(input);
        this.output.add(output);
    }

    public int mapValues(String code) {
        List<List<Character>> values = map.values().stream().toList();
        for (List<Character> value : values) {
            if (sameChars(value, code)) {
                for (Map.Entry<Integer, List<Character>> entry : map.entrySet()) {
                    if (entry.getValue().equals(value)) {
                        return entry.getKey();
                    }
                }
            }
        }
        return 0;
    }

    private boolean sameChars(List<Character> charList, String string) {
        if (charList.size() != string.length()) return false;
        List<Character> str2Chars = string.chars().mapToObj((i) -> (char) i).collect(Collectors.toList());
        return charList.containsAll(str2Chars) && str2Chars.containsAll(charList);
    }
}
