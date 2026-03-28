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
    private static final String message = "\nMeters(1)\n, Square_meters(2)\n, Liters(3)\n, grams(4)";

    UnitOfMeasure(int id) {
        this.id = id;
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
