package commands;

import managers.AnnounceManager;
import managers.CollectionManager;

import java.util.Scanner;

/**
 * Команда завершения работы.
 */
public class Exit extends Command{

    //constructor
    public Exit(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        println("goodbye");
        System.exit(1);
    }
}
