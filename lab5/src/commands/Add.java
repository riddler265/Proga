package commands;

import managers.AnnounceManager;
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
        if (input.equalsIgnoreCase(cTCL("yes")) || input.equalsIgnoreCase(cTCL("y"))) return Strategy.Y;
        else if (input.equalsIgnoreCase(cTCL("no")) || input.equalsIgnoreCase(cTCL("n"))) return Strategy.N;
        else if (isSystemReader) {
            announce("need.owner", "yes", "no");
            return needOwner(scanner.nextLine(), scanner);
        } else throw new ExecuteException(getClass().getSimpleName());
    }

    //announce
    protected void announce(String message_key, String... condition_keys) {
        if (isSystemReader) {
            String suffix = java.util.Optional.ofNullable(condition_keys)
                    .filter(c -> c.length > 0)
                    .map(c -> java.util.Arrays.stream(c)
                            .filter(key -> key != null && !key.isBlank())
                            // Прогоняем каждый ключ через твой метод cTCL
                            .map(this::cTCL)
                            .collect(java.util.stream.Collectors.joining(", ")))
                    .filter(result -> !result.isEmpty())
                    .map(result -> "(" + result + ")")
                    .orElse("");

            print("announce", cTCL(message_key), suffix);
            //System.out.print("\nВведите " + cTCL(message_key) + suffix + ": ");
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

        announce("need.owner", "yes", "no");
        switch (needOwner(scanner.nextLine(), scanner)) {
            case Y:
                announce("owner.name","not.empty.string.condition");
                writePersonName(scanner.nextLine(), scanner);
                announce("owner.birthday", "date.condition");
                writeBirthday(scanner.nextLine(), scanner);
                announce("owner.height", "positive.condition", "rounding.condition");
                writeHeight(scanner.nextLine(), scanner);
                announce("owner.passport.id", "null", "string.condition");
                writePassportID(scanner.nextLine(), scanner);
                announce("owner.hair.color", "color.info");
                System.out.println();
                writeHairColor(scanner.nextLine(), scanner);
                break;
            case N:
                finalOwner = null;
        }
    }

    protected void createCoordinates(Scanner scanner) throws ExecuteException {

        announce("coordinate.x", "integer.condition", "bigger.than.minus.645");
        writeCoordinateX(scanner.nextLine(), scanner);
        announce("coordinate.y", "integer.condition");
        writeCoordinateY(scanner.nextLine(), scanner);
    }

    protected void createProduct(Scanner scanner) throws ExecuteException {

        announce("product.name", "not.empty.string.condition");
        writeProductName(scanner.nextLine(), scanner);
        announce("product.price", "null", "positive.condition", "rounding.condition");
        writePrice(scanner.nextLine(), scanner);
        announce("product.partNumber", "null", "not.empty.string.condition");
        writePartNumber(scanner.nextLine(), scanner);
        announce("product.manufactureCost", "rounding.condition");
        writeManufactureCost(scanner.nextLine(), scanner);
        announce("product.unitOfMeasure", "unit.info");
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
