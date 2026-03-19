package commands;

import collection.Manager;
import jsonmanager.JsonManager;

import java.util.Scanner;

public class Save extends Command{

    //fields
    private final JsonManager jsonManager;

    //constructor
    public Save(Manager collection, JsonManager jsonManager) {
        super(collection);
        this.jsonManager = jsonManager;
    }

    //execute

    @Override
    public void execute(String input, Scanner scanner) {
        jsonManager.save();
    }
}
