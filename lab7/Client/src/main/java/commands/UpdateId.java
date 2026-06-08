package commands;

import enums.Commands;
import enums.Strategy;
import exceptions.ExecuteException;
import exceptions.IncorrectInputException;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class UpdateId extends Add {

    public UpdateId(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    // =========================================================
    // SKIP
    // =========================================================

    protected boolean skip(String input) {
        return input.trim().equalsIgnoreCase("\\s") || input.trim().equalsIgnoreCase("/s");
    }

    @Override
    protected Strategy needOwner(String input, Scanner scanner) throws ExecuteException {
        if (input.equalsIgnoreCase(format("yes")) || input.equalsIgnoreCase(format("y"))) return Strategy.Y;
        else if (input.equalsIgnoreCase(format("no")) || input.equalsIgnoreCase(format("n"))) return Strategy.N;
        else if (skip(input)) return Strategy.S;
        else if (isSystemReader) {
            announce("need.owner", "yes", "no");
            return needOwner(scanner.nextLine(), scanner);
        } else throw new ExecuteException(getClass().getSimpleName());
    }

    // =========================================================
    // WRITE PRODUCT
    // =========================================================

    @Override
    public void writeProductName(String input, Scanner scanner) throws ExecuteException {
        if (skip(input)) return;
        try {
            addBuilder.setName(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) { System.out.print(e.getMessage()); writeProductName(scanner.nextLine(), scanner); }
            else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    @Override
    public void writePrice(String input, Scanner scanner) throws ExecuteException {
        if (skip(input)) return;
        try {
            addBuilder.setPrice(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) { System.out.print(e.getMessage()); writePrice(scanner.nextLine(), scanner); }
            else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    @Override
    public void writePartNumber(String input, Scanner scanner) {
        if (skip(input)) return;
        super.writePartNumber(input, scanner);
    }

    @Override
    public void writeManufactureCost(String input, Scanner scanner) throws ExecuteException {
        if (skip(input)) return;
        try {
            addBuilder.setManufactureCost(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) { System.out.print(e.getMessage()); writeManufactureCost(scanner.nextLine(), scanner); }
            else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    @Override
    protected void writeUnitOfMeasure(String input, Scanner scanner) throws ExecuteException {
        if (skip(input)) return;
        super.writeUnitOfMeasure(input, scanner);
    }

    // =========================================================
    // WRITE COORDINATES
    // =========================================================

    @Override
    protected void writeCoordinateX(String input, Scanner scanner) {
        if (skip(input)) return;
        try {
            addBuilder.setX(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) { System.out.print(e.getMessage()); writeCoordinateX(scanner.nextLine(), scanner); }
        }
    }

    @Override
    protected void writeCoordinateY(String input, Scanner scanner) throws ExecuteException {
        if (skip(input)) return;
        try {
            addBuilder.setY(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) { System.out.print(e.getMessage()); writeCoordinateY(scanner.nextLine(), scanner); }
            else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    // =========================================================
    // WRITE PERSON
    // =========================================================

    @Override
    protected void writePersonName(String input, Scanner scanner) {
        if (skip(input)) return;
        super.writePersonName(input, scanner);
    }

    @Override
    protected void writeBirthday(String input, Scanner scanner) {
        if (skip(input)) return;
        super.writeBirthday(input, scanner);
    }

    @Override
    protected void writeHeight(String input, Scanner scanner) throws ExecuteException {
        if (skip(input)) return;
        try {
            addBuilder.setHeight(input);
        } catch (IncorrectInputException e) {
            if (isSystemReader) { System.out.print(e.getMessage()); writeHeight(scanner.nextLine(), scanner); }
            else throw new ExecuteException(getClass().getSimpleName());
        }
    }

    @Override
    protected void writePassportID(String input, Scanner scanner) {
        if (skip(input)) return;
        super.writePassportID(input, scanner);
    }

    @Override
    protected void writeHairColor(String input, Scanner scanner) {
        if (skip(input)) return;
        super.writeHairColor(input, scanner);
    }

    // =========================================================
    // CREATE PERSON
    // =========================================================

    @Override
    protected void createPerson(Scanner scanner) throws ExecuteException {
        // \s = оставить как есть, yes = установить нового, no = удалить
        announce("need.owner.update", "yes", "no", "skip");
        switch (needOwner(scanner.nextLine(), scanner)) {
            case Y:
                announce("owner.name", "not.empty.string.condition");
                writePersonName(scanner.nextLine(), scanner);
                announce("owner.birthday", "date.condition");
                writeBirthday(scanner.nextLine(), scanner);
                announce("owner.height", "positive.condition", "rounding.condition");
                writeHeight(scanner.nextLine(), scanner);
                announce("owner.passport.id", "null", "string.condition");
                writePassportID(scanner.nextLine(), scanner);
                announce("owner.hair.color", "color.info");
                writeHairColor(scanner.nextLine(), scanner);
                addBuilder.setOwner();         // ownerAction = set
                break;
            case N:
                addBuilder.setOwnerAction("remove"); // явно удалить
                break;
            case S:
                addBuilder.setOwnerAction("keep");   // не трогать
                break;
        }
    }

    // =========================================================
    // EXECUTE
    // =========================================================

    @Override
    public void execute(String input, Scanner scanner) {
        isSystemReader = consoleManager.isSystemReader();
        addBuilder = new ClientRequestBuilder(Commands.UPDATE_ID);

        try {
            addBuilder.setIntParameter(input.substring(input.lastIndexOf(" ") + 1));

            createCoordinates(scanner);
            createPerson(scanner);
            createProduct(scanner);

            toOutQueue(addBuilder.buildRequest());
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        } catch (IncorrectInputException e) {
            System.out.println("no such id");
        }
    }
}
