package commands;

import managers.CollectionManager;
import java.util.Scanner;

/**
 * <p>
 *     Команда, выводящая список
 *     всех доступных команд.
 * </p>
 */
public class Help extends Command{
    //fields
    private static String commands = "\nhelp - вывести справку по доступным командам." +
            "\ninfo - вывести информацию о коллекции." +
            "\nshow - вывести все элементы коллекции." +
            "\nadd {element} - добавить новый элемент в коллекцию." +
            "\nupdate id {element} - обновить значение элемента коллекции, id которого равен заданному." +
            "\nremove_by_id id - удалить элемент из коллекции по его id." +
            "\nclear - очистить коллекцию." +
            "\nsave - сохранить коллекцию в файл." +
            "\nexecute_script file_name - считать и исполнить скрипт из указанного файла." +
            "\nexit - завершить программу (без сохранения в файл)." +
            "\nadd_if_min {element} - добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции." +
            "\nremove_greater {element} - удалить из коллекции все элементы, превышающие заданный." +
            "\nhistory - вывести последние 8 команд." +
            "\nremove_all_by_price price - удалить из коллекции все элементы, значение поля price которого эквивалентно заданному." +
            "\nfilter_less_than_manufacture_cost manufactureCost - вывести элементы, значение поля manufactureCost которых меньше заданного." +
            "\nfilter_greater_than_manufacture_cost manufactureCost - вывести элементы, значение поля manufactureCost которых больше заданного.\n";

    //constructor
    public Help(CollectionManager collection) {
        super(collection);
    }

    @Override
    public void execute(String input, Scanner scanner) {
        System.out.println(commands);
    }
}
