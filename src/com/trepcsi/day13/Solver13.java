package com.trepcsi.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Solver13 {
    Set<Dot> dotLists = new HashSet<>();
    List<Map<String, Integer>> foldLines = new ArrayList<>();

    public Solver13(String fileName) {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                fillData(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int solveA() {
        dotLists.forEach(dot -> dot.mirror(foldLines.get(0)));
        dotLists = new HashSet<>(dotLists);
        return dotLists.size();
    }

    public int solveB() {
        for (var foldLine : foldLines) {
            dotLists.forEach(dot -> dot.mirror(foldLine));
            dotLists = new HashSet<>(dotLists);
        }
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 50; i++) {
                if (dotLists.contains(new Dot(i, j))) {
                    System.out.print("K");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
        return dotLists.size();
    }

    private void print() {
        System.out.println(foldLines);
        System.out.println();
        dotLists.add(new Dot(0, 3));
        System.out.println(dotLists);
    }

    private void fillData(String line) {
        if (line.length() == 0) return;
        if (line.length() < 10) {
            dotLists.add(new Dot(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1])));
        } else {
            Map<String, Integer> m = new HashMap<>();
            m.put(line.split(" ")[2].split("=")[0], Integer.parseInt(line.split(" ")[2].split("=")[1]));
            foldLines.add(m);
        }

    }

    public static class Dot {
        private int x;
        private int y;

        public Dot(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private Dot mirror(Map<String, Integer> foldLine) {
            String key = new ArrayList<>(foldLine.keySet()).get(0);
            if (key.equals("x")) {
                this.x = foldLine.get(key) - Math.abs(this.x - foldLine.get(key));
            }
            if (key.equals("y")) {
                this.y = foldLine.get(key) - Math.abs(this.y - foldLine.get(key));
            }
            return this;
        }

        @Override
        public String toString() {
            return "Dot{" + x + "," + y + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dot dot = (Dot) o;
            return x == dot.x && y == dot.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
