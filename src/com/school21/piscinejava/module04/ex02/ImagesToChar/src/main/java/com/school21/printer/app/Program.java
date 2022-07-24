package com.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.school21.printer.logic.BmpToAsciiConverter;
import com.school21.printer.logic.ColorParamProcessor;

import java.util.stream.Collectors;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.RED_TEXT;
import static java.lang.String.format;

public class Program {

    public static void main(String[] argv) {
        try {
            Args args = new Args();
            JCommander jc = JCommander.newBuilder().addObject(args).build();
            jc.parse(argv);
            if (args.help) {
                jc.usage();
            } else if (args.listColors) {
                System.out.printf("available color options:%n");
                for (String color : ColorParamProcessor.COLORS.keySet().stream()
                        .filter(s -> !s.startsWith("BRIGHT_")).sorted().collect(Collectors.toList())) {
                    System.out.printf("[BRIGHT_]%s%n", color);
                }
            } else {
                System.out.print(BmpToAsciiConverter.convert(
                        args.imageFile, args.pxWhiteColor, args.pxBlackColor));
            }
        } catch (Exception ex) {
            System.out.println(colorize(format("Error: %s", ex.getMessage()), RED_TEXT()));
            System.exit(1);
        }
    }
}
