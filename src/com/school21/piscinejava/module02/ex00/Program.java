package com.school21.piscinejava.module02.ex00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Program {
    private static int maxSignatureLength = 0;
    private static final String SIGNATURES_FILE_PATH = "/home/amalliar/IdeaProjects/piscine-java/src/com/school21/piscinejava/module02/signatures.txt";
    private static final String RESULT_FILE_PATH = "/home/amalliar/IdeaProjects/piscine-java/src/com/school21/piscinejava/module02/result.txt";
    private static final Map<String, String> FILE_SIGNATURES_MAP = new HashMap<>();
    private static final String PS1 = "-> ";

    public static void main(String[] args) {
        loadFileSignatures();
        Scanner scanner = new Scanner(System.in);
        try (FileOutputStream fos = new FileOutputStream(RESULT_FILE_PATH)) {
            for (;;) {
                System.out.print(PS1);
                String filePath = scanner.nextLine().trim();
                String fileType = getFileType(filePath);
                if (!"UNDEFINED".equals(fileType)) {
                    fos.write(fileType.getBytes());
                    fos.write("\n".getBytes());
                    System.out.println("PROCESSED");
                }
            }
        } catch (Exception ex) {
            System.err.printf("Error: can't create result file -- %s%n", ex.getMessage());
        }
    }

    private static String getFileType(final String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            StringBuilder builder = new StringBuilder();
            int nextByte = fis.read();
            while (nextByte != -1 && builder.length() <= maxSignatureLength) {
                String nextByteAsString = Integer.toString(nextByte, 16).toUpperCase();
                builder.append((nextByteAsString.length() < 2) ? "0" + nextByteAsString : nextByteAsString);
                String fileType = FILE_SIGNATURES_MAP.get(builder.toString());
                if (fileType != null) {
                    return fileType;
                }
                nextByte = fis.read();
            }
        } catch (Exception ex) {
            System.err.printf("Error: can't open specified file -- %s%n", ex.getMessage());
        }
        return "UNDEFINED";
    }

    private static void loadFileSignatures() {
        try (FileInputStream fis = new FileInputStream(SIGNATURES_FILE_PATH)) {
            String nextLine = getNextLine(fis);
            while (!nextLine.isBlank()) {
                String[] valueKeyPair = Arrays.stream(nextLine.split(","))
                        .map(s -> s.replace(" ", "")).toArray(String[]::new);
                if (valueKeyPair.length != 2) {
                    throw new RuntimeException("Error: invalid signatures format");
                }
                if (valueKeyPair[1].length() > maxSignatureLength) {
                    maxSignatureLength = valueKeyPair[1].length();
                }
                FILE_SIGNATURES_MAP.put(valueKeyPair[1], valueKeyPair[0]);
                nextLine = getNextLine(fis);
            }
        } catch (Exception ex) {
            System.err.printf("Error: can't load file signatures -- %s%n", ex.getMessage());
            System.exit(1);
        }
    }

    private static String getNextLine(FileInputStream fis) throws IOException {
        StringBuilder builder = new StringBuilder();
        int nextByte = fis.read();
        while (nextByte != '\n' && nextByte != -1) {
            builder.append((char) nextByte);
            nextByte = fis.read();
        }
        return builder.toString();
    }
}
