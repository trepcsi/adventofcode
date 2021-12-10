package com.trepcsi.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Solver10 {

    public List<String> lines = new ArrayList<>();

    public Solver10(String fileName) {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int solveA() {
        int sum = 0;
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, simplify(lines.get(i)));
            String line = lines.get(i);
            if (corrupted(line)) {
                sum += getErrorPoint(line);
            }
        }
        return sum;
    }

    public Long solveB() {
        List<Long> finishScores = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, simplify(lines.get(i)));
            String line = lines.get(i);
            if (!corrupted(line)) {
                finishScores.add(calcScore(missingPart(line)));
            }
        }
        finishScores.sort(Long::compareTo);
        return finishScores.get(finishScores.size() / 2);
    }

    private Long calcScore(String missingPart) {
        long sum = 0;
        for (int i = 0; i < missingPart.length(); i++) {
            sum *= 5;
            sum += valueOf(missingPart.charAt(i));
        }
        return sum;
    }

    private int valueOf(char x) {
        return switch (x) {
            case ')' -> 1;
            case ']' -> 2;
            case '}' -> 3;
            case '>' -> 4;
            default -> 0;
        };
    }

    private String missingPart(String line) {
        String result = "";
        for (int i = 0; i < line.length(); i++) {
            result += getCloser(line.charAt(line.length() - 1 - i));
        }
        return result;
    }

    private String getCloser(char x) {
        return switch (x) {
            case '(' -> ")";
            case '{' -> "}";
            case '[' -> "]";
            case '<' -> ">";
            default -> "";
        };
    }

    private int getErrorPoint(String line) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ')') return 3;
            if (line.charAt(i) == ']') return 57;
            if (line.charAt(i) == '}') return 1197;
            if (line.charAt(i) == '>') return 25137;
        }
        return 0;
    }

    private boolean corrupted(String line) {
        return line.contains("}") || line.contains(")") || line.contains("]") || line.contains(">");
    }


    private String simplify(String line) {
        if (!isSimplifiable(line)) return line;
        line = removePair(line);
        return simplify(line);
    }

    private boolean isSimplifiable(String line) {
        for (int i = 0; i < line.length() - 1; i++) {
            if (isPair(line.charAt(i), line.charAt(i + 1)))
                return true;
        }
        return false;
    }

    private String removePair(String line) {
        for (int i = 0; i < line.length() - 1; i++) {
            if (isPair(line.charAt(i), line.charAt(i + 1))) {
                StringBuilder sb = new StringBuilder(line);
                sb.deleteCharAt(i).deleteCharAt(i);
                return sb.toString();
            }
        }
        return line;
    }

    private boolean isPair(char a, char b) {
        if (a == '(' && b == ')') return true;
        if (a == '[' && b == ']') return true;
        if (a == '{' && b == '}') return true;
        if (a == '<' && b == '>') return true;
        return false;
    }
}
