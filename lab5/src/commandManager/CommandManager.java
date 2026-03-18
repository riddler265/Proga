package commandManager;

import collection.Manager;
import commands.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class CommandManager {

    //fields
    private final Map<String, Command> commands = new HashMap<>();
    private final history.History history = new history.History();

    //constructor
    public CommandManager(Manager collection) {
        commands.put("help", new Help(collection));
        commands.put("exit", new Exit(collection));
        commands.put("info", new Info(collection));
        commands.put("show", new Show(collection));
        commands.put("history", new History(collection, history));
        commands.put("clear", new Clear(collection));
        commands.put("add", new Add(collection));
        commands.put("update id", new Update(collection));
        commands.put("remove_by_id", new Remove(collection));
        commands.put("execute_file", new Execute(collection, this));
        commands.put("add_if_min", new AddIf(collection));
    }

    public void execute(String input, Scanner scanner) {
        if (commands.containsKey(input.replaceAll("\\s+\\S+$", ""))) {
            commands.get(input.replaceAll("\\s+\\S+$", "")).execute(input, scanner);
            history.add(input);
        } else {
            System.out.println("Unknown command. Write help.\n");
        }
    }
}
