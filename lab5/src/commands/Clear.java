package commands;

import collectionManager.CollectionManager;

import java.util.Scanner;

public class Clear extends Command{

    //constructor
    public Clear(CollectionManager collection) {
        super(collection);
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println();
        collection.getCollection().clear();
        System.out.println("Коллекция очищена.\n");
    }
}
