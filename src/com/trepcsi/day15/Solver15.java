package com.trepcsi.day15;

import java.io.BufferedReader;
import java.io.FileReader;

public class Solver15 {

    private final int N = 100;

    private int[][] graph;
    private int[][] pathCosts;
    private int[][][] previous;
    private boolean[][] visited;

    public Solver15(String fileName) {

        var size = N;
        graph = new int[size][size];
        pathCosts = new int[size][size];
        visited = new boolean[size][size];
        previous = new int[size][size][2]; //x,y

        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            int j = 0;
            while ((line = reader.readLine()) != null) {
                char[] row = line.toCharArray();
                for (int i = 0; i < row.length; i++) {
                    graph[j][i] = Character.getNumericValue(row[i]);
                    pathCosts[j][i] = Integer.MAX_VALUE;  //max at the beginning
                    visited[j][i] = false;
                    previous[j][i][0] = -1;  //undefined cordX
                    previous[j][i][1] = -1;  //undefined cordY
                }
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int solveA() {
        dijkstra();
        return pathCosts[pathCosts.length - 1][pathCosts.length - 1];
    }

    public int solveB() {
        dijkstra();
        return pathCosts[pathCosts.length - 1][pathCosts.length - 1];
    }

    private void dijkstra() {
        pathCosts[0][0] = 0;
        while (thereIsNotVisited()) {
            int[] bestCord = visitBest();
            updatedNotVisitedNeighbours(bestCord[0], bestCord[1]);
        }
    }

    private void updatedNotVisitedNeighbours(int x, int y) {
        if (x != 0) {
            if (!visited[x - 1][y]) {
                int newCost = pathCosts[x][y] + graph[x - 1][y];
                if (newCost < pathCosts[x - 1][y]) {
                    pathCosts[x - 1][y] = newCost;
                    previous[x - 1][y][0] = x;
                    previous[x - 1][y][1] = y;
                }
            }
        }
        if (x != N-1) {
            if (!visited[x + 1][y]) {
                int newCost = pathCosts[x][y] + graph[x + 1][y];
                if (newCost < pathCosts[x + 1][y]) {
                    pathCosts[x + 1][y] = newCost;
                    previous[x + 1][y][0] = x;
                    previous[x + 1][y][1] = y;
                }
            }
        }
        if (y != 0) {
            if (!visited[x][y - 1]) {
                int newCost = pathCosts[x][y] + graph[x][y - 1];
                if (newCost < pathCosts[x][y - 1]) {
                    pathCosts[x][y - 1] = newCost;
                    previous[x][y - 1][0] = x;
                    previous[x][y - 1][1] = y;
                }
            }
        }
        if (y != N-1) {
            if (!visited[x][y + 1]) {
                int newCost = pathCosts[x][y] + graph[x][y + 1];
                if (newCost < pathCosts[x][y + 1]) {
                    pathCosts[x][y + 1] = newCost;
                    previous[x][y + 1][0] = x;
                    previous[x][y + 1][1] = y;
                }
            }
        }
    }

    private int[] visitBest() {
        int x = -1;
        int y = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < pathCosts.length; i++) {
            for (int j = 0; j < pathCosts[0].length; j++) {
                if (!visited[i][j]) {
                    if (pathCosts[i][j] < min) {
                        min = pathCosts[i][j];
                        x = i;
                        y = j;
                    }
                }
            }
        }
        visited[x][y] = true;
        return new int[]{x, y};
    }

    private boolean thereIsNotVisited() {
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
                if (!visited[i][j]) return true;
            }
        }
        return false;
    }

    private void print() {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                System.out.print(graph[i][j]);
            }
            System.out.print("\n");
        }
        System.out.println();
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[0].length; j++) {
                System.out.print(pathCosts[i][j] + "  ");
            }
            System.out.print("\n");
        }
    }
}
