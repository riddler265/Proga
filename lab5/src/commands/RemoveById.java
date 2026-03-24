package commands;

import collectionManager.CollectionManager;
import product.Product;

import java.util.Scanner;

public class RemoveById extends Command{

    //fields
    protected int currentId;
    protected Product currentProduct;

    //constructor
    public RemoveById(CollectionManager collection) {
        super(collection);
    }

    //execute

    @Override
    public void execute(String input, Scanner scanner) {
        try {
            currentId = Integer.parseInt(input.substring(input.lastIndexOf(" ") + 1));
            currentProduct = collection.getProductById(currentId);
            collection.removeFromCollection(currentProduct);
        } catch (NumberFormatException e) {
            System.out.println("Продукта с таким id нет\n");
        }
    }
}
