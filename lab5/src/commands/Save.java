package commands;

import managers.AnnounceManager;
import managers.CollectionManager;
import managers.json.JsonManager;

import java.io.IOException;
import java.util.Scanner;

/**
 * Команда сохранения коллекции в json файл.
 */
public class Save extends Command{

    //fields
    private final JsonManager jsonManager;

    //constructor
    public Save(CollectionManager collectionManager, JsonManager jsonManager) {
        super(collectionManager);
        this.jsonManager = jsonManager;
    }

    //execute

    @Override
    public void execute(String input, Scanner scanner) {
            jsonManager.save(collectionManager.getCollection());
    }
}
