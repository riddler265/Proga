package exceptions;

public class IncorrectInputException extends RuntimeException {
    public IncorrectInputException(String message) {
        super("Incorrect " + message + ". Try again:");
    }
}
