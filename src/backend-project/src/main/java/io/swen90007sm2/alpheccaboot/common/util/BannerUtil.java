package io.swen90007sm2.alpheccaboot.common.util;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * helper to print banner
 *
 * @author xiaotian
 */
public class BannerUtil {
    public static final String CUSTOM_BANNER_NAME = "banner.txt";

    public static void printBanner() {
        URL url = Thread.currentThread().getContextClassLoader().getResource(CUSTOM_BANNER_NAME);
        if (url != null) {
            try {
                Path path = Paths.get(url.toURI());
                try (Stream<String> stream = Files.lines(path)) {
                    PrintStream printStream = new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8);
                    stream.forEach(printStream::println);
                }
            } catch (URISyntaxException | IOException e) {
                printDefaultBanner();
            }
        } else {
            printDefaultBanner();
        }
    }

    private static void printDefaultBanner() {
        // overcome JDK 18 encoding failure, so not using System out stream
        PrintStream printStream = new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8);
        printStream.println("  ████████ ██       ██ ████████ ████     ██  ████   ████   ████   ████  ██████\n" +
                " ██░░░░░░ ░██      ░██░██░░░░░ ░██░██   ░██ █░░░ █ █░░░██ █░░░██ █░░░██░░░░░░█\n" +
                "░██       ░██   █  ░██░██      ░██░░██  ░██░█   ░█░█  █░█░█  █░█░█  █░█     ░█\n" +
                "░█████████░██  ███ ░██░███████ ░██ ░░██ ░██░ ████ ░█ █ ░█░█ █ ░█░█ █ ░█     █ \n" +
                "░░░░░░░░██░██ ██░██░██░██░░░░  ░██  ░░██░██ ░░░█  ░██  ░█░██  ░█░██  ░█    █  \n" +
                "       ░██░████ ░░████░██      ░██   ░░████   █   ░█   ░█░█   ░█░█   ░█   █   \n" +
                " ████████ ░██░   ░░░██░████████░██    ░░███  █    ░ ████ ░ ████ ░ ████   █    \n" +
                "░░░░░░░░  ░░       ░░ ░░░░░░░░ ░░      ░░░  ░      ░░░░   ░░░░   ░░░░   ░     \n");
    }
}
