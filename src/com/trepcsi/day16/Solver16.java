package com.trepcsi.day16;

import java.io.BufferedReader;
import java.io.FileReader;


public class Solver16 {

    String binaryString = "";

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
        boolean packetNotProcessed = true;
        decode(binaryString);
        return 1;
    }

    private int decode(String binaryString) {
        System.out.println(binaryString);
        int version = Integer.parseInt(binaryString.substring(0, 3), 2);
        int type = Integer.parseInt(binaryString.substring(3, 6), 2);

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
            System.out.println(Integer.parseInt(valueBin, 2));
            return 11 + i * 5 - 5;
        }

        int subPacketLengthRepresentation;
        if (binaryString.charAt(6) == '0') {
            subPacketLengthRepresentation = 15;
            int lengthOfSubPackets = Integer.parseInt(binaryString.substring(7, 7 + subPacketLengthRepresentation), 2);
            int index = 7 + subPacketLengthRepresentation;
            int indexOtherEnd = 7 + subPacketLengthRepresentation + lengthOfSubPackets;
            int lastResult = 0;
            while (index != 7 + subPacketLengthRepresentation + lengthOfSubPackets) {
                System.out.println("from " + index + " to " + indexOtherEnd);
                lastResult = decode(binaryString.substring(index, indexOtherEnd));
                index += lastResult;
            }
            return index;
        } else {
            subPacketLengthRepresentation = 11;
            int numberOfSubPackets = Integer.parseInt(binaryString.substring(7, 7 + subPacketLengthRepresentation), 2);
            int iteration = numberOfSubPackets;
            int index = 7 + subPacketLengthRepresentation;
            while (iteration > 0) {
                index += decode(binaryString.substring(index));
                iteration--;
            }
            return index;
        }
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
}
