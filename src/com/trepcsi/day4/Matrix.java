package com.trepcsi.day4;

import java.util.List;

import static java.lang.Math.sqrt;

public class Matrix {

    int[][] table;
    int[][] marks;

    public boolean won = false;

    public Matrix(List<String> numbers) {
        int d = (int) sqrt(numbers.size());
        table = new int[d][d];
        marks = new int[d][d];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                table[i][j] = Integer.parseInt(numbers.get(j + (i * d)));
                marks[i][j] = 0;
            }
        }
    }

    public int markNumber(int currentNumber) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == currentNumber) {
                    marks[i][j] = 1;
                    if (solved()) {
                        won = true;
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    private boolean solved() {
        return solvedRows() || solvedColumns();
    }

    private boolean solvedColumns() {
        for (int i = 0; i < marks[0].length; i++) {
            int sum = 0;
            for (int j = 0; j < marks.length; j++) {
                sum += marks[j][i];
                if (sum == marks.length) return true;
            }
        }
        return false;
    }

    private boolean solvedRows() {
        for (int i = 0; i < marks.length; i++) {
            int sum = 0;
            for (int j = 0; j < marks[i].length; j++) {
                sum += marks[i][j];
                if (sum == marks.length) return true;
            }
        }
        return false;
    }

    public int sumOfUnmarked() {
        int sum = 0;
        for (int i = 0; i < marks.length; i++) {
            for (int j = 0; j < marks[i].length; j++) {
                if (marks[i][j] == 0) {
                    sum += table[i][j];
                }
            }
        }
        return sum;
    }

    public void clear() {
        won = false;
        for (int i = 0; i < marks.length; i++) {
            for (int j = 0; j < marks[0].length; j++) {
                marks[i][j] = 0;
            }
        }
    }
}
