package model.enums;

import exceptions.IncorrectInputException;

/**
 * Перечисление единиц измерения.
 */
public enum UnitOfMeasure {
    METERS(1),
    SQUARE_METERS(2),
    LITERS(3),
    GRAMS(4);

    private final int id;
    private static final String message = "\n\tMeters(1), \n\tSquare_meters(2), \n\tLiters(3), \n\tgrams(4)";

    UnitOfMeasure(int id) {
        this.id = id;
    }

    public static String getUnitsInfo() {
        return "Meters(1), Square_meters(2), Liters(3), grams(4)";
    }

    public static UnitOfMeasure getUnit(String input) throws IncorrectInputException {
        if (input == null || input.isBlank()) {
            throw new IncorrectInputException(message);
        }

        String trimmedInput = input.trim();

        try {
            int id = (int) Double.parseDouble(trimmedInput.replace(',', '.'));
            for (UnitOfMeasure unit : values()) {
                if (unit.id == id) return unit;
            }
        } catch (NumberFormatException e) {}

        for (UnitOfMeasure unit : values()) {
            if (unit.name().equalsIgnoreCase(trimmedInput)) {
                return unit;
            }
        }
        throw new IncorrectInputException(message);
    }
}
