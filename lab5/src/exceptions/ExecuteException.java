package exceptions;

public class ExecuteException extends RuntimeException {
    public ExecuteException(String message) {
        super("\n\nОшибка исполнения скрипта. Команда " + message + " будет прервана.\n");
    }
}
