package commands;

import collectionManager.CollectionManager;
import product.Product;

import java.util.Scanner;

/**
 * <p>
 *     Команда, возвращающая все элементы коллекции,
 *     у которых поле manufactureCost меньше заданного значения.
 * </p>
 */
public class FilterLessThanManufactureCost extends Command{

    //fields
    protected float currentManufactureCost;

    //constructor
    public FilterLessThanManufactureCost(CollectionManager collection) {
        super(collection);
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        try {
            currentManufactureCost = Float.parseFloat(input.substring(input.lastIndexOf(" ") + 1));
            if (currentManufactureCost > 0) {
                for (Product product : collection.getCollection()) {
                    if (product.getManufactureCost() < currentManufactureCost) {
                        System.out.println(product);
                    }
                }
                System.out.println();
            } else throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Число должно быть больше нуля.\n");
        }
    }
}
