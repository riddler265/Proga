package commands;

import collection.Manager;

import java.util.Scanner;

public class AddIf extends Add {

    //smallestProduct


    //constructor
    public AddIf(Manager collection, Scanner scanner) {
        super(collection, scanner);
    }

    //execute
    @Override
    public void execute(String input) {

        //coordinates
        createCoordinates();

        //person
        createPerson();

        //product
        createProduct();

        if (collection.getLowestProduct() == null && finalProduct.compareTo(collection.getLowestProduct()) < 0) {
            collection.addToCollection(finalProduct);
            System.out.println("Продукт успешно добавлен\n");
        } else System.out.println("Продукт превышает наименьший элемент коллекции\n");
    }

}
