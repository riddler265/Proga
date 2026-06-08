package enums;

import exceptions.IncorrectInputException;
import localization.AnnounceManager;
import util.NumbParser;

/**
 * Перечисление цветов волос.
 */
public enum Color {
    GREEN(1, "color.green"),
    RED(2, "color.red"),
    BLACK(3, "color.black"),
    YELLOW(4, "color.yellow"),
    ORANGE(5, "color.orange");

    private final int id;
    private final String locale;

    Color(int id, String locale) {
        this.id = id;
        this.locale = locale;
    }

    public static String getColorsInfo() {
        return AnnounceManager.getInstance().format("color.green") + "(" + GREEN.id + ")" + ", " +
                AnnounceManager.getInstance().format("color.red") + "(" + RED.id + ")" + ", " +
                AnnounceManager.getInstance().format("color.black") + "(" + BLACK.id + ")" + ", " +
                AnnounceManager.getInstance().format("color.yellow") + "(" + YELLOW.id + ")" +
                AnnounceManager.getInstance().format("color.orange") + "(" + ORANGE.id + ")";
    }

    public static Color getColor(String input) throws IncorrectInputException {
        if (input == null || input.isBlank()) {
            throw new IncorrectInputException(getColorsInfo());
        }

        String trimmedInput = input.trim();

        try {
            int id = NumbParser.parseInt(input);
            for (Color color : values()) {
                if (color.id == id) return color;
            }
        } catch (NumberFormatException | ArithmeticException ignored) {}

        for (Color color : values()) {
            if (AnnounceManager.getInstance().format(color.locale).equalsIgnoreCase(trimmedInput)) {
                return color;
            }
        }
        throw new IncorrectInputException(getColorsInfo());
    }
}
