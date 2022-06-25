package com.school21.printer.app;

import com.school21.printer.logic.BmpToAsciiConverter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Program {
    private static Path imageFile;
    private static char pxWhiteChar = '░';
    private static char pxBlackChar = '█';
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        parseArgs(args);
        try {
            System.out.print(BmpToAsciiConverter.convert(imageFile.toFile(), pxWhiteChar, pxBlackChar));
        } catch (Exception ex) {
            System.out.printf("%sError: %s%s%n", ANSI_RED, ex.getMessage(), ANSI_RESET);
            System.exit(1);
        }
    }

    public static void parseArgs(String[] args) {
        try {
            imageFile = Paths.get(Arrays.stream(args).filter(a -> a.startsWith("--imageFile=")).findAny()
                    .orElseThrow(() -> new IllegalArgumentException("missing mandatory parameter -- imageFile")).split("=")[1]);
            if (!imageFile.isAbsolute() || !Files.exists(imageFile) || !Files.isRegularFile(imageFile)) {
                throw new IllegalArgumentException("image file is corrupted or doesn't exist");
            }
            Arrays.stream(args).filter(a -> a.startsWith("--pxWhiteChar=")).findAny()
                    .ifPresent(a -> pxWhiteChar = a.charAt(a.indexOf("=") + 1));
            Arrays.stream(args).filter(a -> a.startsWith("--pxBlackChar=")).findAny()
                    .ifPresent(a -> pxBlackChar = a.charAt(a.indexOf("=") + 1));
        } catch (IndexOutOfBoundsException ex) {
            System.out.printf("%sError: missing parameter value%s%n", ANSI_RED, ANSI_RESET);
            System.exit(1);
        } catch (IllegalArgumentException ex) {
            System.out.printf("%sError: %s%s%n", ANSI_RED, ex.getMessage(), ANSI_RESET);
            System.exit(1);
        }
    }
}
