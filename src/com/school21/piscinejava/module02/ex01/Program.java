package com.school21.piscinejava.module02.ex01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Error: invalid number of arguments");
            System.exit(1);
        }
        Map<String, Long> textOneWordFrequencies = getWordFrequencyMap(args[0]);
        Map<String, Long> textTwoWordFrequencies = getWordFrequencyMap(args[1]);
        Set<String> dictionary = new HashSet<>(textOneWordFrequencies.keySet());
        dictionary.addAll(textTwoWordFrequencies.keySet());
        long numerator = 0L;
        long modulusOne = 0L;
        long modulusTwo = 0L;
        for (String key : dictionary) {
            Long freqOne = textOneWordFrequencies.getOrDefault(key, 0L);
            Long freqTwo = textTwoWordFrequencies.getOrDefault(key, 0L);
            numerator += freqOne * freqTwo;
            modulusOne += freqOne * freqOne;
            modulusTwo += freqTwo * freqTwo;
        }
        System.out.printf("Similarity = %.2f", numerator / Math.sqrt(modulusOne * modulusTwo));
    }

    public static Map<String, Long> getWordFrequencyMap(final String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().flatMap(line -> Arrays.stream(line.split("\\s")))
                    .filter(word -> !word.isBlank()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        } catch (Exception ex) {
            System.err.printf("Error: for file %s -- %s%n", filePath, ex.getMessage());
            System.exit(1);
        }
        return Collections.emptyMap();
    }
}
