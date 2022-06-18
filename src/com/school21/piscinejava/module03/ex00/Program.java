package com.school21.piscinejava.module03.ex00;

import java.util.List;

class PrinterRunnable implements Runnable {
    private final String message;
    private final int count;

    public PrinterRunnable(String message, int count) {
        this.message = message;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; ++i) {
            System.out.println(message);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {}
        }
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
        List<Thread> workers = List.of(
                new Thread(new PrinterRunnable("Egg", count)),
                new Thread(new PrinterRunnable("Hen", count))
        );
        workers.forEach(Thread::start);
        for (Thread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException ignored) {}
        }
        new PrinterRunnable("Human", count).run();
    }
}
