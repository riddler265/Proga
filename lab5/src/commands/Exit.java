package commands;

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
        System.out.println("До свидания!\n");
        System.exit(1);
    }
}
