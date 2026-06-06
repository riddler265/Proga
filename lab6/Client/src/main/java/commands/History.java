package commands;

import util.ConsoleManager;

import java.util.Scanner;

public class History extends Command {

    private final stack.History history;

    public History(ConsoleManager consoleManager, stack.History history) {
        super(consoleManager);
        this.history = history;
    }

    @Override
    public void execute(String input, Scanner scanner) {
        for (String command : history.getHistory()) {
            if (command != null)System.out.println(command);
        }
        System.out.println();
    }
}
