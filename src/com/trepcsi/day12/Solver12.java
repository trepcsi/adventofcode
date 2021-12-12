package com.trepcsi.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Solver12 {

    private Map<String, List<String>> validPathsFromNode = new HashMap<>();
    private int counter = 0;

    public Solver12(String fileName) {
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                String nodeA = line.split("-")[0];
                String nodeB = line.split("-")[1];
                if (nodeA.equals("start")) nodeA = nodeA.toUpperCase();
                if (nodeB.equals("start")) nodeB = nodeB.toUpperCase();
                if (validPathsFromNode.containsKey(nodeA)) {
                    validPathsFromNode.get(nodeA).add(nodeB);
                } else {
                    validPathsFromNode.put(nodeA, new ArrayList<>(Collections.singletonList(nodeB)));
                }
                if (validPathsFromNode.containsKey(nodeB)) {
                    validPathsFromNode.get(nodeB).add(nodeA);
                } else {
                    validPathsFromNode.put(nodeB, new ArrayList<>(Collections.singletonList(nodeA)));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int solveA() {
        System.out.println(validPathsFromNode);
        Map<String, Integer> isVisited = new HashMap<>();
        List<String> keys = new ArrayList<>(validPathsFromNode.keySet());
        for (String key : keys) {
            isVisited.put(key, 0);
        }
        List<String> localPathList = new ArrayList<>();
        search("START", isVisited, localPathList);
        return counter;
    }

    private void search(String node, Map<String, Integer> isVisited, List<String> localPathList) {
        if (node.equals("end")) {
            System.out.println(localPathList);
            counter++;
            return;
        }
        if (node.equals(node.toLowerCase())) {
            isVisited.put(node, isVisited.get(node) + 1);
        }
        for (int i = 0; i < validPathsFromNode.get(node).size(); i++) {
            String next = validPathsFromNode.get(node).get(i);
            if (!next.equals("START")) {
                if (next.equals(next.toLowerCase())) {
                    if (isVisited.get(next) == 1 && noSmallVisitedTwice(isVisited)) {
                        localPathList.add(next);
                        int index = localPathList.size();
                        search(next, isVisited, localPathList);
                        localPathList.remove(index - 1);
                    } else if (isVisited.get(next) == 0) {
                        localPathList.add(next);
                        int index = localPathList.size();
                        search(next, isVisited, localPathList);
                        localPathList.remove(index - 1);
                    }
                } else {
                    localPathList.add(next);
                    int index = localPathList.size();
                    search(next, isVisited, localPathList);
                    localPathList.remove(index - 1);
                }
            }
        }
        if (node.equals(node.toLowerCase())) {
            isVisited.put(node, isVisited.get(node) - 1);
        }
    }

    private boolean validPath(List<String> localPathList) {
        int sum = 0;
        for (int i = 0; i < localPathList.size(); i++) {
            if (localPathList.get(i).equals(localPathList.get(i).toLowerCase())) {
                if (Collections.frequency(localPathList, localPathList.get(i)) > 1) {
                    sum++;
                }
            }
        }

        return sum / 2 < 2;
    }

    private boolean noSmallVisitedTwice(Map<String, Integer> isVisited) {
        boolean smallVisitedTwice = false;
        List<String> keys = new ArrayList<>(isVisited.keySet());
        for (String key : keys) {
            if (isVisited.get(key) == 2) {
                smallVisitedTwice = true;
            }
        }
        return !smallVisitedTwice;
    }

}
