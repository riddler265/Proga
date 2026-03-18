package commands;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import collection.Manager;
import commandManager.CommandManager;

public class History extends Command{

    //fields
    private final history.History history;

    //constructor
    public History(Manager collection, history.History history) {
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
