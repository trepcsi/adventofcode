package com.trepcsi.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Solver1 {

    private final List<Integer> measurements;
    private final List<Integer> windowedMeasurements;

    public Solver1(String fileName) {
        measurements = new ArrayList<>();
        windowedMeasurements = new ArrayList<>();

        Consumer<String> addConsumer = t -> measurements.add(Integer.parseInt(t));
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(addConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < measurements.size() - 2; i++) {
            windowedMeasurements.add(measurements.get(i) + measurements.get(i + 1) + measurements.get(i + 2));
        }
    }

    public int solveA() {
        int result = 0;
        for (int i = 1; i < measurements.size(); i++) {
            if (measurements.get(i) > measurements.get(i - 1)) {
                result++;
            }
        }
        return result;
    }

    private int solveA(List<Integer> measurements) {
        int result = 0;
        for (int i = 1; i < measurements.size(); i++) {
            if (measurements.get(i) > measurements.get(i - 1)) {
                result++;
            }
        }
        return result;
    }

    public int solveB() {
        return solveA(windowedMeasurements);
    }

}
