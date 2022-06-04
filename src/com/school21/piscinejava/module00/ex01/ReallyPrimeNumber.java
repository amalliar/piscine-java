package com.school21.piscinejava.module00.ex01;

import java.util.Scanner;

import static java.lang.Math.*;

public class ReallyPrimeNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        if (number < 2) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        int numberOfSteps = 0;
        boolean isPrime = true;
        for (int i = 2; i <= ceil(sqrt(number)); ++i) {
            ++numberOfSteps;
            if (number % i == 0) {
                isPrime = false;
                break;
            }
        }
        System.out.printf("%s %d%n", isPrime, numberOfSteps);
    }
}
