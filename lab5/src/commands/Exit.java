package commands;

import collectionManager.CollectionManager;

import java.util.Scanner;

public class Exit extends Command{

    //constructor
    public Exit(CollectionManager collection) {
        super(collection);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println("До свидания!\n");
        System.exit(1);
    }
}
