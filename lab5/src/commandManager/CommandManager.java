package commandManager;

import collection.Manager;
import commands.Command;
import commands.Exit;
import commands.Help;
import commands.History;
import commands.Info;
import commands.Show;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class CommandManager {

    //fields
    private final Map<String, Command> commands = new HashMap<>();
    private final LinkedList<String> history = new LinkedList<>();
    //private final BlockingQueue<String> history = new LinkedBlockingQueue<>(8);

    //constructor
    public CommandManager(Manager collection) {
        commands.put("help", new Help(collection));
        commands.put("exit", new Exit(collection));
        commands.put("info", new Info(collection));
        commands.put("show", new Show(collection));
        commands.put("history", new History(collection, this));
    }

    //getters
    public LinkedList<String> getHistory() {
        return history;
    }

    //successCommand
    private void successCommand(String command) {
        if (history.size() == 8) {
            history.remove(0);
            for (int i = 0; i < 7; i++) {
                history.set(i, history.get(i+1));
            }
            history.set(8,command);
        } else {
            history.add(command);
        }
    }

    public void execute(String input) {
        if (commands.containsKey(input)) {
            commands.get(input).execute(input);
            successCommand(input);
        } else {
            System.out.println("Unknown command. Write help.\n");
        }
    }
}
