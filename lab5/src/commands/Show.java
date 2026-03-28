package commands;

import java.util.PriorityQueue;
import java.util.Scanner;

import managers.CollectionManager;
import model.Product;

/**
 * Команда, показывающая все элементы коллекции.
 */
public class Show extends Command{

    //constructor
    public Show(CollectionManager collection) {
        super(collection);
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println();
        PriorityQueue<Product> dopleganger = new PriorityQueue<>(collection.getCollection());
        if (dopleganger.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            while (!dopleganger.isEmpty()) {
                System.out.println(dopleganger.poll()); 
            }
        }
        System.out.println();
    }
}
