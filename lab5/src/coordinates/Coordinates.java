package coordinates;

import exceptions.IncorrectInputException;
import product.Product;

import java.util.Objects;

/**
 * Класс координат.
 */
public class Coordinates {
    //fields
    private Integer x; //Значение поля должно быть больше -645, Поле не может быть null
    private Integer y; //Поле не может быть null

    /**
     * Конструктор.
     * <p>
     * @param x координата Х, Значение поля должно быть больше -645, Поле не может быть null,
     * @param y координата У, Поле не может быть null
     * @throws IncorrectInputException - невыполнение вышеперечисленных условий.
     */
    public Coordinates(Integer x, Integer y) throws IncorrectInputException {
        if (x == null || x < -645 || y == null) throw new IncorrectInputException("coordinates");
        else {
            this.x = x;
            this.y = y;
        }
    }

    //getters
    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    //setters
    public Coordinates setX(Integer x) {
        this.x = x;
        return this;
    }

    public Coordinates setY(Integer y) {
        this.y = y;
        return this;
    }

    //equals(), hachCode(), toString()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates coordinates = (Coordinates) o;
        return x.equals(coordinates.x)  && y.equals(coordinates.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "X: " + x.toString() + ", Y:" + y.toString();
    }
}
