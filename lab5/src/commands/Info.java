package commands;

import managers.CollectionManager;

import java.util.Scanner;

/**
 * Команда, выводящая информацию о коллекции.
 */
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
