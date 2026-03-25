package commands;

import collectionManager.CollectionManager;
import jsonmanager.JsonManager;

import java.util.Scanner;

/**
 * Команда сохранения коллекции в json файл.
 */
public class Save extends Command{

    //fields
    private final JsonManager jsonManager;

    //constructor
    public Save(CollectionManager collection, JsonManager jsonManager) {
        super(collection);
        this.jsonManager = jsonManager;
    }

    //execute

    @Override
    public void execute(String input, Scanner scanner) {
        jsonManager.save();
    }
}
