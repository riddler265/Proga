package model.enums;

/**
 * Перечисление единиц измерения.
 */
public enum UnitOfMeasure {
    METERS,
    SQUARE_METERS,
    LITERS,
    GRAMS;

    /**
     * Метод, возвращающий все константы в красивой строке.
     * @return константы.
     */
    public static String units() {
        return "Meters, square_meters, liters, grams";
    }
}
