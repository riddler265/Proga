package coordinates;

import exceptions.IncorrectInputException;
import interfaces.Validate;
import product.Product;

import java.util.Objects;

/**
 * Класс координат.
 */
public class Coordinates implements Validate {
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
    public Coordinates setX(Integer x) throws IncorrectInputException {
        if (x == null || x < -645) throw new IncorrectInputException("coordinate x");
        else this.x = x;
        return this;
    }

    public Coordinates setY(Integer y) throws IncorrectInputException {
        if (y == null) throw new IncorrectInputException("coordinate y");
        else this.y = y;
        return this;
    }

    @Override
    public boolean validate() {
        if (x == null || x < -645) return false;
        if (y == null) return false;
        return true;
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
