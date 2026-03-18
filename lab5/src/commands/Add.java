package commands;

import collection.Manager;
import commandManager.CommandManager;
import coordinates.Coordinates;
import enums.Color;
import enums.UnitOfMeasure;
import exceptions.IncorrectInputException;
import person.Person;
import product.Product;

import java.util.Locale;
import java.util.Scanner;

public class Add extends Command {

    //fields
    //product
    protected String productName;
    protected Float price;
    protected String partNumber;
    protected float manufactureCost;
    protected UnitOfMeasure unitOfMeasure;

    //coordinates
    protected Integer x;
    protected Integer y;

    //person
    protected String personName;
    protected float height;

    //scanner
    protected final Scanner scanner;

    //finalObjectts
    protected Product finalProduct;
    protected Coordinates finalCoordinates;
    protected Person finalOwner;

    //constructor
    public Add(Manager collection, Scanner scanner) {
        super(collection);
        this.scanner = scanner;
    }

    //conditions
    protected boolean needOwner(String input) {
        if (input.equalsIgnoreCase("yes")) return true;
        else if (input.equalsIgnoreCase("no")) return false;
        else {
            System.out.print("Введите yes/no: ");
            needOwner(scanner.nextLine());
            return false;
        }
    }

    //writing

    protected void writeProductName(String input) {
        if (input == null || input.isEmpty()) {
            System.out.print("Строка не может быть пустой. Введите имя: ");
            writeProductName(scanner.nextLine());
        } else productName = input;
    }

    protected void writePrice(String input) {
        try {
            price = Float.parseFloat(input);
            if (price <= 0.0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.print("Введите число больше 0: ");
            writePrice(scanner.nextLine());
        }
    }

    protected void writePartNumber(String input) {
        if (input.isEmpty()) partNumber = null;
        else partNumber = input;
    }

    protected void writeManufactureCost(String input) {
        try {
            manufactureCost = Float.parseFloat(input);
        } catch (NumberFormatException e) {
            System.out.print("Введите число: ");
            writeManufactureCost(scanner.nextLine());
        }
    }

    protected void writeUnitOfMeasure(String input) {
        try {
            unitOfMeasure = UnitOfMeasure.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Введите единицу измерения. " + UnitOfMeasure.units() + ":");
            writeUnitOfMeasure(scanner.nextLine());
        }
    }

    protected void writeCoordinateX(String input) {
        try {
            x = Integer.parseInt(input);
            if (x <= -645) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.print("Введите целое число больше -645: ");
            writeCoordinateX(scanner.nextLine());
        }
    }

    protected void writeCoordinateY(String input) {
        try {
            y = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.print("Введите целое число: ");
            writeCoordinateY(scanner.nextLine());
        }
    }

    protected void writePersonName(String input) {
        if (input == null || input.isEmpty()) {
            System.out.print("Строка не может быть пустой. Введите имя: ");
            writePersonName(scanner.nextLine());
        } else personName = input;
    }

    protected void writeHeight(String input) {
        try {
            height = Float.parseFloat(input);
            if (height <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.print("Введите число больше 0: ");
            writeHeight(scanner.nextLine());
        }
    }

    //create
    protected void createPerson() {
        System.out.print("\nБудет ли у продукта владелец? yes/no: ");
        if (needOwner(scanner.nextLine())) {
            System.out.print("\nВведите имя владельца: ");
            writePersonName(scanner.nextLine());
            System.out.print("\nВведите рост владельца больше 0: ");
            writeHeight(scanner.nextLine());

            try {
                finalOwner = new Person(personName, height, null, Color.BLACK);
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
                createPerson();
            }
        } else finalOwner = null;
    }

    protected void createCoordinates() {
        System.out.print("\nВведите целое число больше -645 - координату X: ");
        writeCoordinateX(scanner.nextLine());
        System.out.print("\nВведите целое число - координату Y: ");
        writeCoordinateY(scanner.nextLine());
        try {
            finalCoordinates = new Coordinates(x, y);
        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            createCoordinates();
        }
    }

    protected void createProduct() {
        System.out.print("\nВведите название продукта: ");
        writeProductName(scanner.nextLine());
        System.out.print("\nВведите цену продукта больше 0: ");
        writePrice(scanner.nextLine());
        System.out.print("\nВведите номер партии: ");
        writePartNumber(scanner.nextLine());
        System.out.print("\nВведите стоимость производства продукта: ");
        writeManufactureCost(scanner.nextLine());
        System.out.println("\nВведите единицу измерения. " + UnitOfMeasure.units() + ":");
        writeUnitOfMeasure(scanner.nextLine());

        try {
            finalProduct = new Product(productName, finalCoordinates, price, partNumber, manufactureCost, unitOfMeasure, finalOwner);
            collection.addToCollection(finalProduct);
        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            System.out.println("Возникла ошибка. Попробуйте снова.");
            execute("add");
        }
    }


    //execute
    @Override
    public void execute(String input) {

        //createCoordinates
        createCoordinates();

        //createOwner
        createPerson();

        //createProduct
        createProduct();
        System.out.println();
    }
}
