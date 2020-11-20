package com.airwallex.rpncalculator;

import java.util.Scanner;

/**
 * The entry point of the calculator application in the command line mode
 *
 * @author Zhu Zhaohua
 */
public class CalculatorMain {
    public static void main(String[] args) {
        CalculatorDriver driver = new CalculatorDriver();
        System.out.println("RPN Calculator started.\n" +
                "Please enter space-separated numbers/operators to perform calculation.");
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            driver.evaluate(sc.nextLine());
        }
        sc.close();
    }
}
