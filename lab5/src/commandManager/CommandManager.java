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
    //private final BlockingQueue<String> history = new LinkedBlockingQueue<>(8);

    //constructor
    public CommandManager(Manager collection, Scanner scanner) {
        commands.put("help", new Help(collection));
        commands.put("exit", new Exit(collection));
        commands.put("info", new Info(collection));
        commands.put("show", new Show(collection));
        commands.put("history", new History(collection, history));
        commands.put("clear", new Clear(collection));
        commands.put("add", new Add(collection, scanner));
        commands.put("update id", new Update(collection));
        commands.put("add_if_min", new AddIf(collection, scanner));
    }

    public void execute(String input) {
        if (commands.containsKey(input)) {
            commands.get(input).execute(input);
            history.add(input);
        } else if (commands.containsKey(input.replaceAll("\\s+\\S+$", ""))){
            commands.get(input.replaceAll("\\s+\\S+$", "")).execute(input);
            history.add(input);
        } else {
            System.out.println("Unknown command. Write help.\n");
        }
    }
}
