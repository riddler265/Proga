package commands;

import exceptions.IncorrectInputException;
import managers.CollectionManager;
import managers.CommandManager;
import model.enums.Color;
import model.enums.UnitOfMeasure;
import exceptions.ExecuteException;
import model.Product;

import java.util.NoSuchElementException;
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
    public boolean skip(String input) {
        return input.trim().equalsIgnoreCase("\\s") || input.trim().equalsIgnoreCase("/s");
    }//endregion

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
            if (finalOwner != null) {
                createPerson(scanner);
            }
            createProduct(scanner);
            System.out.println("\nНовые параметры продукта №" + finalProduct.getId());

        } catch (NumberFormatException e) {
            System.out.println("Продукта с таким id нет\n");
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        }
    }
}
