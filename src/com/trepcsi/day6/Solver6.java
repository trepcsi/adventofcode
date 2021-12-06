package com.trepcsi.day6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Solver6 {

    Map<Integer, Long> fishNumberPerDay;
    String fileName;

    public Solver6(String fileName) {
        this.fileName=fileName;
    }

    private void simulateDaysPass(int days) {
        for (int day = 0; day < days; day++) {
            long numberOfSpawns = fishNumberPerDay.get(0);
            for (int i = 0; i < 8; i++) {
                fishNumberPerDay.remove(i);
                fishNumberPerDay.put(i, fishNumberPerDay.get(i + 1));
            }
            fishNumberPerDay.put(6, fishNumberPerDay.get(6) + numberOfSpawns);
            fishNumberPerDay.put(8, numberOfSpawns);
        }
    }

    private void fillData(String t) {
        String[] numbers = t.split(",");
        for (int i = 0; i < 9; i++) {
            fishNumberPerDay.put(i, 0L);
        }
        for (String number : numbers) {
            var key = Integer.parseInt(number);
            fishNumberPerDay.put(key, fishNumberPerDay.get(key) + 1);
        }
    }

    private void readFile() {
        fishNumberPerDay = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            fillData(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long solveA() {
        readFile();
        simulateDaysPass(80);
        return sumFishAllDays();
    }

    public long solveB() {
        readFile();
        simulateDaysPass(256);
        return sumFishAllDays();
    }

    private long sumFishAllDays() {
        return fishNumberPerDay.values().stream().reduce(0L, Long::sum);
    }
}
