package commands;

import managers.AnnounceManager;
import managers.CollectionManager;
import managers.CommandManager;
import model.Coordinates;
import model.Person;
import exceptions.ExecuteException;
import model.Product;

import java.util.Scanner;

/**
 * Команда, добавляющая элемент в коллекцию, если он будет являться наименьшим в ней.
 */
public class AddIfMin extends Add {

    //constructor
    public AddIfMin(CollectionManager collection, CommandManager commandManager) {
        super(collection, commandManager);
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {

        isSystemReader = commandManager.isSystemReader();

        finalProduct = new Product();
        finalOwner = new Person();
        finalCoordinates = new Coordinates();

        try {
            createCoordinates(scanner);
            createPerson(scanner);
            createProduct(scanner);
            System.out.println();

            if (finalProduct.compareTo(collectionManager.getLowestProduct()) < 0) {
                collectionManager.addToCollection(finalProduct);
            } else println("addIM.failure");

        } catch (NullPointerException e) {
            collectionManager.addToCollection((finalProduct));
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        }
    }
}
