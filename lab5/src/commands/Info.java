package commands;

import managers.AnnounceManager;
import managers.CollectionManager;

import java.util.Scanner;

/**
 * Команда, выводящая информацию о коллекции.
 */
public class Info extends Command{

    //constructor
    public Info(CollectionManager collectionManager) {
        super(collectionManager);
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println(collectionManager.getInfo());
    }
}
