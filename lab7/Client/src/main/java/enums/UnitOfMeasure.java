package enums;

import exceptions.IncorrectInputException;
import localization.AnnounceManager;
import util.NumbParser;

/**
 * Перечисление единиц измерения.
 */
public enum UnitOfMeasure {
    METERS(1, "unit.meter"),
    SQUARE_METERS(2, "unit.sq_meter"),
    LITERS(3, "unit.liter"),
    GRAMS(4, "unit.gram");

    private final int id;
    private final String locale;


    UnitOfMeasure(int id, String locale) {
        this.id = id;
        this.locale = locale;
    }

    public static String getUnitsInfo() {
        return AnnounceManager.getInstance().format("unit.meter") + "(" + METERS.id + ")" + ", " +
                AnnounceManager.getInstance().format("unit.sq_meter") + "(" + SQUARE_METERS.id + ")" + ", " +
                AnnounceManager.getInstance().format("unit.liter") + "(" + LITERS.id + ")" + ", " +
                AnnounceManager.getInstance().format("unit.gram") + "(" + GRAMS.id + ")";
    }

    public static UnitOfMeasure getUnit(String input) throws IncorrectInputException {
        if (input == null || input.isBlank()) {
            throw new IncorrectInputException(getUnitsInfo());
        }

        String trimmedInput = input.trim();

        try {
            int id = NumbParser.parseInt(input);
            for (UnitOfMeasure unit : values()) {
                if (unit.id == id) return unit;
            }
        } catch (NumberFormatException | ArithmeticException ignored) {}

        for (UnitOfMeasure unit : values()) {
            if (AnnounceManager.getInstance().format(unit.locale).equalsIgnoreCase(trimmedInput)) {
                return unit;
            }
        }
        throw new IncorrectInputException(getUnitsInfo());
    }
}
