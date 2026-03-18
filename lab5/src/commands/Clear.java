package commands;

import collection.Manager;
import commandManager.CommandManager;

public class Clear extends Command{

    //constructor
    public Clear(Manager collection) {
        super(collection);
    }

    //execute
    @Override
    public void execute(String input) {
        System.out.println();
        collection.getCollection().clear();
        System.out.println("Collection is clear now\n");
    }
}
