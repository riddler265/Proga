package model;

import exceptions.IncorrectInputException;
import interfaces.Validate;
import java.util.Objects;
import util.NumbParser;

/**
 * Класс координат.
 */
public class Coordinates implements Validate {

    //fields
    private Integer x; //Значение поля должно быть больше -645, Поле не может быть null
    private Integer y; //Поле не может быть null

    //region getters
    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }//endregion

    //region setters
    public Coordinates setX(String input) throws IncorrectInputException {
        try {
            this.x = NumbParser.parseInt(input);
            if (x <= -645) throw new ArithmeticException();
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("целое число > -645");
        }
        return this;
    }

    public Coordinates setY(String input) throws IncorrectInputException {
        try {
            this.y = NumbParser.parseInt(input);
        } catch (ArithmeticException | NumberFormatException e) {
            throw new IncorrectInputException("целое число");
        }
        return this;
    }//endregion

    @Override
    public boolean validate() {
        if (x == null || x < -645) return false;
        if (y == null) return false;
        return true;
    }

    //region equals(), hachCode(), toString()
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
        return "X: " + x.toString() + ", Y: " + y.toString();
    }//endregion
}
