package exceptions;

public class ListenerIsTooFarAwayException extends Exception {
    public ListenerIsTooFarAwayException() {
        super("The listener is too far away to start a talk.");
    }
    public ListenerIsTooFarAwayException(String name) {
        super(name + " is too far away to start a talk.");
    }
}