package commands;

import collection.Manager;
import commandManager.CommandManager;
import coordinates.Coordinates;
import enums.Color;
import enums.UnitOfMeasure;
import exceptions.ExecuteException;
import exceptions.IncorrectInputException;
import person.Person;
import product.Product;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Add extends Command {

    //fields
    //commandManager
    protected final CommandManager commandManager;

    //product
    protected String productName;
    protected Float price;
    protected String partNumber;
    protected float manufactureCost;
    protected UnitOfMeasure unitOfMeasure;

    //scanner
    protected boolean isSystemReader;

    //coordinates
    protected Integer x;
    protected Integer y;

    //person
    protected String personName;
    protected float height;

    //finalObjects
    protected Product finalProduct;
    protected Coordinates finalCoordinates;
    protected Person finalOwner;

    //constructor
    public Add(Manager collection, CommandManager commandManager) {
        super(collection);
        this.commandManager = commandManager;
    }

    //conditions
    protected boolean needOwner(String input, Scanner scanner) throws ExecuteException {
        if (input.equalsIgnoreCase("yes")) return true;
        else if (input.equalsIgnoreCase("no")) return false;
        else if (isSystemReader) {
            System.out.print("Введите yes/no: ");
            return needOwner(scanner.nextLine(), scanner);
        }
        throw new ExecuteException(getClass().getSimpleName());
    }

    //writing
    protected void writeProductName(String input, Scanner scanner) throws ExecuteException {
        if (!(input == null || input.isEmpty())) productName = input;
        else if (isSystemReader) {
            System.out.print("Строка не может быть пустой. Введите имя: ");
            writeProductName(scanner.nextLine(), scanner);
        }
        throw new ExecuteException(getClass().getSimpleName());
    }

    protected void writePrice(String input, Scanner scanner) throws ExecuteException {
        if (input.isEmpty()) {
            price = null;
            return;
        }
        try {
            price = Float.parseFloat(input);
            if (price <= 0.0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            if (isSystemReader) {
                System.out.print("Введите число больше 0: ");
                writePrice(scanner.nextLine(), scanner);
            } throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writePartNumber(String input, Scanner scanner) throws ExecuteException {
        if (input.isEmpty()) partNumber = null;
        else partNumber = input;
    }

    protected void writeManufactureCost(String input, Scanner scanner) throws ExecuteException {
        try {
            manufactureCost = Float.parseFloat(input);
        } catch (NumberFormatException e) {
            if (isSystemReader) {
                System.out.print("Введите число: ");
                writeManufactureCost(scanner.nextLine(), scanner);
            } throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeUnitOfMeasure(String input, Scanner scanner) throws ExecuteException {
        try {
            unitOfMeasure = UnitOfMeasure.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            if (isSystemReader) {
                System.out.println("Введите единицу измерения. " + UnitOfMeasure.units() + ":");
                writeUnitOfMeasure(scanner.nextLine(), scanner);
            } throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeCoordinateX(String input, Scanner scanner) throws ExecuteException {
        try {
            x = Integer.parseInt(input);
            if (x <= -645) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            if (isSystemReader) {
                System.out.print("Введите целое число больше -645: ");
                writeCoordinateX(scanner.nextLine(), scanner);
            } throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeCoordinateY(String input, Scanner scanner) throws ExecuteException{
        try {
            y = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            if (isSystemReader) {
                System.out.print("Введите целое число: ");
                writeCoordinateY(scanner.nextLine(), scanner);
            } throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writePersonName(String input, Scanner scanner) throws ExecuteException {
        if (!(input == null || input.isEmpty())) {
            personName = input;
        } else if (isSystemReader) {
            System.out.print("Строка не может быть пустой. Введите имя: ");
            writePersonName(scanner.nextLine(), scanner);
        } else {
            throw new ExecuteException("add");
        }
    }

    protected void writeHeight(String input, Scanner scanner) throws ExecuteException{
        try {
            height = Float.parseFloat(input);
            if (height <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            if (isSystemReader) {
                System.out.print("Введите число больше 0: ");
                writeHeight(scanner.nextLine(), scanner);
            } throw new ExecuteException(getClass().getSimpleName());
        }
    }

    //create
    protected void createPerson(Scanner scanner) throws ExecuteException {
        try {
            System.out.print("\nБудет ли у продукта владелец? yes/no: ");
            if (needOwner(scanner.nextLine(), scanner)) {
                System.out.print("\nВведите имя владельца: ");
                writePersonName(scanner.nextLine(), scanner);
                System.out.print("\nВведите рост владельца больше 0: ");
                writeHeight(scanner.nextLine(), scanner);

                finalOwner = new Person(personName, height, null, Color.BLACK);
            } else finalOwner = null;
        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            createPerson(scanner);
        }
    }

    protected void createCoordinates(Scanner scanner) throws ExecuteException {
        try {
            System.out.print("\nВведите целое число больше -645 - координату X: ");
            writeCoordinateX(scanner.nextLine(), scanner);
            System.out.print("\nВведите целое число - координату Y: ");
            writeCoordinateY(scanner.nextLine(), scanner);

            finalCoordinates = new Coordinates(x, y);
        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            createCoordinates(scanner);
        }
    }

    protected void createProduct(Scanner scanner) throws ExecuteException {
        try {
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

            finalProduct = new Product(productName, finalCoordinates, price, partNumber, manufactureCost, unitOfMeasure, finalOwner);
            collection.addToCollection(finalProduct);
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
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        }
    }
}
