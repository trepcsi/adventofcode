package com.trepcsi;

import com.trepcsi.day1.Solver1;
import com.trepcsi.day2.Solver2;
import com.trepcsi.day3.Solver3;

public class Main {

    public static void main(String[] args) {

        Solver1 solver1 = new Solver1("./src/com/trepcsi/day1/input.txt");
        System.out.println(solver1.solveA());
        System.out.println(solver1.solveB());

        Solver2 solver2 = new Solver2("./src/com/trepcsi/day2/input.txt");
        System.out.println(solver2.solveA());
        System.out.println(solver2.solveB());

        Solver3 solver3 = new Solver3("./src/com/trepcsi/day3/input.txt");
        System.out.println(solver3.solveA());
        System.out.println(solver3.solveB());
    }
}
