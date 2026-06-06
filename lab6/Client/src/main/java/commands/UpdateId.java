package commands;

import enums.Color;
import enums.Commands;
import enums.Strategy;
import exceptions.ExecuteException;
import exceptions.IncorrectInputException;
import json.JsonManager;
import util.ClientRequestBuilder;
import util.ConsoleManager;

import java.util.Scanner;

public class UpdateId extends Add {

    public UpdateId(ConsoleManager consoleManager) {
        super(consoleManager);
    }

    //region helpers
    //skip
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
    //endregion

    //region writing
    //product
    @Override
    public void writeProductName(String input, Scanner scanner) throws ExecuteException{
        if (!skip(input)) {
            super.writeProductName(input, scanner);
        }
    }

    @Override
    public void writePrice(String input, Scanner scanner) throws ExecuteException {
        if (!skip(input)) {
            super.writePrice(input, scanner);
        }
    }
    @Override
    public void writePartNumber(String input, Scanner scanner) {
        if (!skip(input)) {
            super.writePartNumber(input, scanner);
        }
    }

    @Override
    public void writeManufactureCost(String input, Scanner scanner) throws ExecuteException {
        if (!skip(input)) {
            super.writeManufactureCost(input, scanner);
        }
    }

    @Override
    protected void writeUnitOfMeasure(String input, Scanner scanner) throws ExecuteException {
        if (!skip(input)) {
            super.writeUnitOfMeasure(input, scanner);
        }
    }

    //coordinates
    @Override
    protected void writeCoordinateX(String input, Scanner scanner) {
        if (!skip(input)) {
            super.writeCoordinateX(input, scanner);
        }
    }

    @Override
    protected void writeCoordinateY(String input, Scanner scanner) throws ExecuteException {
        if (!skip(input)) {
            super.writeCoordinateY(input, scanner);
        }
    }

    //person
    @Override
    protected void writePersonName(String input, Scanner scanner) {
        if (!skip(input)) {
            super.writePersonName(input, scanner);
        }
    }

    @Override
    protected void writeBirthday(String input, Scanner scanner) {
        if (!skip(input)) {
            super.writeBirthday(input, scanner);
        }
    }

    @Override
    protected void writeHeight(String input, Scanner scanner) throws ExecuteException {
        if (!skip(input)) {
            super.writeHeight(input, scanner);
        }
    }

    @Override
    protected void writePassportID(String input, Scanner scanner) {
        if (!skip(input)) {
            super.writePassportID(input, scanner);
        }
    }

    @Override
    protected void writeHairColor(String input, Scanner scanner) {
        if (!skip(input)) {
            super.writeHairColor(input, scanner);
        }
    }//endregion

    //region creation
    @Override
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
                break;
            case S:
                addBuilder.setPersonName("detach everything as it is");
                break;
        }
    }//endregion

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
