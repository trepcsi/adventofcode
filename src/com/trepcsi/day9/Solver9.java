package com.trepcsi.day9;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver9 {

    List<List<Integer>> table;
    List<List<Boolean>> marker;

    public Solver9(String fileName) {
        table = new ArrayList<>();
        marker = new ArrayList<>();
        List<Integer> helper = new ArrayList<>();
        for (int i = 0; i < 102; i++) {
            helper.add(10);
        }
        table.add(helper);
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                List<Integer> inputNumbers = new ArrayList<>();
                inputNumbers.add(10);
                for (Character c : line.toCharArray()) {
                    inputNumbers.add(Character.getNumericValue(c));
                }
                inputNumbers.add(10);
                table.add(inputNumbers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        table.add(helper);
    }

    public int solveA() {
        int sum = 0;
        for (int i = 1; i < table.size() - 1; i++) {
            for (int j = 1; j < table.get(0).size() - 1; j++) {
                List<Integer> connected = new ArrayList<>();
                connected.add(table.get(i - 1).get(j)); //N
                connected.add(table.get(i + 1).get(j)); //S
                connected.add(table.get(i).get(j - 1)); //W
                connected.add(table.get(i).get(j + 1)); //E
                int current = table.get(i).get(j);
                if (current < Collections.min(connected)) {
                    sum += current + 1;
                }
            }
        }

        return sum;
    }

    public int solveB() {
        fillMarker();

        List<Integer> results = new ArrayList<>();
        for (int i = 1; i < table.size() - 1; i++) {
            for (int j = 1; j < table.get(0).size() - 1; j++) {
                List<Integer> connected = new ArrayList<>();
                connected.add(table.get(i - 1).get(j)); //N
                connected.add(table.get(i + 1).get(j)); //S
                connected.add(table.get(i).get(j - 1)); //W
                connected.add(table.get(i).get(j + 1)); //E
                int current = table.get(i).get(j);
                if (current < Collections.min(connected)) {
                    int size = 0;
                    size += countConnected(i, j);
                    results.add(size);
                }
            }
        }
        return max3Multiplex(results);
    }

    private void fillMarker() {
        marker = new ArrayList<>();
        for (int i = 0; i < table.size(); i++) {
            List<Boolean> markerRow = new ArrayList<>();
            for (int j = 0; j < table.get(0).size(); j++) {
                markerRow.add(false);
            }
            marker.add(markerRow);
        }
    }

    private int max3Multiplex(List<Integer> list) {
        int result = 1;
        for(int i = 0; i < 3; i++){
            var max = Collections.max(list);
            result *= max;
            list.remove(max);
        }
        return result;
    }

    private int countConnected(int row, int column) {
        if (table.get(row).get(column) == 9 || table.get(row).get(column) == 10 || marker.get(row).get(column))
            return 0;
        marker.get(row).set(column, true);
        return 1 +
                countConnected(row - 1, column) +
                countConnected(row + 1, column) +
                countConnected(row, column - 1) +
                countConnected(row, column + 1);
    }

}
