package exceptions;

/**
 * <p>
 * Исключения для некорректных данных для классов:
 * {@link product.Product},
 * {@link person.Person},
 * {@link coordinates.Coordinates}.
 */
public class IncorrectInputException extends RuntimeException {
    public IncorrectInputException(String message) {
        super("Incorrect " + message + ". Try again:");
    }
}
