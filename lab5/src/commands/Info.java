package commands;

import collection.Manager;

import java.util.Scanner;

public class Info extends Command{

    //constructor
    public Info(Manager collection) {
        super(collection);
    }

    //execute
    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println(collection.getInfo());
    }
}
