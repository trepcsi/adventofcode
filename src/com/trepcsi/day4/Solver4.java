package com.trepcsi.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver4 {

    List<Matrix> bingoTables;
    List<Integer> inputNumbers;

    public Solver4(String fileName) {
        bingoTables = new ArrayList<>();
        inputNumbers = new ArrayList<>();

        try {
            int i = 0;
            String line;
            List<String> tableNumbers = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                if (0 == i) {
                    String[] inputNumbersString = line.split(",");
                    for (String s : inputNumbersString) {
                        inputNumbers.add(Integer.parseInt(s));
                    }
                } else {
                    if (line.trim().length() == 0 && tableNumbers.size() != 0) {
                        tableNumbers.remove(0);
                        bingoTables.add(new Matrix(tableNumbers));
                        tableNumbers.clear();
                    }
                    String[] numbersInLine = line.trim().split("\\s+");
                    Collections.addAll(tableNumbers, numbersInLine);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int solve(int numberOfWinsNeeded) {
        int lastNumber = 0;
        int isWon = 0;
        int i = 0;
        int winnerPosition = -1;
        while (isWon != numberOfWinsNeeded) {
            if (i == inputNumbers.size()) break;
            int currentNumber = inputNumbers.get(i);
            int j = 0;
            for (Matrix m : bingoTables) {
                if (!m.won) {
                    isWon += m.markNumber(currentNumber);
                    winnerPosition = j;
                }
                if (isWon == numberOfWinsNeeded) break;
                j++;
            }
            lastNumber = currentNumber;
            i++;
        }
        return lastNumber * bingoTables.get(winnerPosition).sumOfUnmarked();
    }

    public int solveA() {
        for (Matrix m : bingoTables) {
            m.clear();
        }
        return solve(1);
    }

    public int solveB() {
        for (Matrix m : bingoTables) {
            m.clear();
        }
        return solve(bingoTables.size());
    }
}
