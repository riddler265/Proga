package commands;

import collectionManager.CollectionManager;
import commandManager.CommandManager;
import product.Product;

import java.util.Scanner;

public class RemoveGreater extends RemoveById {

    //fields
    private final CommandManager commandManager;

    //constructor
    public RemoveGreater(CollectionManager collection, CommandManager commandManager) {
        super(collection);
        this.commandManager = commandManager;
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        commandManager.execute("show", scanner);
        System.out.print("Введите id продукта: ");
        try {
            currentId = Integer.parseInt(scanner.nextLine());
            currentProduct = collection.getProductById(currentId);
            if (currentProduct != null) {
                for (Product product : collection.getCollection()) {
                    if (currentProduct.compareTo(product) > 0) {
                        collection.removeFromCollection(product);
                    }
                }
                System.out.println("Все продукты, больше заданного удалены\n");
            } else throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Продукта с таким id нет\n");
        }
    }


}
