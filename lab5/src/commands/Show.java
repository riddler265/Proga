package commands;

import java.util.PriorityQueue;
import java.util.Scanner;

import collection.Manager;
import product.Product;

public class Show extends Command{

    //constructor
    public Show(Manager collection) {
        super(collection);
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println();
        PriorityQueue<Product> dopleganger = new PriorityQueue<>(collection.getCollection());
        if (dopleganger.isEmpty()) {
            System.out.println("Collection is empty.");
        } else {
            while (!dopleganger.isEmpty()) {
                System.out.println(dopleganger.poll()); 
            }
        }
        System.out.println();
    }
}
