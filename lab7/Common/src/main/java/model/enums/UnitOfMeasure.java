package model.enums;

import java.io.Serializable;

/**
 * Перечисление единиц измерения.
 * Скопировано из клиентской части — используется в общих моделях.
 */
public enum UnitOfMeasure {
    METERS(1, "unit.meter"),
    SQUARE_METERS(2, "unit.sq_meter"),
    LITERS(3, "unit.liter"),
    GRAMS(4, "unit.gram");

    private final int id;
    private final String key;
    private static final String conditionKey = "unitOfMeasure.conditions";


    UnitOfMeasure(int id, String key) {
        this.id = id;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static String getConditionKey() {
        return conditionKey;
    }

    public static UnitOfMeasure getUnitOfMeasure(String name, int id) {
        for (UnitOfMeasure unitOfMeasure : UnitOfMeasure.values()) {
            if (unitOfMeasure.id == id || unitOfMeasure.name().equalsIgnoreCase(name)) {
                return unitOfMeasure;
            }
        } return null;
    }
}
