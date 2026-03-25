package commands;

import collectionManager.CollectionManager;
import product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Команда, удаляющая из коллекции все элементы, у которых значение поля price равно заданномму.
 */
public class RemoveAllByPrice extends Command{

    //fields
    private float currentPrice;
    private final List<Product> toRemove= new ArrayList<>();

    //constructor
    public RemoveAllByPrice(CollectionManager collection) {
        super(collection);
    }

    /**
     * Исполнение команды. Используется {@link java.util.Collection#removeIf(Predicate)} для безопасного удаления.
     * @param input ввод пользователя в консоль.
     * <p>
     * @param scanner объект класса {@link Scanner},
     *     текущий источник чтения.
     * </p>
     */
    @Override
    public void execute(String input, Scanner scanner) {
        try {
            currentPrice = Float.parseFloat(input.substring(input.lastIndexOf(" ") + 1));
            if (currentPrice > 0.0) {
                collection.getCollection().removeIf(product -> product.getPrice() == currentPrice);
                System.out.println("\nВсе предметы с заданной ценой удалены.\n");
            } else throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Нужно ввести положительное число.");
        }
    }
}
