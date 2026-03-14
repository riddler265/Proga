package commands;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import collection.Manager;
import commandManager.CommandManager;

public class History extends Command{

    //fields
    private final CommandManager commandManager;

    //constructor
    public History(Manager collection, CommandManager commandManager) {
        super(collection);
        this.commandManager = commandManager;
    }

    //execute
    @Override
    public void execute(String input) {
        System.out.println();
        LinkedList<String> dopleganger = new LinkedList<>(commandManager.getHistory());
        for (String command : dopleganger) {
            System.out.println(command);
        }
        System.out.println();
    }

}
