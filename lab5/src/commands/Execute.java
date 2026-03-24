package commands;

import collectionManager.CollectionManager;
import commandManager.CommandManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Execute extends Command{

    //fields
    private File path;
    private final CommandManager commandManager;

    //constructor
    public Execute(CollectionManager collection, CommandManager commandManager) {
        super(collection);
        this.commandManager = commandManager;
    }

    //execute
    @Override
    public void execute(String input, Scanner currentScanner) {
        path = new File("scripts", input.substring(input.lastIndexOf(" ") + 1));
        try (Scanner scanner = new Scanner(path)) {
            commandManager.setIsSystemReader(false);
            while (scanner.hasNextLine()) {
                commandManager.execute(scanner.nextLine(), scanner);
            }
            commandManager.setIsSystemReader(true);
            System.out.println("Файл исполнен\n");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + path.getAbsolutePath() + "\n");
        }
    }
}
