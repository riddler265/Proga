package commands;

import managers.CollectionManager;
import managers.CommandManager;
import model.Coordinates;
import model.Person;
import model.enums.UnitOfMeasure;
import exceptions.ExecuteException;
import exceptions.IncorrectInputException;
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
            } else System.out.println("Продукт превышает наименьший элемент коллекции.\n");

        } catch (NullPointerException e) {
            collectionManager.addToCollection((finalProduct));
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        }
    }
}
