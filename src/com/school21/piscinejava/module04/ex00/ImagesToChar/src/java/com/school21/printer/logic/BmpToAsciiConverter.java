package com.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BmpToAsciiConverter {

    public static String convert(File imageFile, char pxWhiteChar, char pxBlackChar) throws IOException {
        BufferedImage image = ImageIO.read(imageFile);
        if (image == null) {
            throw new IllegalArgumentException("provided file does not contain image data");
        }
        String newline = System.getProperty("line.separator");
        StringBuilder builder = new StringBuilder((image.getWidth() + newline.length()) * image.getHeight());
        for (int j = 0; j < image.getHeight(); ++j) {
            for (int i = 0; i < image.getWidth(); ++i) {
                builder.append((image.getRGB(i, j) == Color.white.getRGB()) ? pxWhiteChar : pxBlackChar);
            }
            builder.append(newline);
        }
        return builder.toString();
    }
}
