package com.school21.piscinejava.module00.ex05;

import java.util.Scanner;

public class Schedule {
    public static final String[] DAYS_OF_WEEK = new String[] {"MO", "TU", "WE", "TH", "FR", "SA", "SU"};
    public static final int FIRST_DAY_OF_MONTH_IDX = 1;
    public static final int MAX_COLUMN_LENGTH = 10;

    public static void main(String[] args) {
        String[] students = new String[10];
        String[][] classes = new String[10][2];
        String[][] attendance = new String[500][4];
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        while (true) {
            String student = scanner.next();
            if (".".equals(student)) {
                break;
            }
            students[i++] = student;
        }
        i = 0;
        while (true) {
            String classTime = scanner.next();
            if (".".equals(classTime)) {
                break;
            }
            String classDay = scanner.next();
            classes[i][0] = classTime;
            classes[i++][1] = classDay;
        }
        i = 0;
        while (true) {
            String student = scanner.next();
            if (".".equals(student)) {
                break;
            }
            String classTime = scanner.next();
            String classDate = scanner.next();
            String presence = scanner.next();
            attendance[i][0] = student;
            attendance[i][1] = classTime;
            attendance[i][2] = classDate;
            attendance[i++][3] = presence;
        }

        printTableHeader(classes);
        for (String student : students) {
            populateTable(classes, attendance, student);
        }
    }

    public static void printTableHeader(String[][] classes) {
        for (int i = 0; i < MAX_COLUMN_LENGTH; ++i) {
            System.out.print(" ");
        }
        for (int i = 0; i < 30; ++i) {
            String currentWeekDay = DAYS_OF_WEEK[(i + FIRST_DAY_OF_MONTH_IDX) % 7];
            for (int j = 1; j <= 6; ++j) {
                for (String[] currentClass : classes) {
                    if (currentClass[0] == null) {
                        break;
                    }
                    if (currentWeekDay.equals(currentClass[1]) && (j + "").equals(currentClass[0])) {
                        System.out.printf("%d:00 %s %2s|", j, currentWeekDay, i + 1);
                    }
                }
            }
        }
        System.out.println();
    }

    public static void populateTable(String[][] classes, String[][] attendance, String student) {
        if (student == null) {
            return;
        }
        System.out.printf("%10.10s", student);
        for (int i = 0; i < 30; ++i) {
            for (int j = 1; j <= 6; ++j) {
                boolean hasRecordForDateAndTime = false;
                for (String[] record : attendance) {
                    if (record[0] == null) {
                        break;
                    }
                    if (student.equals(record[0]) && (i + 1 + "").equals(record[2]) && (j + "").equals(record[1])) {
                        System.out.printf("%10d|", "HERE".equals(record[3]) ? 1 : -1);
                        hasRecordForDateAndTime = true;
                        break;
                    }
                }
                String currentWeekDay = DAYS_OF_WEEK[(i + FIRST_DAY_OF_MONTH_IDX) % 7];
                if (!hasRecordForDateAndTime && hasClassForDayAndTime(classes, currentWeekDay, j + "")) {
                    System.out.print("          |");
                }
            }
        }
        System.out.println();
    }

    public static boolean hasClassForDayAndTime(String[][] classes, String weekDay, String time) {
        for (String[] currentClass : classes) {
            if (currentClass[0] == null) {
                return false;
            }
            if (weekDay.equals(currentClass[1]) && time.equals(currentClass[0])) {
                return true;
            }
        }
        return false;
    }
}
