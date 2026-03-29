package exceptions;

/**
 * Исключение для команды {@link commands.Execute}.
 */
public class ExecuteException extends RuntimeException {
    public ExecuteException(String message) {
        super("Ошибка исполнения скрипта. Команда " + message + " была прервана.\n");
    }
}
