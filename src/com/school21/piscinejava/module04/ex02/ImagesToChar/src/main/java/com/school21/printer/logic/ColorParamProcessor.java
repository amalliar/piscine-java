package com.school21.printer.logic;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;
import com.diogonunes.jcolor.Attribute;

import java.util.AbstractMap;
import java.util.Map;

import static com.diogonunes.jcolor.Attribute.*;
import static java.lang.String.format;

public class ColorParamProcessor implements IStringConverter<Attribute>, IParameterValidator {
    public static final Map<String, Attribute> COLORS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("BLACK", BLACK_BACK()),
            new AbstractMap.SimpleEntry<>("RED", RED_BACK()),
            new AbstractMap.SimpleEntry<>("GREEN", GREEN_BACK()),
            new AbstractMap.SimpleEntry<>("YELLOW", YELLOW_BACK()),
            new AbstractMap.SimpleEntry<>("BLUE", BLUE_BACK()),
            new AbstractMap.SimpleEntry<>("MAGENTA", MAGENTA_BACK()),
            new AbstractMap.SimpleEntry<>("CYAN", CYAN_BACK()),
            new AbstractMap.SimpleEntry<>("WHITE", WHITE_BACK()),
            new AbstractMap.SimpleEntry<>("BRIGHT_BLACK", BRIGHT_BLACK_BACK()),
            new AbstractMap.SimpleEntry<>("BRIGHT_RED", BRIGHT_RED_BACK()),
            new AbstractMap.SimpleEntry<>("BRIGHT_GREEN", BRIGHT_GREEN_BACK()),
            new AbstractMap.SimpleEntry<>("BRIGHT_YELLOW", BRIGHT_YELLOW_BACK()),
            new AbstractMap.SimpleEntry<>("BRIGHT_BLUE", BRIGHT_BLUE_BACK()),
            new AbstractMap.SimpleEntry<>("BRIGHT_MAGENTA", BRIGHT_MAGENTA_BACK()),
            new AbstractMap.SimpleEntry<>("BRIGHT_CYAN", BRIGHT_CYAN_BACK()),
            new AbstractMap.SimpleEntry<>("BRIGHT_WHITE", BRIGHT_WHITE_BACK())
    );

    @Override
    public Attribute convert(String color) {
        return COLORS.get(color);
    }

    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!COLORS.containsKey(value)) {
            throw new ParameterException(format("unknown color -- %s", value));
        }
    }
}
