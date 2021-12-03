package com.trepcsi.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Solver2 {

    private final Map<String, List<Integer>> moves;         //-> {up->1,6,4,3},{forward->5,4,3}
    private final List<Map<String, Integer>> movesWithAim;  //-> {up->1},{up->6},..,{forward->5}

    public Solver2(String fileName) {
        moves = new HashMap<>();
        movesWithAim = new ArrayList<>();
        Consumer<String> fillMoves = t -> {
            addMove(t.split(" ")[0], Integer.parseInt(t.split(" ")[1]));
            addMoveWithAim(t.split(" ")[0], Integer.parseInt(t.split(" ")[1]));
        };
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(fillMoves);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public int solveA() {
        int x = moves.get("forward").stream().reduce(0, Integer::sum);
        int y = moves.get("down").stream().reduce(0, Integer::sum) - moves.get("up").stream().reduce(0, Integer::sum);
        return x * y;
    }

    public int solveB() {
        int x = 0;
        int y = 0;
        int aim = 0;
        for (Map<String, Integer> move : movesWithAim) {
            if (move.containsKey("up")) {
                aim -= move.get("up");
            } else if (move.containsKey("down")) {
                aim += move.get("down");
            } else {
                x += move.get("forward");
                y += aim * move.get("forward");
            }
        }
        return x * y;
    }

    private void addMove(String key, Integer value) {
        if (moves.containsKey(key)) {
            moves.get(key).add(value);
            return;
        }
        var valueList = new ArrayList<Integer>();
        valueList.add(value);
        moves.put(key, valueList);
    }

    private void addMoveWithAim(String key, Integer value) {
        Map<String, Integer> map = new HashMap<>();
        map.put(key, value);
        movesWithAim.add(map);
    }
}
