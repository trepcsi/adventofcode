package com.trepcsi.day11;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Solver11 {
    private List<List<Integer>> table;
    private List<List<Boolean>> flashHelper;
    private int flashCounter = 0;

    public Solver11(String fileName) {
        table = new ArrayList<>();
        flashHelper = new ArrayList<>();
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                List<Integer> inputNumbers = new ArrayList<>();
                List<Boolean> inputFlashed = new ArrayList<>();
                for (Character c : line.toCharArray()) {
                    inputNumbers.add(Character.getNumericValue(c));
                    inputFlashed.add(false);
                }
                table.add(inputNumbers);
                flashHelper.add(inputFlashed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int solveA() {
        simulateSteps();
        return flashCounter;
    }

    private void simulateSteps() {
        boolean isSynchronized = false;
        int i = 0;
        while (!isSynchronized) {
            i++;
            int oldFlashNumber = flashCounter;
            clearHelper();
            for (int row = 0; row < table.size(); row++) {
                for (int col = 0; col < table.get(0).size(); col++) {
                    if (table.get(row).get(col) != 9) {
                        if (!flashHelper.get(row).get(col)) {
                            table.get(row).set(col, table.get(row).get(col) + 1);
                        }
                    } else {
                        flash(row, col);
                    }
                }
            }
            if (flashCounter-oldFlashNumber==100){
                isSynchronized = true;
            }
        }
        System.out.println(i);
    }

    private void flash(int row, int col) {
        flashCounter++;
        table.get(row).set(col, 0);
        flashHelper.get(row).set(col, true);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(row + i < 0 || row + i > 9 || col + j < 0 || col + j > 9)) {
                    if (!(i == 0 && j == 0)) {
                        if (!flashHelper.get(row + i).get(col + j)) {
                            if (table.get(row + i).get(col + j) != 9) {
                                table.get(row + i).set(col + j, table.get(row + i).get(col + j) + 1);
                            } else {
                                flash(row + i, col + j);
                            }
                        }
                    }
                }
            }
        }
    }

    private void clearHelper() {
        for (List<Boolean> rows : flashHelper) {
            for (int j = 0; j < flashHelper.get(0).size(); j++) {
                rows.set(j, false);
            }
        }
    }

    private void print() {
        for (var row : table) {
            for (int col : row) {
                System.out.print(col + " ");
            }
            System.out.print("\n");
        }
    }
}
