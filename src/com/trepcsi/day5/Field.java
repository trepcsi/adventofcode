package com.trepcsi.day5;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private int[][] table;

    public Field(List<int[]> input, boolean diagonalsNeeded) {
        table = new int[1000][1000];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = 0;
            }
        }
        drawLines(input, diagonalsNeeded);
    }

    private void drawLines(List<int[]> input, boolean diagonalsNeeded) {
        for (int[] line : input) {
            var neededDots = calculateDots(line, diagonalsNeeded);
            for (int i = 0; i < neededDots.get(0).size(); i++) {
                table[neededDots.get(0).get(i)][neededDots.get(1).get(i)] +=1;
            }
        }
    }

    private List<List<Integer>> calculateDots(int[] input, boolean diagonalsNeeded) {
        int x1 = input[0];
        int y1 = input[1];
        int x2 = input[2];
        int y2 = input[3];

        List<Integer> xs = new ArrayList<>();
        List<Integer> ys = new ArrayList<>();

        if (x1 == x2) {
            if (y1 > y2) {
                int t = y1;
                y1 = y2;
                y2 = t;
            }
            for (int i = y1; i <= y2; i++) {
                xs.add(x1);
                ys.add(i);
            }
        } else if (y1 == y2) {
            if (x1 > x2) {
                int t = x1;
                x1 = x2;
                x2 = t;
            }
            for (int i = x1; i <= x2; i++) {
                xs.add(i);
                ys.add(y1);
            }
        } else if (diagonalsNeeded && Math.abs(x2 - x1) == Math.abs(y2 - y1)) {
            if (x2 > x1 && y2 > y1) {
                for (int i = 0; i <= Math.abs(x2 - x1); i++) {
                    xs.add(x1 + i);
                    ys.add(y1 + i);
                }
            }
            if (x2 > x1 && y2 < y1) {
                for (int i = 0; i <= Math.abs(x2 - x1); i++) {
                    xs.add(x1 + i);
                    ys.add(y1 - i);
                }
            }
            if (x2 < x1 && y2 > y1) {
                for (int i = 0; i <= Math.abs(x2 - x1); i++) {
                    xs.add(x1 - i);
                    ys.add(y1 + i);
                }
            }
            if (x2 < x1 && y2 < y1) {
                for (int i = 0; i <= Math.abs(x2 - x1); i++) {
                    xs.add(x1 - i);
                    ys.add(y1 - i);
                }
            }
        }
        List<List<Integer>> results = new ArrayList<>();
        results.add(xs);
        results.add(ys);
        return results;
    }

    public int countOverlaps() {
        int sum = 0;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if (table[i][j] >= 2) {
                    sum++;
                }
            }
        }
        return sum;
    }

}
