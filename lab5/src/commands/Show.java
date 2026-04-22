package commands;

import java.util.PriorityQueue;
import java.util.Scanner;

import managers.AnnounceManager;
import managers.CollectionManager;
import model.Product;

/**
 * Команда, показывающая все элементы коллекции.
 */
public class Show extends Command{

    //constructor
    public Show(CollectionManager collectionManager) {
        super(collectionManager);
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        PriorityQueue<Product> doppelganger = new PriorityQueue<>(collectionManager.getCollection());
        if (doppelganger.isEmpty()) {
            println("show.empty");
        } else {
            while (!doppelganger.isEmpty()) {
                System.out.println("\n" + doppelganger.poll());
            }
        }
        System.out.println();
    }
}
