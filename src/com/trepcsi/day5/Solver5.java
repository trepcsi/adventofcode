package com.trepcsi.day5;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Solver5 {

    private List<int[]> lines;  //[x1,y1,x2,y2],[...],...

    public Solver5(String fileName) {
        lines = new ArrayList<>();
        Consumer<String> fillMoves = this::fillData;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(fillMoves);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public int solveA(){
        Field f = new Field(lines, false);
        return f.countOverlaps();
    }

    public int solveB(){
        Field f = new Field(lines, true);
        return f.countOverlaps();
    }

    private void fillData(String t) {
        int[] line = new int[4];
        String[] inputLineAsArray = t.replace(" -> ", ",").split(",");
        for (int i = 0; i < inputLineAsArray.length; i++) {
            line[i] = Integer.parseInt(inputLineAsArray[i]);
        }
        lines.add(line);
    }

}
