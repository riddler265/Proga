package commands;

import collection.Manager;
import enums.UnitOfMeasure;
import exceptions.IncorrectInputException;
import product.Product;

import java.util.Scanner;

public class AddIf extends Add {

    //smallestProduct


    //constructor
    public AddIf(Manager collection) {
        super(collection);
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

        //coordinates
        createCoordinates(scanner);

        //person
        createPerson(scanner);

        //product
        createProduct(scanner);

        if (collection.getLowestProduct() == null || finalProduct.compareTo(collection.getLowestProduct()) < 0) {
            collection.addToCollection(finalProduct);
        } else System.out.println("Продукт превышает наименьший элемент коллекции\n");
    }
}
