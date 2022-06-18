package com.school21.piscinejava.module03.ex02;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class SummingCallable implements Callable<Long> {
    private final int[] workset;
    private final int from;
    private final int to;

    SummingCallable(int[] workset, int from, int to) {
        this.workset = workset;
        this.from = from;
        this.to = Math.min(to, workset.length);
    }

    @Override
    public Long call() {
        long sum = 0;
        for (int i = from; i < to; ++i) {
            sum += workset[i];
        }
        System.out.printf("Thread %d: from %d to %d sum is %d%n", Thread.currentThread().getId(), from, to, sum);
        return sum;
    }
}

public class Program {
    private static int arraySize;
    private static int threadsCount;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final Random RAND = new Random();

    public static void main(String[] args) {
        parseArgs(args);
        int[] workset = new int[arraySize];
        long sum = 0;
        for (int i = 0; i < workset.length; ++i) {
            workset[i] = RAND.nextInt(1001);
            sum += workset[i];
        }
        System.out.printf("Sum: %d%n", sum);

        ExecutorService service = Executors.newFixedThreadPool(threadsCount);
        List<Future<Long>> tasks = new LinkedList<>();
        int subsetSize = arraySize / threadsCount;
        for (int i = 0; i < threadsCount; ++i) {
            tasks.add(service.submit(new SummingCallable(workset, i * subsetSize, (i + 1) * subsetSize)));
        }
        sum = 0;
        for (Future<Long> task : tasks) {
            try {
                sum += task.get();
            } catch (ExecutionException | InterruptedException ignored) {}
        }
        System.out.printf("Sum by threads: %d%n", sum);
        service.shutdownNow();
    }

    public static void parseArgs(String[] args) {
        if (args.length != 2) {
            System.out.printf("%sError: invalid argument(s)%s%n", ANSI_RED, ANSI_RESET);
            System.exit(1);
        }
        try {
            arraySize = Integer.parseInt(Arrays.stream(args).filter(a -> a.startsWith("--arraySize=")).findAny()
                    .orElseThrow(() -> new IllegalArgumentException("missing mandatory argument -- arraySize")).split("=")[1]);
            threadsCount = Integer.parseInt(Arrays.stream(args).filter(a -> a.startsWith("--threadsCount=")).findAny()
                    .orElseThrow(() -> new IllegalArgumentException("missing mandatory argument -- threadsCount")).split("=")[1]);
        } catch (NumberFormatException ex) {
            System.out.printf("%sError: invalid number format%s%n", ANSI_RED, ANSI_RESET);
            System.exit(1);
        } catch (IllegalArgumentException ex) {
            System.out.printf("%sError: %s%s%n", ANSI_RED, ex.getMessage(), ANSI_RESET);
            System.exit(1);
        }
    }
}
