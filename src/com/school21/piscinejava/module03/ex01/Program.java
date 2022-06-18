package com.school21.piscinejava.module03.ex01;

import java.util.LinkedList;
import java.util.List;

class ProducerConsumer {
    private final List<Integer> list = new LinkedList<>();
    private final int capacity = 1;
    private final int iterations;
    private final String onProduce;
    private final String onConsume;

    ProducerConsumer(int iterations, String onProduce, String onConsume) {
        this.iterations = iterations;
        this.onProduce = onProduce;
        this.onConsume = onConsume;
    }

    public void produce() {
        try {
            for (int i = 0; i < iterations; ++i) {
                synchronized (this) {
                    while (list.size() == capacity) {
                        wait();
                    }
                    list.add(0);
                    System.out.println(onProduce);
                    notify();
                }
            }
        } catch (InterruptedException ignored) {}
    }

    public void consume() {
        try {
            for (int i = 0; i < iterations; ++i) {
                synchronized (this) {
                    while (list.isEmpty()) {
                        wait();
                    }
                    list.remove(0);
                    System.out.println(onConsume);
                    notify();
                }
            }
        } catch (InterruptedException ignored) {}
    }
}

public class Program {
    private static int count;
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            System.out.printf("%sError: invalid argument(s)%s%n", ANSI_RED, ANSI_RESET);
            System.exit(1);
        }
        try {
            count = Integer.parseInt(args[0].split("=")[1]);
        } catch (NumberFormatException ex) {
            System.out.printf("%sError: invalid count value%s%n", ANSI_RED, ANSI_RESET);
            System.exit(1);
        }
        ProducerConsumer pc = new ProducerConsumer(count, "Egg", "Hen");
        List<Thread> workers = List.of(new Thread(pc::produce), new Thread(pc::consume));
        workers.forEach(Thread::start);
        for (Thread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException ignored) {}
        }
    }
}
