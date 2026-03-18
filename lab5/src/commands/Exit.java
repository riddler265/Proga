package commands;

import collection.Manager;
import commandManager.CommandManager;

import java.util.Scanner;

public class Exit extends Command{

    //constructor
    public Exit(Manager collection) {
        super(collection);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        System.exit(1);
    }
}
