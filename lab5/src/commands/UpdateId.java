package commands;

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
    }
    //endregion

    //region setters
    //product
    public void setProductName(String input, Scanner scanner) throws ExecuteException{
        if (!skip(input)) writeProductName(input, scanner);
    }

    public void setPrice(String input, Scanner scanner) throws ExecuteException {
        if (!skip(input)) writePrice(input, scanner);
    }

    public void setPartNumber(String input, Scanner scanner) {
        if (!skip(input)) writePartNumber(input, scanner);
    }

    public void setManufactureCost(String input, Scanner scanner) throws ExecuteException {
        if (!skip(input)) writeManufactureCost(input,scanner);
    }

    protected void setUnitOfMeasure(String input, Scanner scanner) throws ExecuteException {
        if (!skip(input)) writeUnitOfMeasure(input, scanner);
    }

    //coordinates
    protected void setCoordinateX(String input, Scanner scanner) {
        if (!skip(input)) writeCoordinateX(input, scanner);
    }

    protected void setCoordinateY(String input, Scanner scanner) throws ExecuteException {
        if (!skip(input)) writeCoordinateY(input, scanner);
    }

    //person
    protected void setPersonName(String input, Scanner scanner) {
        if (!skip(input)) writePersonName(input, scanner);
    }

    protected void setBirthday(String input, Scanner scanner) {
        if (!skip(input)) writeBirthday(input, scanner);
    }

    protected void setHeight(String input, Scanner scanner) throws ExecuteException {
        if (!skip(input)) writeHeight(input, scanner);
    }

    protected void setPassportID(String input, Scanner scanner) {
        if (!skip(input)) writePassportID(input, scanner);
    }

    protected void setHairColor(String input, Scanner scanner) {
        if (!skip(input)) writeHairColor(input, scanner);
    }//endregion

    //region create
    @Override
    protected void createPerson(Scanner scanner) throws ExecuteException {

        announce("Введите имя владельца","");
        setPersonName(scanner.nextLine(), scanner);
        announce("Введите дату рождения владельца", "");
        setBirthday(scanner.nextLine(), scanner);
        announce("Введите рост владельца", "больше 0");
        setHeight(scanner.nextLine(), scanner);
        announce("Введите данные паспорта", "строка/Null");
        setPassportID(scanner.nextLine(), scanner);
        announce("Введите цвет волос владельца", Color.getColorsInfo());
        System.out.println();
        setHairColor(scanner.nextLine(), scanner);

    }

    @Override
    protected void createCoordinates(Scanner scanner) throws ExecuteException {

        announce("Введите координату X", "целое число больше -645");
        setCoordinateX(scanner.nextLine(), scanner);
        announce("Введите координату Y", "целое число");
        setCoordinateY(scanner.nextLine(), scanner);
    }

    @Override
    protected void createProduct(Scanner scanner) throws ExecuteException {

        announce("Введите название продукта", "не пустая строка");
        setProductName(scanner.nextLine(), scanner);
        announce("Введите цену продукта больше 0", "число больше 0/Null");
        setPrice(scanner.nextLine(), scanner);
        announce("Введите номер партии", "число больше 0/Null");
        setPartNumber(scanner.nextLine(), scanner);
        announce("Введите стоимость производства продукта", "число");
        setManufactureCost(scanner.nextLine(), scanner);
        announce("Введите единицу измерения", UnitOfMeasure.getUnitsInfo());
        System.out.println();
        setUnitOfMeasure(scanner.nextLine(), scanner);
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
            System.out.println("\nНовые параметры продукта №" + finalProduct.getId());

        } catch (NumberFormatException e) {
            System.out.println("Продукта с таким id нет\n");
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        }
    }
}
