package com.trepcsi.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Solver12 {

    private Map<String, List<String>> validPathsFromNode = new HashMap<>();
    private int counter = 0;
    private int smallVisited = 0;

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
        Map<String, Boolean> isVisited = new HashMap<>();
        List<String> keys = new ArrayList<>(validPathsFromNode.keySet());
        for (String key : keys) {
            isVisited.put(key, false);
        }
        List<String> localPathList = new ArrayList<>();
        search("START", isVisited, localPathList);
        return counter;
    }

    private void search(String node, Map<String, Boolean> isVisited, List<String> localPathList) {
        if (node.equals("end")) {
            System.out.println(localPathList);
            counter++;
            return;
        }
        if (node.equals(node.toLowerCase()) || node.equals("START")) {
            isVisited.put(node, true);
        }
        for (int i = 0; i < validPathsFromNode.get(node).size(); i++) {
            String next = validPathsFromNode.get(node).get(i);
            if (!isVisited.get(next)) {
                localPathList.add(next);
                search(next, isVisited, localPathList);
                localPathList.remove(next);
            }
        }
        isVisited.put(node, false);
    }

}
