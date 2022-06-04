package com.school21.piscinejava.module00.ex02;

import java.util.Scanner;

import static com.school21.piscinejava.module00.ex00.SumOfDigits.sumOfDigits;
import static java.lang.Math.*;

public class EndlessSequenceOrNot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number;
        int coffeeQueries = 0;
        do {
            number = scanner.nextInt();
            if (isPrime(sumOfDigits(number))) {
                ++coffeeQueries;
            }
        } while (number != 42);
        System.out.printf("Count of coffee-request - %d%n", coffeeQueries);
    }

    public static boolean isPrime(int number) {
        boolean isPrime = true;
        for (int i = 2; i <= ceil(sqrt(number)); ++i) {
            if (number % i == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }
}
