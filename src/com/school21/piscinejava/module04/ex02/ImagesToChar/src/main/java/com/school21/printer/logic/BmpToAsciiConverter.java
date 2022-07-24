package com.school21.printer.logic;

import com.diogonunes.jcolor.Attribute;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import static com.diogonunes.jcolor.Ansi.colorize;

public class BmpToAsciiConverter {
    public static String convert(final String imageFile, final Attribute pxWhiteColor,
                                 final Attribute pxBlackColor) throws IOException {
        BmpToAsciiConverter instance = new BmpToAsciiConverter();
        BufferedImage image = ImageIO.read(instance.getFileAsIOStream(imageFile));
        if (image == null) {
            throw new IllegalArgumentException("provided file does not contain image data");
        }
        String newline = System.getProperty("line.separator");
        StringBuilder builder = new StringBuilder((image.getWidth() + newline.length()) * image.getHeight());
        for (int j = 0; j < image.getHeight(); ++j) {
            for (int i = 0; i < image.getWidth(); ++i) {
                builder.append(colorize(" ", (image.getRGB(i, j) == Color.white.getRGB())
                        ? pxWhiteColor : pxBlackColor));
            }
            builder.append(newline);
        }
        return builder.toString();
    }

    private InputStream getFileAsIOStream(final String fileName) throws FileNotFoundException {
        if (Paths.get(fileName).isAbsolute()) {
            return new FileInputStream(fileName);
        }
        InputStream ioStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (ioStream == null) {
            throw new IllegalArgumentException("image file is corrupted or doesn't exist");
        }
        return ioStream;
    }
}
