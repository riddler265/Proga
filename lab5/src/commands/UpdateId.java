package commands;

import managers.CollectionManager;
import managers.CommandManager;
import model.Person;
import model.enums.Color;
import model.enums.Strategy;
import exceptions.ExecuteException;
import java.awt.*;
import java.util.Scanner;

/**
 * Команда, обновляющая значения объекта по id.
 */
public class UpdateId extends Add {

    //constructor
    public UpdateId(CollectionManager collection, CommandManager commandManager) {
        super(collection, commandManager);
    }

    //region helpers
    //skip
    protected boolean skip(String input) {
        return input.trim().equalsIgnoreCase("\\s") || input.trim().equalsIgnoreCase("/s");
    }

    @Override
    protected Strategy needOwner(String input, Scanner scanner) throws ExecuteException {
        if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) return Strategy.Y;
        else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) return Strategy.N;
        else if (skip(input)) return Strategy.S;
        else if (isSystemReader) {
            System.out.print("Введите yes/no: ");
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
        announce("Что с владельцем?", "yes/no");
        switch (needOwner(scanner.nextLine(), scanner)) {
            case Y:
                if (finalOwner == null) finalOwner = new Person();
                announce("Введите имя владельца","");
                writePersonName(scanner.nextLine(), scanner);
                announce("Введите дату рождения владельца", "dd-MM-yyyy HH:mm:ss");
                writeBirthday(scanner.nextLine(), scanner);
                announce("Введите рост владельца", "больше 0, 5 знаков после запятой");
                writeHeight(scanner.nextLine(), scanner);
                announce("Введите данные паспорта", "Null/строка");
                writePassportID(scanner.nextLine(), scanner);
                announce("Введите цвет волос владельца", Color.getColorsInfo());
                System.out.println();
                writeHairColor(scanner.nextLine(), scanner);
                break;
            case N:
                finalOwner = null;
                break;
            case S:
                break;
        }
    }//endregion

    @Override
    public void execute(String input, Scanner scanner) {

        isSystemReader = commandManager.isSystemReader();

        try {

            int currentId = Integer.parseInt(input.substring(input.lastIndexOf(" ") + 1));
            finalProduct = collectionManager.getProductById(currentId);
            finalCoordinates = finalProduct.getCoordinates();
            finalOwner = finalProduct.getOwner();

            System.out.println("Если не хотите менять параметр - введите \\s");

            createCoordinates(scanner);
            createPerson(scanner);
            createProduct(scanner);
            System.out.println("\nНовые параметры продукта №" + finalProduct.getId() + "\n" + finalProduct.toString() + "\n");

        } catch (NumberFormatException e) {
            System.out.println("Продукта с таким id нет\n");
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        }
    }
}
