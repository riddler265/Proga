package commands;

import collection.Manager;
import java.util.Scanner;

public class Help extends Command{
    //fields
    private static String commands = "\nhelp - list of all available commands." +
            "\ninfo - collection info." +
            "\nshow - all the items in the collection." +
            "\nadd {element} - add a new item to the collection." +
            "\nupdate id {element} - update the value of a collection element whose id is equal to the specified value." +
            "\nremove_by_id id - delete an item from the collection by its id." +
            "\nclear - clear the collection." +
            "\nsave - save the collection to a file." +
            "\nexecute_script file_name - read and execute the script from the specified file." +
            "\nadd_if_min {element} - add a new element to the collection if its value is less than the value of the smallest element in the collection." +
            "\nremove_greater {element} - remove all elements from the collection that exceed the specified value." +
            "\nhistory - display the last 8 commands." +
            "\nremove_all_by_price price - remove all elements from the collection whose price field value is equivalent to the specified value." +
            "\nfilter_less_than_manufacture_cost manufactureCost - display elements whose manufactureCost field value is less than the specified value." +
            "\nfilter_greater_than_manufacture_cost manufactureCost - display elements whose manufactureCost field value is greater than the specified value" +
            "\n";

    //constructor
    public Help(Manager collection) {
        super(collection);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println(commands);
    }
}
