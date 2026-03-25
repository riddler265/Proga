package commands;

import collectionManager.CollectionManager;
import commandManager.CommandManager;
import enums.UnitOfMeasure;
import exceptions.ExecuteException;
import product.Product;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class UpdateId extends Command{

    //fields
    //product
    private int currentId;
    private Product currentProduct;

    //commandManager
    private final CommandManager commandManager;
    private boolean isSystemReader;

    //constructor
    public UpdateId(CollectionManager collection, CommandManager commandManager) {
        super(collection);
        this.commandManager = commandManager;
    }

    //setters
    public void setProductName(String input, Scanner scanner) {
        if (!input.isEmpty()) currentProduct.setName(input);
    }

    public void setPrice(String input, Scanner scanner) throws ExecuteException {
        if (input.equalsIgnoreCase("null")) {
            currentProduct.setPrice(null);
        } else if (!input.isEmpty()) {
            try {
                Float price = Float.parseFloat(input);
                if (price <= 0.0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                if (isSystemReader) {
                    System.out.print("Введите число больше 0: ");
                    setPrice(scanner.nextLine(), scanner);
                } else throw new ExecuteException(getClass().getSimpleName());
            }
        }
    }

    public void setPartNumber(String input, Scanner scanner) {
        if (input.equals("null")) currentProduct.setPartNumber(null);
        else if (input.isEmpty()) currentProduct.setPartNumber(input);
    }

    public void setManufactureCost(String input, Scanner scanner) throws ExecuteException {
        if (!input.isEmpty()) {
            try {
                float manufactureCost = Float.parseFloat(input);
                currentProduct.setManufactureCost(manufactureCost);
            } catch (NumberFormatException e) {
                if (isSystemReader) {
                    System.out.print("Введите число: ");
                    setManufactureCost(scanner.nextLine(), scanner);
                } else throw new ExecuteException(getClass().getSimpleName());
            }
        }
    }

    protected void setUnitOfMeasure(String input, Scanner scanner) throws ExecuteException {
        if (!input.isEmpty()) {
            try {
                UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(input.toUpperCase());
                currentProduct.setUnitOfMeasure(unitOfMeasure);
            } catch (IllegalArgumentException e) {
                if (isSystemReader) {
                    System.out.println("Введите единицу измерения. " + UnitOfMeasure.units() + ":");
                    setUnitOfMeasure(scanner.nextLine(), scanner);
                } else throw new ExecuteException(getClass().getSimpleName());
            }
        }
    }

    protected void setCoordinateX(String input, Scanner scanner) {
        if (!input.isEmpty()) {
            try {
                Integer x = Integer.parseInt(input);
                if (x <= -645) {
                    throw new NumberFormatException();
                }
                currentProduct.getCoordinates().setX(x);
            } catch (NumberFormatException e) {
                if (isSystemReader) {
                    System.out.print("Введите целое число больше -645: ");
                    setCoordinateX(scanner.nextLine(), scanner);
                } else throw new ExecuteException(getClass().getSimpleName());
            }
        }
    }

    protected void setCoordinateY(String input, Scanner scanner) throws ExecuteException {
        if (!input.isEmpty()) {
            try {
                Integer y = Integer.parseInt(input);
                currentProduct.getCoordinates().setY(y);
            } catch (NumberFormatException e) {
                if (isSystemReader) {
                    System.out.print("Введите целое число: ");
                    setCoordinateY(scanner.nextLine(), scanner);
                } else throw new ExecuteException(getClass().getSimpleName());
            }
        }
    }

    protected void setPersonName(String input, Scanner scanner) {
        if (!input.isEmpty()) currentProduct.getOwner().setName(input);
    }

    protected void setHeight(String input, Scanner scanner) throws ExecuteException {
        if (!input.isEmpty()) {
            try {
                float height = Float.parseFloat(input);
                if (height <= 0) {
                    throw new NumberFormatException();
                }
                currentProduct.getOwner().setHeight(height);
            } catch (NumberFormatException e) {
                if (isSystemReader) {
                    System.out.print("Введите число больше 0: ");
                    setHeight(scanner.nextLine(), scanner);
                } else throw new ExecuteException(getClass().getSimpleName());
            }
        }
    }

    @Override
    public void execute(String input, Scanner scanner) {
        isSystemReader = commandManager.isSystemReader();

        try {
            currentId = Integer.parseInt(input.substring(input.lastIndexOf(" ") + 1));
            currentProduct = collection.getProductById(currentId);

            System.out.println("\nЕсли вы не хотите менять характеристику, нажмите enter");
            System.out.print("\nВведите название продукта: ");
            setProductName(scanner.nextLine(), scanner);
            System.out.print("\nВведите цену продукта больше 0: ");
            setPrice(scanner.nextLine(), scanner);
            System.out.print("\nВведите номер партии: ");
            setPartNumber(scanner.nextLine(), scanner);
            System.out.print("\nВведите стоимость производства продукта: ");
            setManufactureCost(scanner.nextLine(), scanner);
            System.out.println("\nВведите единицу измерения. " + UnitOfMeasure.units() + ":");
            setUnitOfMeasure(scanner.nextLine(), scanner);
            System.out.print("\nВведите целое число больше -645 - координату X: ");
            setCoordinateX(scanner.nextLine(), scanner);
            System.out.print("\nВведите целое число - координату Y: ");
            setCoordinateY(scanner.nextLine(), scanner);
            if (currentProduct.getOwner() != null) {
                System.out.print("\nВведите имя владельца: ");
                setPersonName(scanner.nextLine(), scanner);
                System.out.print("\nВведите рост владельца больше 0: ");
                setHeight(scanner.nextLine(), scanner);
            }
            System.out.println("\n\nНовые характеристики продукта №" + currentId + ":");
            System.out.println(currentProduct.toString() + "\n");
        } catch (NumberFormatException e) {
            System.out.println("Продукта с таким id нет\n");
        } catch (ExecuteException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("\nПроверьте правильность написания команд в исполняемом файле.");
        }
    }
}
