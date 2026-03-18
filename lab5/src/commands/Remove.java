package commands;

import collection.Manager;
import product.Product;

public class Remove extends Command{

    //fields
    private int currentId;
    private Product currentProduct;

    //constructor
    public Remove(Manager collection) {
        super(collection);
    }

    //execute

    @Override
    public void execute(String input) {
        try {
            currentId = Integer.parseInt(input.substring(input.lastIndexOf(" ") + 1));
            currentProduct = collection.getProductById(currentId);
            collection.removeFromCollectiob(currentProduct);
            System.out.println("Продукт №" + currentId + " удален\n");
        } catch (NumberFormatException e) {
            System.out.println("Продукта с таким id нет\n");
        }
    }
}
