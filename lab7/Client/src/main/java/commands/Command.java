package commands;

import com.google.gson.JsonObject;
import enums.Commands;
import localization.AnnounceManager;
import util.ConsoleManager;
import util.Session;

import java.util.Scanner;
import java.util.Set;

public abstract class Command {

    protected final ConsoleManager consoleManager;

    // Команды которые можно выполнять без авторизации
    private static final Set<String> PUBLIC_COMMANDS = Set.of(
        Commands.REGISTER.getName(),
        Commands.LOGIN.getName(),
        Commands.HELP.getName(),
        Commands.EXIT.getName(),
        Commands.HISTORY.getName()
    );

    public Command(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    protected void print(String key, String... parameters) {
        AnnounceManager.getInstance().print(key, parameters);
    }

    protected void println(String key, String... parameters) {
        AnnounceManager.getInstance().println(key, parameters);
    }

    protected String format(String key, String... parameters) {
        return AnnounceManager.getInstance().format(key, parameters);
    }

    public abstract void execute(String input, Scanner scanner);

    /**
     * Кладёт запрос в очередь отправки.
     * Если команда требует авторизации, а пользователь не залогинен — предупреждает.
     */
    protected void toOutQueue(JsonObject request) {
        String command = request.has("command") ? request.get("command").getAsString() : "";
        if (!PUBLIC_COMMANDS.contains(command) && !Session.getInstance().isAuthenticated()) {
            try {
                System.out.println(AnnounceManager.getInstance().format("response.error.not_authenticated"));
            } catch (Exception e) {
                System.out.println("[ERROR] You must be logged in. Use: login");
            }
            return;
        }
        consoleManager.toOutQueue(request.toString());
    }
}
