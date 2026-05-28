package exceptions;

import managers.AnnounceManager;

/**
 * Исключение для команды {@link commands.Execute}.
 */
public class ExecuteException extends RuntimeException {
    public ExecuteException(String message) {
        super(AnnounceManager.getInstance().cTCL("execute.e", message));
    }
}
