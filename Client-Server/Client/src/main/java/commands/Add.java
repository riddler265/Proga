package commands;

import enums.Commands;
import enums.Strategy;
import exceptions.ExecuteException;
import exceptions.IncorrectInputException;
import json.JsonManager;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Objects;
import java.util.Scanner;

public class Add extends Command {

    protected boolean isSystemReader;
    protected ClientRequestBuilder addBuilder;

    public Add(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    //region helpers
    //conditions
    protected Strategy needOwner(String input, Scanner scanner) throws ExecuteException {
        if (input.equalsIgnoreCase(format("yes")) || input.equalsIgnoreCase(format("y"))) return Strategy.Y;
        else if (input.equalsIgnoreCase(format("no")) || input.equalsIgnoreCase(format("n"))) return Strategy.N;
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
                            // Прогоняем каждый ключ через твой метод format
                            .map(this::format)
                            .collect(java.util.stream.Collectors.joining(", ")))
                    .filter(result -> !result.isEmpty())
                    .map(result -> "(" + result + ")")
                    .orElse("");

            print("announce", format(message_key), suffix);
            //System.out.print("\nВведите " + format(message_key) + suffix + ": ");
        }
    }//endregion

    //region writing
    //product
    protected void writeProductName(String input, Scanner scanner) throws ExecuteException {
        try {
            addBuilder.setName(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeProductName(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writePrice(String input, Scanner scanner) throws ExecuteException {
        try {
            addBuilder.setPrice(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writePrice(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writePartNumber(String input, Scanner scanner) throws ExecuteException {
        try {
            addBuilder.setPartNumber(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writePartNumber(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeManufactureCost(String input, Scanner scanner) throws ExecuteException {
        try {
            addBuilder.setManufactureCost(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeManufactureCost(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeUnitOfMeasure(String input, Scanner scanner) throws ExecuteException {
        try {
            addBuilder.setUnitOfMeasure(input);
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
            addBuilder.setX(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeCoordinateX(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeCoordinateY(String input, Scanner scanner) throws ExecuteException{
        try {
            addBuilder.setY(input);
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
            addBuilder.setPersonName(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writePersonName(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeBirthday(String input, Scanner scanner) {
        try {
            addBuilder.setBirthday(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeBirthday(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writeHeight(String input, Scanner scanner) throws ExecuteException{
        try {
            addBuilder.setHeight(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) {
                System.out.print(e.getMessage());
                writeHeight(scanner.nextLine(), scanner);
            } else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    protected void writePassportID(String input, Scanner scanner) {
        addBuilder.setPassportID(input);
    }

    protected void writeHairColor(String input, Scanner scanner) {
        try {
            addBuilder.setHairColor(input);
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
        if (Objects.requireNonNull(needOwner(scanner.nextLine(), scanner)) == Strategy.Y) {
            announce("owner.name", "not.empty.string.condition");
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
            addBuilder.setOwner();
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

        addBuilder.setCoordinates();
    }//endregion

    @Override
    public void execute(String input, Scanner scanner) {

        isSystemReader = consoleManager.isSystemReader();
        addBuilder = new ClientRequestBuilder(Commands.ADD);

        try {
            createCoordinates(scanner);
            createPerson(scanner);
            createProduct(scanner);

            toOutQueue(addBuilder.buildRequest());
            System.out.println(addBuilder.buildRequest());
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        }
    }
}
