package com.school21.piscinejava.module00.ex04;

import java.util.Scanner;

public class ABitMoreOfStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        char[] charFrequency = new char[65536];
        for (char c : input.toCharArray()) {
            ++charFrequency[c];
        }
        char[] tenMostFrequent = new char[10];
        for (int i = 0; i < 65536; ++i) {
            char newChar = (char)i;
            char newCharFreq = charFrequency[newChar];
            for (int j = 0; j < 10; ++j) {
                char storedChar = tenMostFrequent[j];
                char storedCharFreq = charFrequency[storedChar];
                if (newCharFreq > storedCharFreq
                        || (newCharFreq == storedCharFreq && newChar < storedChar)) {
                    tenMostFrequent[j] = newChar;
                    newChar = storedChar;
                    newCharFreq = storedCharFreq;
                }
            }
        }

        char[][] histogram = new char[30][12];
        char fillChar = '#';
        char emptyChar = ' ';
        double fillScale = (double)charFrequency[tenMostFrequent[0]] / 10;

        for (int i = 0; i < 10; ++i) {
            char currentChar = tenMostFrequent[i];
            char currentCharFreq = charFrequency[currentChar];
            int numToFill = (int)(charFrequency[currentChar] / fillScale);

            // Prepare first column.
            for (int j = 0; j < 12; ++j) {
                histogram[i * 3][j] = emptyChar;
            }
            if (currentCharFreq / 100 != 0) {
                histogram[i * 3][numToFill + 1] = (char)(currentCharFreq / 100 + '0');
            }

            // Prepare second column.
            for (int j = 0; j < 12; ++j) {
                histogram[i * 3 + 1][j] = emptyChar;
            }
            if (currentCharFreq / 10  != 0) {
                histogram[i * 3 + 1][numToFill + 1] = (char)(currentCharFreq / 10 % 10 + '0');
            }

            // Prepare third column.
            histogram[i * 3 + 2][0] = currentChar;
            for (int j = 1; j <= numToFill; ++j) {
                histogram[i * 3 + 2][j] = fillChar;
            }
            histogram[i * 3 + 2][numToFill + 1] = (char)(currentCharFreq % 10 + '0');
            for (int j = numToFill + 2; j < 12; ++j) {
                histogram[i * 3 + 2][j] = emptyChar;
            }
        }

        for (int j = 11; j >= 0; --j) {
            for (int i = 0; i < 30; ++i) {
                System.out.print(histogram[i][j]);
            }
            System.out.println();
        }
    }
}
