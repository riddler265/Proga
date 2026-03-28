package commands;

import java.util.Scanner;

import managers.CollectionManager;

/**
 * Команда, выводящая в консоль последние 8 команд
 */
public class History extends Command{

    //fields
    private final history.History history;

    //constructor
    public History(CollectionManager collection, history.History history) {
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
