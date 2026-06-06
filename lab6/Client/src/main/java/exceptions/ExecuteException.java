package exceptions;


import localization.AnnounceManager;


public class ExecuteException extends RuntimeException {
    public ExecuteException(String message) {
        super(AnnounceManager.getInstance().format("execute.e", message));
    }
}
