package com.trepcsi.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Solver3 {

    private final List<int[]> data;

    public Solver3(String fileName) {
        data = new ArrayList<>();
        Consumer<String> fillMoves = t -> fillData(t);
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(fillMoves);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public int solveA() {
        String gamma = "";
        String epsilon = "";
        int length = data.get(0).length;

        for (int i = 0; i < length; i++) {
            int numberOfOnes = 0;
            for (int[] number : data) {
                numberOfOnes += number[i];
            }
            if (numberOfOnes * 2 >= data.size()) {
                gamma += "1";
                epsilon += "0";
            } else {
                gamma += "0";
                epsilon += "1";
            }
        }
        return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2);
    }

    public int solveB() {
        List<int[]> generatorRatingList = new ArrayList<>(data);
        List<int[]> scrubberRatingList = new ArrayList<>(data);

        int length = data.get(0).length;
        for (int column = 0; column < length; column++) {
            generatorRatingList = keepTheRelevantOnes(generatorRatingList, countOnes(generatorRatingList, column), column, 1);
            if (generatorRatingList.size() == 1) break;
        }
        for (int column = 0; column < length; column++) {
            scrubberRatingList = keepTheRelevantOnes(scrubberRatingList, countOnes(scrubberRatingList, column), column, 0);
            if (scrubberRatingList.size() == 1) break;
        }

        String generatorString = "";
        String scrubberString = "";
        for (int i = 0; i < length; i++) {
            generatorString += String.valueOf(generatorRatingList.get(0)[i]);
            scrubberString += String.valueOf(scrubberRatingList.get(0)[i]);
        }
        return Integer.parseInt(generatorString, 2) * Integer.parseInt(scrubberString, 2);
    }

    private int countOnes(List<int[]> ratingList, int column) {
        int numberOfOnes = 0;
        for (int[] number : ratingList) {
            numberOfOnes += number[column];
        }
        return numberOfOnes;
    }

    private List<int[]> keepTheRelevantOnes(List<int[]> ratingList, int numberOfOnes, int column, int keepOnes) {
        List<int[]> newRatingList = new ArrayList<>();
        for (int[] number : ratingList) {
            if (numberOfOnes * 2 >= ratingList.size()) {
                if (number[column] == keepOnes) {
                    newRatingList.add(number);
                }
            } else {
                if (number[column] == (1 - keepOnes)) {
                    newRatingList.add(number);
                }
            }
        }
        return newRatingList;
    }

    private void fillData(String line) {
        int[] digits = new int[line.length()];
        for (int i = 0; i < line.length(); i++) {
            digits[i] = Character.getNumericValue((line.charAt(i)));
        }
        data.add(digits);
    }
}
