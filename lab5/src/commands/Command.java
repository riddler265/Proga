package commands;

import collection.Manager;
import commandManager.CommandManager;
import exceptions.IncorrectInputException;

public abstract class Command {

    //fields
    protected final Manager collection;

    //constructor
    public Command(Manager collection) {
        this.collection = collection;
    }

    public abstract void execute(String input);
}
