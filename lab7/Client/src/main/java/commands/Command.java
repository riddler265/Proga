package commands;

import com.google.gson.JsonObject;
import communication.Request;
import localization.AnnounceManager;
import util.ConsoleManager;

import java.util.Scanner;

public abstract class Command {

    protected final ConsoleManager consoleManager;

    public Command(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    protected void print(String key, String ... parameters) {
        AnnounceManager.getInstance().print(key, parameters);
    }

    protected void println(String key, String ... parameters) {
        AnnounceManager.getInstance().println(key, parameters);
    }

    protected String format(String key, String ... parameters) {
        return AnnounceManager.getInstance().format(key, parameters);
    }

    protected void toOutQueue(Request request) {
        consoleManager.toOutQueue(request);
    }

    public abstract void execute(String input, Scanner scanner);
}
