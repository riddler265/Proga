package exceptions;

public class CannotCreateLotteryException extends Exception {
    public CannotCreateLotteryException() {
        super("a party member does not belong to the Ministry of Planty");
    }
}