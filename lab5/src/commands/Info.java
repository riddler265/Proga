package commands;

import collection.Manager;

public class Info extends Command{

    //constructor
    public Info(Manager collection) {
        super(collection);
    }

    //execute
    @Override
    public void execute(String inout) {
        System.out.println(collection.getInfo());
    }
}
