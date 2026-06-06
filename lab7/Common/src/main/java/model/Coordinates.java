package model;

import java.util.Objects;

/**
 * Класс координат. Serializable для передачи по сети.
 */
public class Coordinates {

    private Integer x; // > -645, not null
    private Integer y; // not null

    public Integer getX() { return x; }
    public Integer getY() { return y; }

    public Coordinates setX(Integer x) {
        this.x = x;
        return this;
    }

    public Coordinates setY(Integer y) {
        this.y = y;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() { return Objects.hash(x, y); }

    @Override
    public String toString() { return "X: " + x + ", Y: " + y; }
}
