package commands;

import managers.CollectionManager;
import model.Product;

import java.util.Scanner;

/**
 * Удаление элемента коллекции по полю id.
 */
public class RemoveById extends Command{

    //fields
    protected int currentId;
    protected Product currentProduct;

    //constructor
    public RemoveById(CollectionManager collectionManager) {
        super(collectionManager);
    }

    //execute

    @Override
    public void execute(String input, Scanner scanner) {
        try {
            currentId = Integer.parseInt(input.substring(input.lastIndexOf(" ") + 1));
            currentProduct = collectionManager.getProductById(currentId);
            collectionManager.removeFromCollection(currentProduct);
        } catch (NumberFormatException e) {
            System.out.println("Продукта с таким id нет.\n");
        }
    }
}
