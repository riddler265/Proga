package exceptions;

import model.Coordinates;
import model.Person;
import model.Product;

/**
 * <p>
 * Исключения для некорректных данных для классов:
 * {@link Product},
 * {@link Person},
 * {@link Coordinates}.
 */
public class IncorrectInputException extends RuntimeException {
    public IncorrectInputException(String conditions) {
        super("Допустимые значения параметра: " + conditions + "\nПопробуйте еще раз: ");
    }
}
