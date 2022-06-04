package com.school21.piscinejava.module00.ex03;

import java.util.Scanner;

public class ALittleBitOfStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long studentTestData = 0;
        String endOfInput = "42";
        String maxGradeGraph = "=========";
        for (int i = 0; i < 18; ++i) {
            String week = scanner.nextLine();
            if (endOfInput.equals(week)) {
                break ;
            }
            if (!("Week " + (i + 1)).equals(week)) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            int minGrade = 9;
            for (int j = 0; j < 5; ++j) {
                int currentGrade = scanner.nextInt();
                if (currentGrade < minGrade) {
                    minGrade = currentGrade;
                }
            }
            scanner.nextLine();
            int shift = (i == 0) ? 1 : (int)pow(10, i);
            studentTestData = studentTestData + shift * minGrade;
        }
        int weekNo = 1;
        while (studentTestData > 0) {
            int minGrade = (int)(studentTestData % 10);
            studentTestData /= 10;
            String gradeGraph = "%." + minGrade + "s>%n";
            System.out.printf(("Week %d " + gradeGraph), weekNo++, maxGradeGraph);
        }
    }

    public static double pow(double a, double b) {
        if (b == 0) {
            return 1;
        } else if (a == 0 || a == 1) {
            return a;
        }
        double result = 1;
        for (int i = 0; i < b; ++i) {
            result *= a;
        }
        return result;
    }

}
