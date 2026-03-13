package commandManager;

import collection.Manager;
import commands.Command;
import commands.Exit;
import commands.Help;
import commands.Info;

import java.util.Map;
import java.util.HashMap;

public class CommandManager {

    //fields
    private final Map<String, Command> commands = new HashMap<>();

    //constructor
    public CommandManager(Manager collection) {
        commands.put("help", new Help(collection));
        commands.put("exit", new Exit(collection));
        commands.put("info", new Info(collection));
    }

    public void execute(String input) {
        if (commands.containsKey(input)) {
            commands.get(input).execute(input);
        } else {
            System.out.println("Unknown command. Write help.\n");
        }
    }
}
