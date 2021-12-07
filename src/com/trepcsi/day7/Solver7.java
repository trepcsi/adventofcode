package com.trepcsi.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver7 {

    private final List<Integer> points;

    public Solver7(String fileName) {
        points = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            fillData(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int SolveA() {
        List<Integer> results = new ArrayList<>();

        for (int i = 0; i < Collections.max(points); i++) {
            int sum = 0;
            for (Integer point : points) {
                sum += moveCostA(point, i);
            }
            results.add(sum);
        }
        return Collections.min(results);
    }

    public int SolveB() {
        List<Integer> results = new ArrayList<>();

        for (int i = 0; i < Collections.max(points); i++) {
            int sum = 0;
            for (Integer point : points) {
                sum += moveCostB(point, i);
            }
            results.add(sum);
        }
        return Collections.min(results);
    }

    //TODO create a function which expects move-cost function parameter
    //like -> line 30/43  sum += this function(point, i)

    private int moveCostB(int start, int finish) {
        int length = Math.abs(finish - start);
        return length * (length + 1) / 2;
    }

    private int moveCostA(int start, int finish) {
        return Math.abs(start - finish);
    }

    private void fillData(String line) {
        String[] numbers = line.split(",");
        for (String number : numbers) {
            var n = Integer.parseInt(number);
            points.add(n);
        }
    }
}
