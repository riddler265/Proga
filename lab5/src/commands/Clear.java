package commands;

import managers.CollectionManager;

import java.util.Scanner;

/**
 * Команда очищения коллекции.
 */
public class Clear extends Command{

    //constructor
    public Clear(CollectionManager collectionManager) {
        super(collectionManager);
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println();
        collectionManager.getCollection().clear();
        System.out.println("Коллекция очищена.\n");
    }
}
