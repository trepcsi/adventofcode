package com.trepcsi.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Solver16 {

    private String binaryString = "";
    private int versionSum = 0;

    private List<Result> resultList = new ArrayList<>();

    public Solver16(String fileName) {
        String hexaString = "";
        try {
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                hexaString += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < hexaString.length(); i++) {
            binaryString += map(String.valueOf(hexaString.charAt(i)));
        }
    }

    public int solveA() {
        decode(binaryString, 0);

        return versionSum;
    }

    public long solveB() {
        decode(binaryString, 0);
        System.out.print("\n");
        calculateResult();
        System.out.println(resultList);
        return resultList.get(0).value;
    }

    private void calculateResult() {
        List<Result> values = new ArrayList<>();

        while (resultList.size() != 1) {
            for (int i = 0; i < resultList.size(); i++) {
                if (noMoreDepth(i)) {
                    getListOfValues(i, resultList.get(i).depth, values);
                    for (Result value : values) {
                        resultList.remove(value);
                    }
                    resultList.get(i-1).calculateValue(values);


                }
            }
            System.out.println();

        }

    }

    private boolean noMoreDepth(int i) {
        int newDepth = 0;
        for (int j = i; j < resultList.size(); j++) {
            if (resultList.get(j).depth != resultList.get(i).depth) {
                newDepth = resultList.get(j).depth;
                break;
            }
        }
        return newDepth < resultList.get(i).depth;
    }

    private int getListOfValues(int i, int depth, List<Result> result) {
        int index = 0;
        result.clear();
        for (int j = i; j < resultList.size(); j++) {
            if (resultList.get(j).depth == depth) {
                index = j;

                result.add(resultList.get(j));
            } else {
                break;
            }
        }
        return index;
    }


    private int decode(String binaryString, int depth) {
        System.out.print("\n");
        depth++;
        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }

        int version = Integer.parseInt(binaryString.substring(0, 3), 2);
        versionSum += version;
        int type = Integer.parseInt(binaryString.substring(3, 6), 2);
        System.out.print(type + " -> ");

        if (type == 4) {
            String valueBin = "";
            boolean reachedLast = false;
            int i = 0;
            while (!reachedLast) {
                valueBin += binaryString.substring(7 + i * 5, 11 + i * 5);
                if (binaryString.charAt(6 + 5 * i) == '0') {
                    reachedLast = true;
                }
                i++;
            }
            System.out.print(Long.parseLong(valueBin, 2));
            Result result = new Result(depth, type, Long.parseLong(valueBin, 2));
            resultList.add(result);
            return 11 + i * 5 - 5;
        }

        Result result = new Result(depth, type, 0L);
        resultList.add(result);

        int subPacketLengthRepresentation;
        int index;
        if (binaryString.charAt(6) == '0') {
            subPacketLengthRepresentation = 15;
            int lengthOfSubPackets = Integer.parseInt(binaryString.substring(7, 7 + subPacketLengthRepresentation), 2);
            index = 7 + subPacketLengthRepresentation;
            int indexOtherEnd = 7 + subPacketLengthRepresentation + lengthOfSubPackets;
            int lastResult;
            while (index != 7 + subPacketLengthRepresentation + lengthOfSubPackets) {
                lastResult = decode(binaryString.substring(index, indexOtherEnd), depth);
                index += lastResult;
            }

        } else {
            subPacketLengthRepresentation = 11;
            int numberOfSubPackets = Integer.parseInt(binaryString.substring(7, 7 + subPacketLengthRepresentation), 2);
            int iteration = numberOfSubPackets;
            index = 7 + subPacketLengthRepresentation;
            while (iteration > 0) {
                index += decode(binaryString.substring(index), depth);
                iteration--;
            }

        }


        return index;
    }

    public String map(String hexa) {
        return switch (hexa) {
            case "0" -> "0000";
            case "1" -> "0001";
            case "2" -> "0010";
            case "3" -> "0011";
            case "4" -> "0100";
            case "5" -> "0101";
            case "6" -> "0110";
            case "7" -> "0111";
            case "8" -> "1000";
            case "9" -> "1001";
            case "A" -> "1010";
            case "B" -> "1011";
            case "C" -> "1100";
            case "D" -> "1101";
            case "E" -> "1110";
            case "F" -> "1111";
            default -> "asdasd";
        };
    }


    public static class Result {
        int depth;
        int type;
        long value;

        public Result(int depth, int type, long value) {
            this.depth = depth;
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "depth=" + depth +
                    ", type=" + type +
                    ", value=" + value +
                    '}';
        }

        public void calculateValue(List<Result> values) {
            switch (this.type) {
                case 0 -> this.value = values.stream().map(t -> t.value).reduce(0L, (a, b) -> a + b);
                case 1 -> this.value = values.stream().map(t -> t.value).reduce(1L, (a, b) -> a * b);
                case 2 -> this.value = values.stream().map(t -> t.value).min(Long::compareTo).get();
                case 3 -> this.value = values.stream().map(t -> t.value).max(Long::compareTo).get();
                case 5 -> {
                    if (values.get(0).value > values.get(1).value) {
                        this.value = 1;
                    } else {
                        this.value = 0;
                    }
                }
                case 6 -> {
                    if (values.get(0).value < values.get(1).value) {
                        this.value = 1;
                    } else {
                        this.value = 0;
                    }
                }
                case 7 -> {
                    if (values.get(0).value == values.get(1).value) {
                        this.value = 1;
                    } else {
                        this.value = 0;
                    }
                }
            }
            values.clear();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Result result = (Result) o;
            return depth == result.depth && type == result.type && value == result.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(depth, type, value);
        }
    }

}
