package commands;

import managers.AnnounceManager;
import managers.CollectionManager;
import model.Product;

import java.util.Scanner;

/**
 * <p>
 *     Команда, возвращающая все элементы коллекции,
 *     у которых поле manufactureCost превышает заданное значение.
 * </p>
 */
public class FilterGreaterThanManufactureCost extends FilterLessThanManufactureCost{

    //constructor
    public FilterGreaterThanManufactureCost(CollectionManager collectionManager) {
        super(collectionManager);
    }

    //execute
    public void execute(String input, Scanner scanner) {
        try {
            currentManufactureCost = Float.parseFloat(input.substring(input.lastIndexOf(" ") + 1));
            if (currentManufactureCost > 0) {
                for (Product product : collectionManager.getCollection()) {
                    if (product.getManufactureCost() > currentManufactureCost) {
                        System.out.println(product);
                    }
                }
                System.out.println();
            } else throw new NumberFormatException();
        } catch (NumberFormatException e) {
            println("remove.by.price.condition");
        }
    }
}
