package commands;

import collectionManager.CollectionManager;
import commandManager.CommandManager;
import enums.UnitOfMeasure;
import exceptions.ExecuteException;
import exceptions.IncorrectInputException;
import product.Product;

import java.util.Scanner;

public class AddIfMin extends Add {

    //constructor
    public AddIfMin(CollectionManager collection, CommandManager commandManager) {
        super(collection, commandManager);
    }

    //create
    @Override
    protected void createProduct(Scanner scanner) {
        System.out.print("\nВведите название продукта: ");
        writeProductName(scanner.nextLine(), scanner);
        System.out.print("\nВведите цену продукта больше 0: ");
        writePrice(scanner.nextLine(), scanner);
        System.out.print("\nВведите номер партии: ");
        writePartNumber(scanner.nextLine(), scanner);
        System.out.print("\nВведите стоимость производства продукта: ");
        writeManufactureCost(scanner.nextLine(), scanner);
        System.out.println("\nВведите единицу измерения. " + UnitOfMeasure.units() + ":");
        writeUnitOfMeasure(scanner.nextLine(), scanner);

        try {
            finalProduct = new Product(productName, finalCoordinates, price, partNumber, manufactureCost, unitOfMeasure, finalOwner);
        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            System.out.println("Возникла ошибка. Попробуйте снова.");
            execute("add", scanner);
        }
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {

        isSystemReader = commandManager.isSystemReader();

        try {
            //createCoordinates
            createCoordinates(scanner);

            //createOwner
            createPerson(scanner);

            //createProduct
            createProduct(scanner);
            System.out.println();

            if (collection.getLowestProduct() == null || finalProduct.compareTo(collection.getLowestProduct()) < 0) {
                collection.addToCollection(finalProduct);
            } else System.out.println("Продукт превышает наименьший элемент коллекции\n");

        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        }
    }
}
