package coordinates;

import exceptions.IncorrectInputException;

public class Coordinates {
    //fields
    private Integer x; //Значение поля должно быть больше -645, Поле не может быть null
    private Integer y; //Поле не может быть null

    //constructor
    public Coordinates(Integer x, Integer y) throws IncorrectInputException {
        if (x == null || x < -645 || y == null) throw new IncorrectInputException("coordinates");
        else {
            this.x = x;
            this.y = y;
        }
    }
}
