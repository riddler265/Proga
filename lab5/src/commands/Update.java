package commands;

import collection.Manager;
import enums.UnitOfMeasure;
import exceptions.IncorrectInputException;
import product.Product;

import java.util.Scanner;

public class Update extends Command{

    //fields
    private int currentId;
    private Product currentProduct;
    private Scanner scanner = new Scanner(System.in);

    //constructor
    public Update(Manager collection) {
        super(collection);
    }

    //setters
    public void setProductName(String input) {
        if (!input.isEmpty()) currentProduct.setName(input);
    }

    public void setPrice(String input) {
        if (!input.isEmpty()) {
            try {
                Float price = Float.parseFloat(input);
                if (price <= 0.0) {
                    throw new NumberFormatException();
                }
                currentProduct.setPrice(price);
            } catch (NumberFormatException e) {
                System.out.print("Введите число больше 0: ");
                setPrice(scanner.nextLine());
            }
        }
    }

    public void setPartNumber(String input) {
        if (!input.isEmpty()) currentProduct.setPartNumber(input);
    }

    public void setManufactureCost(String input) {
        if (!input.isEmpty()) {
            try {
                float manufactureCost = Float.parseFloat(input);
                currentProduct.setManufactureCost(manufactureCost);
            } catch (NumberFormatException e) {
                System.out.print("Введите число: ");
                setManufactureCost(scanner.nextLine());
            }
        }
    }

    protected void setUnitOfMeasure(String input) {
        if (!input.isEmpty()) {
            try {
                UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(input.toUpperCase());
                currentProduct.setUnitOfMeasure(unitOfMeasure);
            } catch (IllegalArgumentException e) {
                System.out.println("Введите единицу измерения. " + UnitOfMeasure.units() + ":");
                setUnitOfMeasure(scanner.nextLine());
            }
        }
    }

    protected void setCoordinateX(String input) {
        if (!input.isEmpty()) {
            try {
                Integer x = Integer.parseInt(input);
                if (x <= -645) {
                    throw new NumberFormatException();
                }
                currentProduct.getCoordinates().setX(x);
            } catch (NumberFormatException e) {
                System.out.print("Введите целое число больше -645: ");
                setCoordinateX(scanner.nextLine());
            }
        }
    }

    protected void setCoordinateY(String input) {
        if (!input.isEmpty()) {
            try {
                Integer y = Integer.parseInt(input);
                currentProduct.getCoordinates().setY(y);
            } catch (NumberFormatException e) {
                System.out.print("Введите целое число: ");
                setCoordinateY(scanner.nextLine());
            }
        }
    }

    protected void setPersonName(String input) {
        currentProduct.getOwner().setName(input);
    }

    protected void setHeight(String input) {
        try {
            float height = Float.parseFloat(input);
            if (height <= 0) {
                throw new NumberFormatException();
            }
            currentProduct.getOwner().setHeight(height);
        } catch (NumberFormatException e) {
            System.out.print("Введите число больше 0: ");
            setHeight(scanner.nextLine());
        }
    }

    @Override
    public void execute(String input, Scanner scanner) {
        try {
            currentId = Integer.parseInt(input.substring(input.lastIndexOf(" ") + 1));
            currentProduct = collection.getProductById(currentId);
            if (!(currentProduct == null)) {
                System.out.println("\nЕсли вы не хотите менять характеристику, нажмите enter");
                System.out.print("\nВведите название продукта: ");
                setProductName(scanner.nextLine());
                System.out.print("\nВведите цену продукта больше 0: ");
                setPrice(scanner.nextLine());
                System.out.print("\nВведите номер партии: ");
                setPartNumber(scanner.nextLine());
                System.out.print("\nВведите стоимость производства продукта: ");
                setManufactureCost(scanner.nextLine());
                System.out.println("\nВведите единицу измерения. " + UnitOfMeasure.units() + ":");
                setUnitOfMeasure(scanner.nextLine());
                System.out.print("\nВведите целое число больше -645 - координату X: ");
                setCoordinateX(scanner.nextLine());
                System.out.print("\nВведите целое число - координату Y: ");
                setCoordinateY(scanner.nextLine());
                System.out.print("\nВведите имя владельца: ");
                setPersonName(scanner.nextLine());
                System.out.print("\nВведите рост владельца больше 0: ");

                System.out.println("\n\nНовые характеристики продукта №" + currentId + ":");
                System.out.println(currentProduct.toString());
            } else throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Продукта с таким id нет\n");
        }
    }
}
