package commands;

import managers.CollectionManager;
import managers.CommandManager;
import model.Coordinates;
import model.enums.Color;
import model.enums.Strategy;
import model.enums.UnitOfMeasure;
import exceptions.ExecuteException;
import exceptions.IncorrectInputException;
import model.Person;
import model.Product;

import java.util.Scanner;

/**
 * Команда, добавляющая элемент в коллекцию.
 */
public class Add extends Command {

    //fields
    //commandManager
    protected final CommandManager commandManager;
    protected boolean isSystemReader;

    //finalObjects
    protected Product finalProduct;
    protected Coordinates finalCoordinates;
    protected Person finalOwner;

    //constructor
    public Add(CollectionManager collection, CommandManager commandManager) {
        super(collection);
        this.commandManager = commandManager;
    }

    //region helpers
    //conditions
    protected Strategy needOwner(String input, Scanner scanner) throws ExecuteException {
        if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) return Strategy.Y;
        else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) return Strategy.N;
        else if (isSystemReader) {
            System.out.print("Введите yes/no: ");
            return needOwner(scanner.nextLine(), scanner);
        } else throw new ExecuteException(getClass().getSimpleName());
    }

    //announce
    protected void announce(String message, String conditions) {
        if (isSystemReader) {
            String suffix = java.util.Optional.ofNullable(conditions)
                    .filter(c -> !c.isBlank())
                    .map(c -> " (" + c + ")")
                    .orElse("");

            System.out.print("\n" + message + suffix + ": ");
        }
    }//endregion

    //region writing
    //product
    protected void writeProductName(String input, Scanner scanner) throws ExecuteException {
        try {
            finalProduct.setName(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeProductName(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writePrice(String input, Scanner scanner) throws ExecuteException {
        try {
            finalProduct.setPrice(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writePrice(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writePartNumber(String input, Scanner scanner) throws ExecuteException {
        try {
            finalProduct.setPartNumber(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writePartNumber(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeManufactureCost(String input, Scanner scanner) throws ExecuteException {
        try {
            finalProduct.setManufactureCost(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeManufactureCost(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeUnitOfMeasure(String input, Scanner scanner) throws ExecuteException {
        try {
            finalProduct.setUnitOfMeasure(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeUnitOfMeasure(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    //coordinates
    protected void writeCoordinateX(String input, Scanner scanner) throws ExecuteException {
        try {
            finalCoordinates.setX(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeCoordinateX(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeCoordinateY(String input, Scanner scanner) throws ExecuteException{
        try {
            finalCoordinates.setY(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeCoordinateY(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    //person
    protected void writePersonName(String input, Scanner scanner) throws ExecuteException {
        try {
            finalOwner.setName(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writePersonName(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeBirthday(String input, Scanner scanner) {
        try {
            finalOwner.setBirthday(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeBirthday(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeHeight(String input, Scanner scanner) throws ExecuteException{
        try {
            finalOwner.setHeight(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeHeight(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writePassportID(String input, Scanner scanner) {
        finalOwner.setPassportID(input);
    }

    protected void writeHairColor(String input, Scanner scanner) {
        try {
            finalOwner.setHairColor(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeHairColor(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }//endregion

    //region create
    protected void createPerson(Scanner scanner) throws ExecuteException {

        announce("Будет ли у продукта владелец?", "yes/no");
        switch (needOwner(scanner.nextLine(), scanner)) {
            case Y:
                announce("Введите имя владельца","");
                writePersonName(scanner.nextLine(), scanner);
                announce("Введите дату рождения владельца", "dd-MM-yyyy HH:mm:ss");
                writeBirthday(scanner.nextLine(), scanner);
                announce("Введите рост владельца", "больше 0, округление до 5 знаков");
                writeHeight(scanner.nextLine(), scanner);
                announce("Введите данные паспорта", "Null/строка");
                writePassportID(scanner.nextLine(), scanner);
                announce("Введите цвет волос владельца", Color.getColorsInfo());
                System.out.println();
                writeHairColor(scanner.nextLine(), scanner);
                break;
            case N, S:
                finalOwner = null;
        }
    }

    protected void createCoordinates(Scanner scanner) throws ExecuteException {

        announce("Введите координату X", "целое число больше -645");
        writeCoordinateX(scanner.nextLine(), scanner);
        announce("Введите координату Y", "целое число");
        writeCoordinateY(scanner.nextLine(), scanner);
    }

    protected void createProduct(Scanner scanner) throws ExecuteException {

        announce("Введите название продукта", "не пустая строка");
        writeProductName(scanner.nextLine(), scanner);
        announce("Введите цену продукта больше 0", "Null/число больше 0, округление до 5 знаков");
        writePrice(scanner.nextLine(), scanner);
        announce("Введите номер партии", "Null/не пустая строка");
        writePartNumber(scanner.nextLine(), scanner);
        announce("Введите стоимость производства продукта", "число, округление до 5 знаков");
        writeManufactureCost(scanner.nextLine(), scanner);
        announce("Введите единицу измерения", UnitOfMeasure.getUnitsInfo());
        System.out.println();
        writeUnitOfMeasure(scanner.nextLine(), scanner);

        finalProduct.setOwner(finalOwner).setCoordinates(finalCoordinates);
    }//endregion


    //execute
    @Override
    public void execute(String input, Scanner scanner) {

        isSystemReader = commandManager.isSystemReader();

        finalProduct = new Product();
        finalOwner = new Person();
        finalCoordinates = new Coordinates();

        try {
            createCoordinates(scanner);
            createPerson(scanner);
            createProduct(scanner);
            System.out.println();
            collectionManager.addToCollection(finalProduct);
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        }
    }
}
