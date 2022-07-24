package com.school21.printer.app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.diogonunes.jcolor.Attribute;
import com.school21.printer.logic.ColorParamProcessor;

import static com.diogonunes.jcolor.Attribute.BLACK_BACK;
import static com.diogonunes.jcolor.Attribute.BRIGHT_WHITE_BACK;

@Parameters(separators = "=")
public class Args {

    @Parameter(names = "--imageFile", description = "path to bmp image file to process")
    public String imageFile = "8-bit-dinosaur.bmp";

    @Parameter(names = "--pxWhiteColor", description = "color to use for white pixels", converter = ColorParamProcessor.class, validateWith = ColorParamProcessor.class)
    public Attribute pxWhiteColor = BRIGHT_WHITE_BACK();

    @Parameter(names = "--pxBlackColor", description = "color to use for black pixels", converter = ColorParamProcessor.class, validateWith = ColorParamProcessor.class)
    public Attribute pxBlackColor = BLACK_BACK();

    @Parameter(names = "--list-colors", description = "list available color options", help = true)
    public boolean listColors = false;

    @Parameter(names = "--help", description = "display this help text and exit", help = true)
    public boolean help = false;
}

