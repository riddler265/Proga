package commands;

import collection.Manager;
import commandManager.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Execute extends Command{

    //fields
    private File path;
    private final CommandManager commandManager;

    //constructor
    public Execute(Manager collection, CommandManager commandManager) {
        super(collection);
        this.commandManager = commandManager;
    }

    //execute
    @Override
    public void execute(String input, Scanner currentScanner) {
        path = new File("scripts", input.substring(input.lastIndexOf(" ") + 1));
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                commandManager.execute(scanner.nextLine(), scanner);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + path.getAbsolutePath() + "\n");
        }
    }
}
