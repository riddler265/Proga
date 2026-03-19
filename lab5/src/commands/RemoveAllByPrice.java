package commands;

import collection.Manager;
import commandManager.CommandManager;
import product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RemoveAllByPrice extends Command{

    //fields
    private float currentPrice;
    private final List<Product> toRemove= new ArrayList<>();

    //constructor
    public RemoveAllByPrice(Manager collection) {
        super(collection);
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        try {
            currentPrice = Float.parseFloat(input.substring(input.lastIndexOf(" ") + 1));
            if (currentPrice > 0.0) {
                for (Product product : collection.getCollection()) {
                    System.out.println(product.getPrice());
                    if (product.getPrice() == currentPrice) {
                        toRemove.add(product);
                    }
                }
                for (Product product : toRemove) {
                    collection.removeFromCollection(product);
                }
                toRemove.clear();
            } else throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Нужно ввести положительное число.");
        }
    }
}
