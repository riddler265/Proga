package model.enums;

import exceptions.IncorrectInputException;
import util.NumbParser;

/**
 * Перечисление цветов волос.
 */
public enum Color {
    GREEN(1),
    RED(2),
    BLACK(3),
    YELLOW(4),
    ORANGE(5);

    private final int id;
    private static final String message = "\n\tGreen(1), \n\tRed(2), \n\tBlack(3), \n\tYellow(4), \n\tOrange(5), \n\tNull";

    Color(int id) {
        this.id = id;
    }

    public static String getColorsInfo() {
        return "Green(1), Red(2), Black(3), Yellow(4), Orange(5), Null";
    }

    public static Color getcolor(String input) throws IncorrectInputException {
        if (input == null || input.isBlank()) {
            throw new IncorrectInputException(message);
        }

        String trimmedInput = input.trim();

        try {
            int id = NumbParser.parseInt(input);
            for (Color color : values()) {
                if (color.id == id) return color;
            }
        } catch (NumberFormatException | ArithmeticException ignored) {}

        for (Color color : values()) {
            if (color.name().equalsIgnoreCase(trimmedInput)) {
                return color;
            }
        }
        throw new IncorrectInputException(message);
    }
}
