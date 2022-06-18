package com.school21.piscinejava.module02.ex02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Program {
    private static Path pwd;
    private static List<Builtin> builtins;
    private static final String PS1 = "-> ";
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    private interface Builtin {
        boolean canHandle(String cmd);
        void execute(String[] args);
    }

    public static void main(String[] args) {
        initShell(args);
        for (;;) {
            try {
                System.out.print(PS1);
                args = SCANNER.nextLine().split("\\s");
                for (Builtin builtin : builtins) {
                    if (builtin.canHandle(args[0])) {
                        builtin.execute(args);
                        break;
                    }
                }
            } catch (NoSuchElementException ex) {
                System.exit(0);
            } catch (Exception ex) {
                System.out.printf("%sError: %s%s%n", ANSI_RED, ex.getMessage(), ANSI_RESET);
            }
        }
    }

    private static void builtinCd(String[] args) {
        if (args.length != 2) {
            System.out.printf("%sError: invalid arguments%s%n", ANSI_RED, ANSI_RESET);
            return;
        }
        Path newPwdFromRelative = pwd.resolve(args[1]).normalize();
        Path newPwdFromAbsolute = Paths.get(args[1]).normalize();
        if (Files.exists(newPwdFromRelative) && Files.isDirectory(newPwdFromRelative)) {
            pwd = newPwdFromRelative;
        } else if (newPwdFromAbsolute.isAbsolute() && Files.exists(newPwdFromAbsolute) && Files.isDirectory(newPwdFromAbsolute)) {
            pwd = newPwdFromAbsolute;
        } else {
            throw new InvalidPathException(args[1], "invalid path");
        }
        System.out.println(pwd);
    }

    private static void builtinLs(String[] args) {
        if (args.length != 1) {
            System.out.printf("%sError: builtin ls does not support arguments%s%n", ANSI_RED, ANSI_RESET);
            return;
        }
        try (Stream<Path> pathStream = Files.list(pwd)) {
            pathStream.sorted().sorted((p0, p1) -> {
                if (Files.isDirectory(p0) && Files.isRegularFile(p1)) {
                    return -1;
                } else if (Files.isRegularFile(p0) && Files.isDirectory(p1)) {
                    return 1;
                }
                return 0;
            }).forEach(path -> {
                try {
                    if (!Files.isHidden(path)) {
                        System.out.printf("%s%s %d KiB%n", path.getFileName(), Files.isDirectory(path) ? "/" : "",
                                Files.size(path) / 1024);
                    }
                } catch (IOException ignored) {}
            });
        } catch (IOException ignored) {}
    }

    private static void builtinMv(String[] args) {
        if (args.length != 3) {
            System.out.printf("%sError: invalid arguments%s%n", ANSI_RED, ANSI_RESET);
            return;
        }
        Path srcPathFromRelative = pwd.resolve(args[1]).normalize();
        Path srcPathFromAbsolute = Paths.get(args[1]).normalize();
        Path dstPathFromRelative = pwd.resolve(args[2]).normalize();
        Path dstPathFromAbsolute = Paths.get(args[2]).normalize();
        if (Files.isDirectory(dstPathFromAbsolute) || Files.isDirectory(dstPathFromRelative)) {
            dstPathFromAbsolute = dstPathFromAbsolute.resolve(srcPathFromAbsolute.getFileName());
            dstPathFromRelative = dstPathFromRelative.resolve(srcPathFromRelative.getFileName());
        }
        try {
            Files.move(srcPathFromAbsolute.isAbsolute() ? srcPathFromAbsolute : srcPathFromRelative,
                    dstPathFromAbsolute.isAbsolute() ? dstPathFromAbsolute : dstPathFromRelative, REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void builtinExit(String[] args) {
        System.exit(0);
    }

    private static void initShell(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
            System.out.printf("%sError: invalid argument(s)%s%n", ANSI_RED, ANSI_RESET);
            System.exit(1);
        }
        try {
            pwd = Paths.get(args[0].split("=")[1]).normalize();
            if (!pwd.isAbsolute() || !Files.exists(pwd) || !Files.isDirectory(pwd)) {
                throw new InvalidPathException(pwd.toString(), "invalid path");
            }
        } catch (InvalidPathException ex) {
            System.out.printf("%sError: %s%s%n", ANSI_RED, ex.getMessage(), ANSI_RESET);
            System.exit(1);
        }
        builtins = new ArrayList<>(List.of(
                new Builtin() { public boolean canHandle(String cmd) { return "ls".equals(cmd); } public void execute(String[] args) { builtinLs(args); } },
                new Builtin() { public boolean canHandle(String cmd) { return "cd".equals(cmd); } public void execute(String[] args) { builtinCd(args); } },
                new Builtin() { public boolean canHandle(String cmd) { return "mv".equals(cmd); } public void execute(String[] args) { builtinMv(args); } },
                new Builtin() { public boolean canHandle(String cmd) { return "exit".equals(cmd); } public void execute(String[] args) { builtinExit(args);} }
        ));
        System.out.println(pwd);
    }
}
