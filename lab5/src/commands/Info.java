package commands;

import collectionManager.CollectionManager;

import java.util.Scanner;

public class Info extends Command{

    //constructor
    public Info(CollectionManager collection) {
        super(collection);
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println(collection.getInfo());
    }
}
