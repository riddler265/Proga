package commands;

import java.util.Scanner;

import managers.AnnounceManager;
import managers.CollectionManager;

/**
 * Команда, выводящая в консоль последние 8 команд
 */
public class History extends Command{

    //fields
    private final stack.History history;

    //constructor
    public History(CollectionManager collection, stack.History history) {
        super(collection);
        this.history = history;
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println();
        for (String command : history.getHistory()) {
            if (command != null)System.out.println(command);
        }
        System.out.println();
    }

}
