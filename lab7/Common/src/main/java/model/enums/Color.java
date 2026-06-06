package model.enums;

import java.io.Serializable;

/**
 * Перечисление цветов волос.
 */
public enum Color implements Serializable {
    GREEN(1, "color.green"),
    RED(2, "color.red"),
    BLACK(3, "color.black"),
    YELLOW(4, "color.yellow"),
    ORANGE(5, "color.orange");

    private final int id;
    private final String key;
    private static final String conditionKey = "color.conditions";

    Color(int id, String key) {
        this.id = id;
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }

    public static String getConditionKey() {
        return conditionKey;
    }

    public static Color getColor(String name, int id) {
        for (Color color : Color.values()) {
            if (color.id == id || color.name().equalsIgnoreCase(name)) {
                return color;
            }
        } return null;
    }
}
