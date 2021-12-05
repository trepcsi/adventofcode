package com.trepcsi.day5;

import java.util.List;

public class Field {

    private int[][] table;

    public Field(List<int[]> input) {
        table = new int[1000][1000];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = 0;
            }
        }
        drawLines(input);
    }

    private void drawLines(List<int[]> input) {
        for (int[] line : input) {
            //System.out.println("asd");
            //print();
            int x1 = line[0];
            int y1 = line[1];
            int x2 = line[2];
            int y2 = line[3];
            if (x1 == x2) {
                if(y1>y2){
                    int t = y1;
                    y1 = y2;
                    y2 = t;
                }
                for (int i = 0; i < 1000; i++){
                    for(int j = 0; j < 1000; j++){
                        if(i==x1){
                            if(y1<=j && j<=y2){
                                table[i][j] += 1;
                            }
                        }
                    }
                }
            }
            if (y1 == y2) {
                if(x1>x2){
                    int t = x1;
                    x1 = x2;
                    x2 = t;
                }
                for (int j = 0; j < 1000; j++){
                    for(int i = 0; i < 1000; i++){
                        if(j==y1){
                            if(x1<=i && i<=x2){
                                table[i][j] += 1;
                            }
                        }
                    }
                }
            }
        }
    }

    public int countOverlaps() {
        int sum = 0;
        for(int i = 0; i < table.length; i++){
            for( int j = 0; j < table[0].length; j++){
                if(table[i][j]>=2){
                    sum++;
                }
            }
        }
        return sum;
    }
}
