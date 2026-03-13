package commands;

import collection.Manager;

public class Exit extends Command{

    //constructor
    public Exit(Manager collection) {
        super(collection);
    }

    @Override
    public void execute(String input) {
        System.exit(1);
    }
}
