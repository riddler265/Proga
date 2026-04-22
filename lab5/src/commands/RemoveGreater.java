package commands;

import managers.AnnounceManager;
import managers.CollectionManager;
import managers.CommandManager;

import java.util.Scanner;

/**
 * Команда, удаляющая элементы, превосходящие заданный.
 */
public class RemoveGreater extends RemoveById {

    //fields
    private final CommandManager commandManager;

    //constructor
    public RemoveGreater(CollectionManager collectionManager, CommandManager commandManager) {
        super(collectionManager);
        this.commandManager = commandManager;
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        commandManager.execute("show", scanner);
        println("write.id");
        try {
            currentId = Integer.parseInt(scanner.nextLine());
            currentProduct = collectionManager.getProductById(currentId);
            if (currentProduct != null) {
               collectionManager.getCollection().removeIf(product -> currentProduct.compareTo(product) > 0);
                println("remove.greater.success");
            } else throw new NumberFormatException();
        } catch (NumberFormatException e) {
            println("no.element.id");
        }
    }
}
