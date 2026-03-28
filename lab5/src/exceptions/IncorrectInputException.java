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
    public IncorrectInputException(String message) {
        super("Incorrect " + message + ". Try again:");
    }
}
