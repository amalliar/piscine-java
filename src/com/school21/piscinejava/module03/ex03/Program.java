package com.school21.piscinejava.module03.ex03;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class DownloadRunnable implements Runnable {
    private final String downloadPath;
    private final String url;

    DownloadRunnable(String downloadPath, String url) {
        this.downloadPath = downloadPath;
        this.url = url;
    }

    @Override
    public void run() {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        System.out.printf("Thread-%d: Start download file %s%n", Thread.currentThread().threadId(), fileName);
        String filePath = downloadPath + FileSystems.getDefault().getSeparator() + fileName;
        if (Files.exists(Path.of(filePath))) {
            System.out.printf("Thread-%d: Warning! File %s already present in the system, skipping...%n",
                    Thread.currentThread().threadId(), fileName);
            return;
        }
        try (FileOutputStream fileOS = new FileOutputStream(downloadPath + FileSystems.getDefault().getSeparator() + fileName)) {
            ReadableByteChannel readChannel = Channels.newChannel(new URI(url).toURL().openStream());
            fileOS.getChannel().transferFrom(readChannel, 0, Long.MAX_VALUE);
            System.out.printf("Thread-%d: Finish download file %s%n", Thread.currentThread().threadId(), fileName);
        } catch (IOException | URISyntaxException ex) {
            System.out.printf("%sThread-%d: Error: %s%s%n", Program.ANSI_RED, Thread.currentThread().threadId(), ex.getMessage(), Program.ANSI_RESET);
        }

    }
}

public class Program {
    private static int threadsCount;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static final String DOWNLOAD_PATH = Paths.get("").toAbsolutePath().toString();

    public static void main(String[] args) {
        parseArgs(args);
        try (ExecutorService service = Executors.newFixedThreadPool(threadsCount)) {
            List<String> filesUrls = Files.readAllLines(Paths.get("files_urls.txt"));
            filesUrls.forEach(url -> service.execute(new DownloadRunnable(DOWNLOAD_PATH, url)));
            service.shutdown();
        } catch (IOException ex) {
            System.out.printf("%sError: %s%s%n", ANSI_RED, ex.getMessage(), ANSI_RESET);
        }
    }

    public static void parseArgs(String[] args) {
        if (args.length != 1) {
            System.out.printf("%sError: invalid argument(s)%s%n", ANSI_RED, ANSI_RESET);
            System.exit(1);
        }
        try {
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
