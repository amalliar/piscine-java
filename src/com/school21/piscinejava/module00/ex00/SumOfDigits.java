package com.school21.piscinejava.module00.ex00;

public class SumOfDigits {
    public static void main(String[] args) {
        int number = 479598;
        System.out.println(sumOfDigits(number));
    }

    public static int sumOfDigits(int number) {
        int sumOfDigits = 0;
        while (number != 0) {
            sumOfDigits += number % 10;
            number /= 10;
        }
        return sumOfDigits;
    }
}
